/*Copyright (c) 2020-2021 TIREA All Rights Reserved.
 This software is the confidential and proprietary information of TIREA You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with TIREA*/
package es.tirea.wavemaker.consultaFacturas.desa.service;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.security.model.SecurityInfo;

import es.tirea.wavemaker.consultaFacturas.desa.Facturas;
import es.tirea.wavemaker.common.security.ldap.SecurityConstants;

/**
 * ServiceImpl object for domain model class Facturas.
 *
 * @see Facturas
 */
public class FacturasServiceTireaImpl extends FacturasServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacturasServiceTireaImpl.class);
    
    @Autowired
    private SecurityService securityService;
    
    @Transactional(readOnly = true, value = "DESATransactionManager")
    @Override
    public Page<Facturas> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Facturas with data security");
        String q = null;
        //Add data user security
        if(securityService.isAuthenticated()) {
            if (((List<String>)Arrays.asList(securityService.getSecurityInfo().getUserInfo().getUserRoles())).contains(SecurityConstants.rolEntidad)){
            //Rol de entidad
            //Construye la query para la seguridad a nivel de datos
                q = ((query!="" && query!= null)?query + " AND ":"") + "idCliente IN (";
            
                String[] listaClientes = (String[])securityService.getSecurityInfo().getUserInfo().getUserAttributes().get(SecurityConstants.attributeEntidades);
            
                for (String idCliente : listaClientes) {
                    q = q + "'" +idCliente + "' ,";
                }
                if (listaClientes.length == 0)
                    q = q + "'-1')";
                else
                    q = q.substring(0, q.length() - 2) + ")";
            }else{
                q = query;
                LOGGER.warn("Query======="+q);
            }
        }else{
            q = "idCliente = -99";
        }
        LOGGER.warn("Query======="+q);
        return super.findAll(q, pageable);
    }
}
