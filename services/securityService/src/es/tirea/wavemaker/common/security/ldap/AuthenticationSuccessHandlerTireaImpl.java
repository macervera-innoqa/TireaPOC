/*Copyright (c) 2020-2021 TIREA All Rights Reserved.
 This software is the confidential and proprietary information of TIREA You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with TIREA*/
package es.tirea.wavemaker.common.security.ldap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.runtime.security.handler.WMAuthenticationSuccessHandler;
import com.wavemaker.runtime.security.WMAuthentication;
import com.wavemaker.runtime.security.Attribute;
import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.security.model.UserInfo;
import com.wavemaker.runtime.security.model.SecurityInfo;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class AuthenticationSuccessHandlerTireaImpl implements WMAuthenticationSuccessHandler {

 private static final Logger classLogger=LoggerFactory.getLogger("auditoria");
 
 @Autowired
 private ExtendedUserInfo extendedUserInfo;
 
 @Autowired
 private SecurityService securityService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, WMAuthentication authentication) throws IOException, ServletException {
        //recupera el rol del usuario
        UserInfo userInfo = securityService.getSecurityInfo().getUserInfo();
        String ip=request.getRemoteAddr();
        String sessionId = request.getSession().getId();
        if (Arrays.asList(userInfo.getUserRoles()).contains(SecurityConstants.rolEntidad)){
            //Si el usuario no es administrador sube a la sesion las entidades a las que tiene permisos de consulta
            authentication.addAttribute(SecurityConstants.attributeEntidades, extendedUserInfo.getEntidades(userInfo.getUserId()), Attribute.AttributeScope.ALL);
        }
        authentication.addAttribute(SecurityConstants.attribOu, extendedUserInfo.getOU(userInfo.getUserId()), Attribute.AttributeScope.ALL);
        authentication.addAttribute(SecurityConstants.attribFacturaPerfil, extendedUserInfo.getFacturaPerfil(userInfo.getUserId()), Attribute.AttributeScope.ALL);
        authentication.addAttribute(SecurityConstants.attribFacturaAcceso, extendedUserInfo.getFacturaAcceso(userInfo.getUserId()), Attribute.AttributeScope.ALL);
        authentication.addAttribute(SecurityConstants.attribIP, ip, Attribute.AttributeScope.ALL);
        authentication.addAttribute(SecurityConstants.attribSessionId, sessionId, Attribute.AttributeScope.ALL);
        
        classLogger.debug("Usuario autenticado");
    }
}
