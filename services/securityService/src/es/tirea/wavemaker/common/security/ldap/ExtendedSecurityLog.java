/*Copyright (c) 2020-2021 TIREA All Rights Reserved.
 This software is the confidential and proprietary information of TIREA You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with TIREA*/
package es.tirea.wavemaker.common.security.ldap;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.log4j.EnhancedPatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.wavemaker.runtime.security.SecurityService;
import es.tirea.wavemaker.common.security.ldap.SecurityConstants;

@Component
public class ExtendedSecurityLog extends EnhancedPatternLayout implements ApplicationContextAware{
    
    private static SecurityService securityService;
    
	public String format(LoggingEvent event){
	        
	    String user = "null";
	    String ip = "null";
	    String ou = "null";
	    String authorized = "No";
	    
	    if(securityService != null) {
            ou = (String)securityService.getSecurityInfo().getUserInfo().getUserAttributes().get(SecurityConstants.attribOu);
            authorized = "Si";
            user = securityService.getSecurityInfo().getUserInfo().getUserName();
            ip = (String)securityService.getSecurityInfo().getUserInfo().getUserAttributes().get(SecurityConstants.attribIP);
	    }
        
        event.setProperty("User", user);
        event.setProperty("AppName", "TireaConsultas");

        event.setProperty("ou", ou);
        event.setProperty("Authorized", authorized);
        event.setProperty("IpAddress", ip);
        
        event.setProperty("ServiceTypeUser", "LOGINEntidad");
        event.setProperty("File", "BBDD");
        event.setProperty("AccessType", "LOGIN");
        

        return super.format(event);
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (applicationContext.getAutowireCapableBeanFactory().getBean("securityService") != null) {
            securityService = (SecurityService) applicationContext.getAutowireCapableBeanFactory().getBean("securityService");
        }
    }
}
