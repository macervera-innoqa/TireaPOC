/*Copyright (c) 2020-2021 TIREA All Rights Reserved.
 This software is the confidential and proprietary information of TIREA You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with TIREA*/
package es.tirea.wavemaker.consultaFacturas.fileservice;

import com.wavemaker.runtime.WMAppContext;
import com.wavemaker.runtime.file.model.DownloadResponse;
import com.wavemaker.runtime.file.manager.FileServiceManager;
import com.wavemaker.runtime.util.WMRuntimeUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.wavemaker.runtime.service.annotations.ExposeToClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.beans.factory.annotation.Value;

import es.tirea.wavemaker.consultaFacturas.desa.service.FacturasService;
import es.tirea.wavemaker.common.security.ldap.SecurityConstants;
import es.tirea.wavemaker.consultaFacturas.desa.Facturas;
import es.tirea.wavemaker.AuditLog;


import com.wavemaker.runtime.security.Attribute;
import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.security.model.UserInfo;
import com.wavemaker.runtime.security.model.SecurityInfo;

import org.springframework.beans.factory.annotation.Autowired;
/**
 * File service class with methods to upload, download, list and delete files.
 * This is a singleton class with all of its public methods exposed to the client via controller.
 * Their return values and parameters will be passed to the client or taken
 * from the client respectively.
 */
@ExposeToClient
public class FileService {

   private static final Logger logger=LoggerFactory.getLogger(FileService.class);
    
    @Autowired
    private FileServiceManager fileServiceManager;
    
    @Autowired
    private FacturasService facturasService;

    @Autowired
    private SecurityService securityService;
    
    private File uploadDirectory = null;
    
    @Value("${app.environment.FilePath}")
    private String parameterFilePath;

    @PostConstruct
    protected void init() {
        uploadDirectory = getUploadDir();
    }


    /**
     * *******************************************************************************
     * INNER CLASS: WMFile
     * DESCRIPTION:
     * The class WMFile is a class used to represent information about a list of files.
     * An array of WMFile objects is returned when the client asks for a list of files
     * on the server.
     * ********************************************************************************
     */
    public static class WMFile {
        private String path;
        private String inlinePath;
        private String name;
        private long size;
        private String type;

        public WMFile(String path, String inlinePath, String name, long size, String type) {
            this.path = path;
            this.inlinePath = inlinePath;
            this.name = name;
            this.size = size;
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getInlinePath() {
            return inlinePath;
        }

        public void setInlinePath(String inlinePath) {
            this.inlinePath = inlinePath;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }
    }

    /*
        The class returns filepath, name , boolean success tells whether the upload was successful or not
        and error message if the upload was not successful.
     */
    public static class FileUploadResponse {

        private String path;
        private String inlinePath;
        private String fileName;
        private long length;

        private boolean success;
        private String errorMessage;

        public FileUploadResponse(String path, String inlinePath, String name, long length, boolean success, String errorMessage) {
            this.path = path;
            this.inlinePath = inlinePath;
            this.fileName = name;
            this.length = length;
            this.success = success;
            this.errorMessage = errorMessage;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public long getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getPath() {
            return this.path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getInlinePath() {
            return inlinePath;
        }

        public void setInlinePath(String inlinePath) {
            this.inlinePath = inlinePath;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    /**
     * *****************************************************************************
     * TEMPLATE PROPERTY: uploadDir
     * DESCRIPTION
     * When you created your java service, you were prompted to enter a value for
     * uploadDir.  The uploadDir is the default location to store files, and any
     * request to delete or download files that contains a relative path will
     * search for the file starting from uploadDir.
     * NOTES:
     * You can change this value at any time.
     * You may need to set a different uploadDir for your deployment environment
     * than you used on your local development environment.
     * ******************************************************************************
     */
    protected File getUploadDir() {
        String uploadDir = this.parameterFilePath;
        File f = new File(uploadDir);
        f.mkdirs();
        return f;
    }
    
    private DownloadResponse getDownloadGeneric(Integer idFactura, String format, boolean inLine) throws Exception{
        logger.debug("Init getDownloadGeneric");
        Facturas factura = facturasService.getById(idFactura);
        String currentUserId = securityService.getUserId();
        
        String returnName = facturasService.findAssociatedFichProcesadoses(idFactura,null).getContent().get(0).getNombre();
        returnName = returnName.substring(0, returnName.lastIndexOf('.'));
        String fileName = returnName + "."+ format;
        String relativePath = "/"+format.toUpperCase()+"/" + returnName.substring(returnName.lastIndexOf('_') + 1, returnName.length()-2) + "/" + factura.getIdServicio() + "/";
        
        if(currentUserId.isEmpty()){
            AuditLog.error("ServiceTYPEUSER", fileName, "Consultar", "No", "No hay usuarios conectados" );
            throw new BadCredentialsException("Ningun usuario ha abierto sesion");
        }
        
        //Comprobar si el ususario tiene acceso a la factura
        if (((List<String>)Arrays.asList(securityService.getSecurityInfo().getUserInfo().getUserRoles())).contains(SecurityConstants.rolEntidad)){
            //Si el usuario no es administrador
            if (!(Arrays.asList((String[])securityService.getSecurityInfo().getUserInfo().getUserAttributes().get(SecurityConstants.attributeEntidades))).contains(factura.getIdCliente())){
                //El usuario no tiene permiso de acceso a esta factura
                AuditLog.error("ServiceTYPEUSER", fileName, "Consultar", "No", "El usuario no tiene acceso a la factura" );
                throw new BadCredentialsException("El usuario no tiene acceso a la factura. securityService.getUserId(): ["+currentUserId+"] , factura.getIdCliente(): ["+factura.getIdCliente()+"]");
                
            }
            
        }
        AuditLog.debug("ServiceTYPEUSER", fileName, "Consultar", "Si", "El usuario ha descargado el fichero" );
        return downloadFile(fileName, relativePath, returnName, false);
        
        
    }
    public DownloadResponse getDownloadPdf(Integer idFactura) throws Exception {
        logger.debug("Init getDownloadPdf");
        return getDownloadGeneric(idFactura, "pdf", false);
    }
    
    public DownloadResponse getDownloadXml(Integer idFactura) throws Exception {
        logger.debug("Init getDownloadXml");
        return getDownloadGeneric(idFactura, "xml", false);
    }
    
    public DownloadResponse getDownloadPdfAsInLine(Integer idFactura) throws Exception {
        logger.debug("Init getDownloadPdfAsInLine");
        return getDownloadGeneric(idFactura, "pdf", true);
    }
    
    public DownloadResponse getDownloadXmlAsInLine(Integer idFactura) throws Exception {
        logger.debug("Init getDownloadXmlAsInLine");
        return getDownloadGeneric(idFactura, "xml", true);
    }

    private DownloadResponse downloadFile(String file, String relativePath, String returnName, boolean inline) throws Exception {
        logger.debug("Init DonwloadFile");
        File f = fileServiceManager.downloadFile(file, relativePath, uploadDirectory);
        
        returnName = (returnName != null && returnName.length() > 0) ? returnName : f.getName();

        // Create our return object and setup its properties
        DownloadResponse downloadResponse = new DownloadResponse();

        // Setup the DownloadResponse
        FileInputStream fis = new FileInputStream(f);
        downloadResponse.setContents(fis);
        downloadResponse.setInline(inline);
        downloadResponse.setFileName(file);
        return downloadResponse;
    }
}
