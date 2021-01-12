/*Copyright (c) 2020-2021 TIREA All Rights Reserved.
 This software is the confidential and proprietary information of TIREA You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with TIREA*/
package es.tirea.wavemaker.common.security.ldap;

import java.io.Serializable;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtendedUserInfo implements Serializable{

 private static final Logger logger=LoggerFactory.getLogger(ExtendedUserInfo.class);
 
 private HashMap<String, String[]> entidades;
 private HashMap<String, String> ou;
 private HashMap<String, String> facturaPerfil;
 private HashMap<String, String> facturaAcceso;;
  
 public String[] getEntidades(String uuid){
     String[] returnValue = this.entidades.get(uuid);
     this.entidades.remove(uuid);
     return returnValue;
 }
 public void setEntidades(String uuid, String[] entidades){
     if (this.entidades == null)  this.entidades = new HashMap<String, String[]>();
     this.entidades.put(uuid, entidades);
     
 }

 public String getOU(String uuid){
    String returnValue = ou.get(uuid);
    this.ou.remove(uuid);
    return returnValue;
 }

 public void setOU(String uuid, String ou){
    if(this.ou == null) this.ou = new HashMap<String, String>();
    this.ou.put(uuid, ou);
 }
 public String getFacturaPerfil(String uuid){
    String returnValue = facturaPerfil.get(uuid);
    this.facturaPerfil.remove(uuid);
    return returnValue;
 }

 public void setFacturaPerfil(String uuid, String facturaPerfil){
    if(this.facturaPerfil == null) this.facturaPerfil = new HashMap<String, String>();
    this.facturaPerfil.put(uuid, facturaPerfil);
 }
 
 public String getFacturaAcceso(String uuid){
    String returnValue = facturaAcceso.get(uuid);
    this.facturaAcceso.remove(uuid);
    return returnValue;
 }

 public void setFacturaAcceso(String uuid, String facturaAcceso){
    if(this.facturaAcceso == null) this.facturaAcceso = new HashMap<String, String>();
    this.facturaAcceso.put(uuid, facturaAcceso);
 }
 
}
