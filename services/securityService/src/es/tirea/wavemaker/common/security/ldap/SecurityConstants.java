/*Copyright (c) 2020-2021 TIREA All Rights Reserved.
 This software is the confidential and proprietary information of TIREA You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with TIREA*/
package es.tirea.wavemaker.common.security.ldap;

public final class SecurityConstants  {
 
    public static final String loginOK = "0 - OK";
    public static final String rolTirea = "TIREA";
    public static final String rolEntidad = "ENTIDAD";
    public static final String attributeEntidades = "entidades";
    public static final String accesoFacturas = "SI";
    public static final String[] attribRestLdap = {"FacturaAcceso","FacturaPerfil","FacturaCliente", "POCEntidad", "FacturaEntidad"};
    public static final String attribOu = "ou";
    public static final String attribFacturaPerfil = "facturaPerfil";
    public static final String attribFacturaAcceso = "facturaAcceso";
    public static final String attribIP = "attribIP";
    public static final String attribSessionId = "attribSessionId";
}
