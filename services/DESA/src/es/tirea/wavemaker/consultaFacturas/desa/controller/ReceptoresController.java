/*Copyright (c) 2020-2021 TIREA All Rights Reserved.
 This software is the confidential and proprietary information of TIREA You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with TIREA*/
package es.tirea.wavemaker.consultaFacturas.desa.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.commons.wrapper.StringWrapper;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.manager.ExportedFileManager;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.runtime.security.xss.XssDisable;
import com.wavemaker.tools.api.core.annotations.MapTo;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import es.tirea.wavemaker.consultaFacturas.desa.Facturas;
import es.tirea.wavemaker.consultaFacturas.desa.Receptores;
import es.tirea.wavemaker.consultaFacturas.desa.service.ReceptoresService;


/**
 * Controller object for domain model class Receptores.
 * @see Receptores
 */
@RestController("DESA.ReceptoresController")
@Api(value = "ReceptoresController", description = "Exposes APIs to work with Receptores resource.")
@RequestMapping("/DESA/Receptores")
public class ReceptoresController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceptoresController.class);

    @Autowired
	@Qualifier("DESA.ReceptoresService")
	private ReceptoresService receptoresService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new Receptores instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Receptores createReceptores(@RequestBody Receptores receptores) {
		LOGGER.debug("Create Receptores with information: {}" , receptores);

		receptores = receptoresService.create(receptores);
		LOGGER.debug("Created Receptores with information: {}" , receptores);

	    return receptores;
	}

    @ApiOperation(value = "Returns the Receptores instance associated with the given id.")
    @RequestMapping(value = "/{idReceptor:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Receptores getReceptores(@PathVariable("idReceptor") Integer idReceptor) {
        LOGGER.debug("Getting Receptores with id: {}" , idReceptor);

        Receptores foundReceptores = receptoresService.getById(idReceptor);
        LOGGER.debug("Receptores details with id: {}" , foundReceptores);

        return foundReceptores;
    }

    @ApiOperation(value = "Updates the Receptores instance associated with the given id.")
    @RequestMapping(value = "/{idReceptor:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Receptores editReceptores(@PathVariable("idReceptor") Integer idReceptor, @RequestBody Receptores receptores) {
        LOGGER.debug("Editing Receptores with id: {}" , receptores.getIdReceptor());

        receptores.setIdReceptor(idReceptor);
        receptores = receptoresService.update(receptores);
        LOGGER.debug("Receptores details with id: {}" , receptores);

        return receptores;
    }
    
    @ApiOperation(value = "Partially updates the Receptores instance associated with the given id.")
    @RequestMapping(value = "/{idReceptor:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Receptores patchReceptores(@PathVariable("idReceptor") Integer idReceptor, @RequestBody @MapTo(Receptores.class) Map<String, Object> receptoresPatch) {
        LOGGER.debug("Partially updating Receptores with id: {}" , idReceptor);

        Receptores receptores = receptoresService.partialUpdate(idReceptor, receptoresPatch);
        LOGGER.debug("Receptores details after partial update: {}" , receptores);

        return receptores;
    }

    @ApiOperation(value = "Deletes the Receptores instance associated with the given id.")
    @RequestMapping(value = "/{idReceptor:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteReceptores(@PathVariable("idReceptor") Integer idReceptor) {
        LOGGER.debug("Deleting Receptores with id: {}" , idReceptor);

        Receptores deletedReceptores = receptoresService.delete(idReceptor);

        return deletedReceptores != null;
    }

    /**
     * @deprecated Use {@link #findReceptores(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of Receptores instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @XssDisable
    public Page<Receptores> searchReceptoresByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Receptores list by query filter:{}", (Object) queryFilters);
        return receptoresService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Receptores instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Receptores> findReceptores(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Receptores list by filter:", query);
        return receptoresService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of Receptores instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @XssDisable
    public Page<Receptores> filterReceptores(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Receptores list by filter", query);
        return receptoresService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
    @RequestMapping(value = "/export/{exportType}", method = {RequestMethod.GET,  RequestMethod.POST}, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @XssDisable
    public Downloadable exportReceptores(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return receptoresService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @XssDisable
    public StringWrapper exportReceptoresAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = Receptores.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> receptoresService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of Receptores instances matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	@XssDisable
	public Long countReceptores( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting Receptores");
		return receptoresService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	@XssDisable
	public Page<Map<String, Object>> getReceptoresAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return receptoresService.getAggregatedValues(aggregationInfo, pageable);
    }

    @RequestMapping(value="/{idReceptor:.+}/facturases", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the facturases instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<Facturas> findAssociatedFacturases(@PathVariable("idReceptor") Integer idReceptor, Pageable pageable) {

        LOGGER.debug("Fetching all associated facturases");
        return receptoresService.findAssociatedFacturases(idReceptor, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service ReceptoresService instance
	 */
	protected void setReceptoresService(ReceptoresService service) {
		this.receptoresService = service;
	}

}