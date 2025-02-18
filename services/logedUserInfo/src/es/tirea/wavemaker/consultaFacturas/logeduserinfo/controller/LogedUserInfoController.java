package es.tirea.wavemaker.consultaFacturas.logeduserinfo.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import es.tirea.wavemaker.consultaFacturas.logeduserinfo.LogedUserInfo;

/**
 * Controller object for domain model class {@link LogedUserInfo}.
 * @see LogedUserInfo
 */
@RestController
@Api(value = "LogedUserInfoController", description = "controller class for java service execution")
@RequestMapping("/logedUserInfo")
public class LogedUserInfoController {

    @Autowired
    private LogedUserInfo logedUserInfo;

    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @RequestMapping(value = "/clientes", method = RequestMethod.GET)
    public List<String> getClientes() {
        return logedUserInfo.getClientes();
    }

    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @RequestMapping(value = "/clientesSinTodos", method = RequestMethod.GET)
    public List<String> getClientesSinTodos() {
        return logedUserInfo.getClientesSinTodos();
    }
}

