/*Copyright (c) 2020-2021 TIREA All Rights Reserved.
 This software is the confidential and proprietary information of TIREA You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with TIREA*/
package es.tirea.wavemaker.consultaFacturas.desa.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wavemaker.runtime.data.dao.query.WMQueryExecutor;
import com.wavemaker.runtime.data.export.ExportOptions;
import com.wavemaker.runtime.data.model.QueryProcedureInput;

import es.tirea.wavemaker.consultaFacturas.desa.models.query.*;

@Service
public class DESAQueryExecutorServiceImpl implements DESAQueryExecutorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DESAQueryExecutorServiceImpl.class);

    @Autowired
    @Qualifier("DESAWMQueryExecutor")
    private WMQueryExecutor queryExecutor;

    @Transactional(value = "DESATransactionManager", readOnly = true)
    @Override
    public Page<FacturasClienteResponse> executeFacturasCliente(Boolean _all, List<String> idCliente, String fechaIni, String fechaFin, Pageable pageable) {
        Map<String, Object> params = new HashMap<>(4);

        params.put("all", _all);
        params.put("idCliente", idCliente);
        params.put("fechaIni", fechaIni);
        params.put("fechaFin", fechaFin);

        return queryExecutor.executeNamedQuery("facturasCliente", params, FacturasClienteResponse.class, pageable);
    }

    @Transactional(value = "DESATransactionManager", timeout = 300, readOnly = true)
    @Override
    public void exportFacturasCliente(Boolean _all, List<String> idCliente, String fechaIni, String fechaFin, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream) {
        Map<String, Object> params = new HashMap<>(4);

        params.put("all", _all);
        params.put("idCliente", idCliente);
        params.put("fechaIni", fechaIni);
        params.put("fechaFin", fechaFin);

        QueryProcedureInput<FacturasClienteResponse> queryInput = new QueryProcedureInput<>("facturasCliente", params, FacturasClienteResponse.class);

        queryExecutor.exportNamedQueryData(queryInput, exportOptions, pageable, outputStream);
    }

    @Transactional(value = "DESATransactionManager", readOnly = true)
    @Override
    public Page<AllClientsResponse> executeAllClients(Pageable pageable) {
        Map<String, Object> params = new HashMap<>(0);


        return queryExecutor.executeNamedQuery("allClients", params, AllClientsResponse.class, pageable);
    }

    @Transactional(value = "DESATransactionManager", timeout = 300, readOnly = true)
    @Override
    public void exportAllClients(ExportOptions exportOptions, Pageable pageable, OutputStream outputStream) {
        Map<String, Object> params = new HashMap<>(0);


        QueryProcedureInput<AllClientsResponse> queryInput = new QueryProcedureInput<>("allClients", params, AllClientsResponse.class);

        queryExecutor.exportNamedQueryData(queryInput, exportOptions, pageable, outputStream);
    }

    @Transactional(value = "DESATransactionManager", readOnly = true)
    @Override
    public Page<FacturaGraphResponse> executeFacturaGraph(Boolean _all, List<String> idCliente, String fechaIni, String fechaFin, Pageable pageable) {
        Map<String, Object> params = new HashMap<>(4);

        params.put("all", _all);
        params.put("idCliente", idCliente);
        params.put("fechaIni", fechaIni);
        params.put("fechaFin", fechaFin);

        return queryExecutor.executeNamedQuery("facturaGraph", params, FacturaGraphResponse.class, pageable);
    }

    @Transactional(value = "DESATransactionManager", timeout = 300, readOnly = true)
    @Override
    public void exportFacturaGraph(Boolean _all, List<String> idCliente, String fechaIni, String fechaFin, ExportOptions exportOptions, Pageable pageable, OutputStream outputStream) {
        Map<String, Object> params = new HashMap<>(4);

        params.put("all", _all);
        params.put("idCliente", idCliente);
        params.put("fechaIni", fechaIni);
        params.put("fechaFin", fechaFin);

        QueryProcedureInput<FacturaGraphResponse> queryInput = new QueryProcedureInput<>("facturaGraph", params, FacturaGraphResponse.class);

        queryExecutor.exportNamedQueryData(queryInput, exportOptions, pageable, outputStream);
    }

}