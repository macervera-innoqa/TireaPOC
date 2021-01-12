/*Copyright (c) 2020-2021 TIREA All Rights Reserved.
 This software is the confidential and proprietary information of TIREA You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with TIREA*/
package es.tirea.wavemaker.consultaFacturas.desa.service;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.security.model.SecurityInfo;

import es.tirea.wavemaker.common.security.ldap.SecurityConstants;

import es.tirea.wavemaker.consultaFacturas.desa.models.query.*;

public class DESAQueryExecutorServiceTireaImpl extends DESAQueryExecutorServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(DESAQueryExecutorServiceTireaImpl.class);
    private static final String allCustomers = "Todos";
    
    @Autowired
    private SecurityService securityService;
    
    @Transactional(value = "DESATransactionManager", readOnly = true)
    @Override
    public Page<FacturaGraphResponse> executeFacturaGraph(Boolean _all, List<String> idCliente,String fechaIni, String fechaFin, Pageable pageable) {
        LOGGER.debug("executeFacturaGraph with data security");
        Boolean myAll = true;
        List<String> myIdCliente = new ArrayList<String>();
        if (idCliente.size() == 1 && !idCliente.get(0).equals(allCustomers)){
            //El usuario ha seleccionado un cliente
            myAll = false;
            myIdCliente.add(idCliente.get(0));
        }else if (((List<String>)Arrays.asList(securityService.getSecurityInfo().getUserInfo().getUserRoles())).contains(SecurityConstants.rolEntidad)){
            //Rol de Entidad
            myAll = false;
            String[] listaClientes = (String[])securityService.getSecurityInfo().getUserInfo().getUserAttributes().get(SecurityConstants.attributeEntidades);
            for (String cliente : listaClientes) {
                myIdCliente.add(cliente);
            }
        }else if (((List<String>)Arrays.asList(securityService.getSecurityInfo().getUserInfo().getUserRoles())).contains(SecurityConstants.rolTirea)){
            //Rol Tirea  
            myAll = true;
            myIdCliente.add("dummy");
        }
        
        return super.executeFacturaGraph(myAll, myIdCliente, fechaIni, fechaFin, pageable);
        
    }
    
    @Transactional(value = "DESATransactionManager", readOnly = true)
    @Override
    public Page<FacturasClienteResponse> executeFacturasCliente(Boolean _all, List<String> idCliente, String fechaIni, String fechaFin, Pageable pageable) {
        LOGGER.debug("executeFacturasCliente with data security");
        Boolean myAll = true;
        List<String> myIdCliente = new ArrayList<String>();
        if (idCliente.size() == 1 && !idCliente.get(0).equals(allCustomers)){
            //El usuario ha seleccionado un cliente
            myAll = false;
            myIdCliente.add(idCliente.get(0));
        }else if (((List<String>)Arrays.asList(securityService.getSecurityInfo().getUserInfo().getUserRoles())).contains(SecurityConstants.rolEntidad)){
            //Rol de Entidad
            myAll = false;
            String[] listaClientes = (String[])securityService.getSecurityInfo().getUserInfo().getUserAttributes().get(SecurityConstants.attributeEntidades);
            for (String cliente : listaClientes) {
                myIdCliente.add(cliente);
            }
        }else if (((List<String>)Arrays.asList(securityService.getSecurityInfo().getUserInfo().getUserRoles())).contains(SecurityConstants.rolTirea)){
            //Rol Tirea  
            myAll = true;
            myIdCliente.add("dummy");
        }
        
        return super.executeFacturasCliente(myAll, myIdCliente, fechaIni, fechaFin, pageable);
    }

}
