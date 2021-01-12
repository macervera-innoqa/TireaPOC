/*Copyright (c) 2020-2021 TIREA All Rights Reserved.
 This software is the confidential and proprietary information of TIREA You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with TIREA*/
package es.tirea.wavemaker.common.security.ldap;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;
import java.lang.reflect.Field;
import org.apache.log4j.Level;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

import com.wavemaker.runtime.security.AuthRequestContext;
import com.wavemaker.runtime.security.WMCustomAuthenticationManager;
import com.wavemaker.runtime.security.WMUser;

import org.springframework.security.core.AuthenticationException;

import tirea.base.ldap.AnalizadorLDAP;

public class Access implements WMCustomAuthenticationManager {

 private static final Logger logger=LoggerFactory.getLogger(Access.class);
 private static final Logger classLogger=LoggerFactory.getLogger("auditoria");
 private static final org.apache.log4j.Logger logger4j = org.apache.log4j.Logger.getLogger("seguridad");
 
 @Value("${app.environment.LDAPHost}")
 private String ldapHost;
 
 @Value("${app.environment.LDAPPort}")
 private Integer ldapPort;
 
 @Value("${app.environment.LDAPBase}")
 private String ldapBase;
 
 @Value("${app.environment.LDAPAtributoAcceso}")
 private String ldapAtributoAcceso;
 
 @Autowired
 private ExtendedUserInfo extendedUserInfo;

 protected org.apache.log4j.Logger getLog4jLogger(Logger logger) {
      Field log4jField = null;
      try {
            Field[] fields = Log4jLoggerAdapter.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if ("logger".equals(field.getName())) {
                    log4jField = field;
                    log4jField.setAccessible(true);
                }
            }
        } catch (Exception e) { 
            logger.warn(e.toString());
            return null;
        }
        
        try {
            Log4jLoggerAdapter adapter = (Log4jLoggerAdapter) logger;
            return (org.apache.log4j.Logger) log4jField.get(adapter);
        } catch (Exception e) {
            logger.warn(e.toString());
            return null;
        }
    }
    
 public WMUser authenticate(AuthRequestContext authRequestContext) throws AuthenticationException {
        
        AnalizadorLDAP instance = new AnalizadorLDAP();
        String ldapUser = authRequestContext.getUsername();
        String ldapPass = authRequestContext.getPassword();
        
        //Workaround Jboss
        //org.apache.log4j.Logger loggerSeg = getLog4jLogger(logger);
        instance.setLogger(logger4j);
        
        HashMap<String, Object> result = instance.logarUsuario(ldapUser, ldapPass, ldapHost, ldapPort, ldapBase, SecurityConstants.attribRestLdap);
        String respuesta = (String)result.get("respuesta");
        
        if (respuesta.equals(SecurityConstants.loginOK)){
            String uuid          = ((String[])result.get("uid"))[0];
            String ou            = ((String[])result.get("ou"))[0];
            String facturaPerfil = ((String[])result.get("FacturaPerfil"))[0];
            String facturaAcceso = ((String[])result.get("FacturaAcceso"))[0];

            extendedUserInfo.setOU(uuid, ou);
            extendedUserInfo.setFacturaPerfil(uuid, facturaPerfil);
            extendedUserInfo.setFacturaAcceso(uuid, facturaAcceso);
            
            if (!facturaPerfil.equals(SecurityConstants.rolTirea)){
                extendedUserInfo.setEntidades(uuid, (String[])result.get("FacturaCliente"));
            }
            
            if (facturaAcceso.equals(SecurityConstants.accesoFacturas)){
                ArrayList<String> rol = new ArrayList<>();
                rol.add(facturaPerfil);

                WMUser userLogged = new WMUser(ldapUser, ldapPass, rol);
                
                userLogged.setUserId(uuid);
                
                return userLogged;
            } else {
                classLogger.error("El usuario no tiene acceso a facturas");
                throw new BadCredentialsException("El usuario no tiene acceso a Facturas [accesoFacturas=NO]");
            }
        }else{
            classLogger.error("Error autenticando al usuario");
            throw new BadCredentialsException(respuesta);
        }
        
    }
}

