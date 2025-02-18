/*Copyright (c) 2020-2021 TIREA All Rights Reserved.
 This software is the confidential and proprietary information of TIREA You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with TIREA*/
package es.tirea.wavemaker.consultaFacturas.desa.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.commons.wrapper.StringWrapper;
import com.wavemaker.runtime.data.export.ExportOptions;
import com.wavemaker.runtime.file.manager.ExportedFileManager;
import com.wavemaker.runtime.security.xss.XssDisable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import es.tirea.wavemaker.consultaFacturas.desa.service.DESAQueryExecutorService;
import es.tirea.wavemaker.consultaFacturas.desa.models.query.*;

@RestController(value = "DESA.QueryExecutionController")
@RequestMapping("/DESA/queryExecutor")
@Api(value = "QueryExecutionController", description = "controller class for query execution")
public class QueryExecutionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryExecutionController.class);

    @Autowired
    private DESAQueryExecutorService queryService;

    @Autowired
	private ExportedFileManager exportedFileManager;

    @RequestMapping(value = "/queries/facturasCliente", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Facturas de un cliente")
    public Page<FacturasClienteResponse> executeFacturasCliente(@RequestParam(value = "all") Boolean _all, @RequestParam(value = "idCliente") List<String> idCliente, @RequestParam(value = "fechaIni") String fechaIni, @RequestParam(value = "fechaFin") String fechaFin, Pageable pageable, HttpServletRequest _request) {
        LOGGER.debug("Executing named query: facturasCliente");
        Page<FacturasClienteResponse> _result = queryService.executeFacturasCliente(_all, idCliente, fechaIni, fechaFin, pageable);
        LOGGER.debug("got the result for named query: facturasCliente, result:{}", _result);
        return _result;
    }

    @ApiOperation(value = "Returns downloadable file url for query facturasCliente")
    @RequestMapping(value = "/queries/facturasCliente/export", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @XssDisable
    public StringWrapper exportFacturasCliente(@RequestParam(value = "all") Boolean _all, @RequestParam(value = "idCliente") List<String> idCliente, @RequestParam(value = "fechaIni") String fechaIni, @RequestParam(value = "fechaFin") String fechaFin, @RequestBody ExportOptions exportOptions, Pageable pageable) {
        LOGGER.debug("Exporting named query: facturasCliente");

        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = "facturasCliente";
        }
        exportedFileName += exportOptions.getExportType().getExtension();

        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName,
                        outputStream -> queryService.exportFacturasCliente(_all, idCliente, fechaIni, fechaFin,  exportOptions, pageable, outputStream));

        return new StringWrapper(exportedUrl);
    }

    @RequestMapping(value = "/queries/allClients", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Todos los clientes")
    public Page<AllClientsResponse> executeAllClients(Pageable pageable, HttpServletRequest _request) {
        LOGGER.debug("Executing named query: allClients");
        Page<AllClientsResponse> _result = queryService.executeAllClients(pageable);
        LOGGER.debug("got the result for named query: allClients, result:{}", _result);
        return _result;
    }

    @ApiOperation(value = "Returns downloadable file url for query allClients")
    @RequestMapping(value = "/queries/allClients/export", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @XssDisable
    public StringWrapper exportAllClients(@RequestBody ExportOptions exportOptions, Pageable pageable) {
        LOGGER.debug("Exporting named query: allClients");

        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = "allClients";
        }
        exportedFileName += exportOptions.getExportType().getExtension();

        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName,
                        outputStream -> queryService.exportAllClients( exportOptions, pageable, outputStream));

        return new StringWrapper(exportedUrl);
    }

    @RequestMapping(value = "/queries/facturaGraph", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Query de soporte a las facturas")
    public Page<FacturaGraphResponse> executeFacturaGraph(@RequestParam(value = "all") Boolean _all, @RequestParam(value = "idCliente") List<String> idCliente, @RequestParam(value = "fechaIni") String fechaIni, @RequestParam(value = "fechaFin") String fechaFin, Pageable pageable, HttpServletRequest _request) {
        LOGGER.debug("Executing named query: facturaGraph");
        Page<FacturaGraphResponse> _result = queryService.executeFacturaGraph(_all, idCliente, fechaIni, fechaFin, pageable);
        LOGGER.debug("got the result for named query: facturaGraph, result:{}", _result);
        return _result;
    }

    @ApiOperation(value = "Returns downloadable file url for query facturaGraph")
    @RequestMapping(value = "/queries/facturaGraph/export", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @XssDisable
    public StringWrapper exportFacturaGraph(@RequestParam(value = "all") Boolean _all, @RequestParam(value = "idCliente") List<String> idCliente, @RequestParam(value = "fechaIni") String fechaIni, @RequestParam(value = "fechaFin") String fechaFin, @RequestBody ExportOptions exportOptions, Pageable pageable) {
        LOGGER.debug("Exporting named query: facturaGraph");

        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = "facturaGraph";
        }
        exportedFileName += exportOptions.getExportType().getExtension();

        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName,
                        outputStream -> queryService.exportFacturaGraph(_all, idCliente, fechaIni, fechaFin,  exportOptions, pageable, outputStream));

        return new StringWrapper(exportedUrl);
    }

}