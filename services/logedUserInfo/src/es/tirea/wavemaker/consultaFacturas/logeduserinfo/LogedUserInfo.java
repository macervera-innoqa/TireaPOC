/*Copyright (c) 2020-2021 TIREA All Rights Reserved.
 This software is the confidential and proprietary information of TIREA You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with TIREA*/
package es.tirea.wavemaker.consultaFacturas.logeduserinfo;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.service.annotations.ExposeToClient;
import com.wavemaker.runtime.service.annotations.HideFromClient;

import es.tirea.wavemaker.common.security.ldap.SecurityConstants;

import es.tirea.wavemaker.consultaFacturas.desa.service.DESAQueryExecutorService;
import es.tirea.wavemaker.consultaFacturas.desa.models.query.AllClientsResponse;

//import es.tirea.wavemaker.consultaFacturas.logeduserinfo.model.*;

/**
 * This is a singleton class with all its public methods exposed as REST APIs via generated controller class.
 * To avoid exposing an API for a particular public method, annotate it with @HideFromClient.
 *
 * Method names will play a major role in defining the Http Method for the generated APIs. For example, a method name
 * that starts with delete/remove, will make the API exposed as Http Method "DELETE".
 *
 * Method Parameters of type primitives (including java.lang.String) will be exposed as Query Parameters &
 * Complex Types/Objects will become part of the Request body in the generated API.
 *
 * NOTE: We do not recommend using method overloading on client exposed methods.
 */
@ExposeToClient
public class LogedUserInfo {

    private static final Logger logger = LoggerFactory.getLogger(LogedUserInfo.class);
    private static final String allCustomers = "Todos";

    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private DESAQueryExecutorService desaQueryExecutorService;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/
    

    public List<String> getClientes() {
        logger.debug("Starting getClientes");
        List<String> listaClientes = new ArrayList<String>();
        if (((List<String>)Arrays.asList(securityService.getSecurityInfo().getUserInfo().getUserRoles())).contains(SecurityConstants.rolEntidad)){
            //Rol de entidad
            logger.debug("Rol de entidad");
            listaClientes.add(this.allCustomers);
            
            for (String strClient : Arrays.asList((String[])securityService.getSecurityInfo().getUserInfo().getUserAttributes().get(SecurityConstants.attributeEntidades))){
                listaClientes.add(strClient);
            }
            
        }else{
            //Rol de Tirea
            logger.debug("Rol de Tirea");
            Pageable wholePage = PageRequest.of(0, Integer.MAX_VALUE);
            Page<AllClientsResponse> allClients = desaQueryExecutorService.executeAllClients(wholePage);
            listaClientes.add(this.allCustomers);
            for (AllClientsResponse oneClient : allClients.getContent()){
                listaClientes.add(oneClient.getIdCliente());
            }
        }
        
        //
        
        return listaClientes;
    }
    
    public List<String> getClientesSinTodos() {
        logger.debug("Starting getClientes");
        List<String> listaClientes = new ArrayList<String>();
        if (((List<String>)Arrays.asList(securityService.getSecurityInfo().getUserInfo().getUserRoles())).contains(SecurityConstants.rolEntidad)){
            //Rol de entidad
            logger.debug("Rol de entidad");
            for (String strClient : Arrays.asList((String[])securityService.getSecurityInfo().getUserInfo().getUserAttributes().get(SecurityConstants.attributeEntidades))){
                listaClientes.add(strClient);
            }
            
        }else{
            //Rol de Tirea
            logger.debug("Rol de Tirea");
            Pageable wholePage = PageRequest.of(0, Integer.MAX_VALUE);
            Page<AllClientsResponse> allClients = desaQueryExecutorService.executeAllClients(wholePage);
            for (AllClientsResponse oneClient : allClients.getContent()){
                listaClientes.add(oneClient.getIdCliente());
            }
        }
        
        //
        
        return listaClientes;
    }

}