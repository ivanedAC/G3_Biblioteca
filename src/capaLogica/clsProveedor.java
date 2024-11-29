/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.ResultSet;

/**
 *
 * @author Chinc
 */
public class clsProveedor {

    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;

    public int generarCodigoProveedor() throws Exception {
        strSQL = "select coalesce(max(codigo),0)+1 as codigo from proveedor";
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar codigo de Proveedor---->" + e.getMessage());
        }
        return 0;
    }

    public int buscarCodigoProveedor(String ndoc) throws Exception {
        strSQL = "select Pro.codigo as codigo from proveedor Pro inner join persona P on Pro.cod_persona=P.codigo where P.numero_documento = '" + ndoc + "'";
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar codigo de Persona---->" + e.getMessage());
        }
        return 0;
    }

    public int buscarCodigoPersona(String ndoc) throws Exception {
        strSQL = "select codigo from persona where numero_documento = '" + ndoc + "'";
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar codigo de Persona---->" + e.getMessage());
        }
        return 0;
    }
    
    public ResultSet obtenerPersonaPorDoc(String doc) throws Exception{
        strSQL = "select * from persona where numero_documento='"+doc+"'";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
            
        } catch (Exception e) {
            throw new Exception("Error al buscar persona-->"+e.getMessage());
        }
    }

    public void insertarProveedorExistente(String ndoc, String estado) throws Exception {
        int cod_per = buscarCodigoPersona(ndoc);
        strSQL = "insert into proveedor (cod_persona,estado)values (" + cod_per + ",'" + estado + "')";
        try {
            objConectar.ejecutar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al insertar proveedor existente-->" + e.getMessage());
        }

    }

    public boolean buscarSexoPorNDoc(String ndoc) throws Exception {
        strSQL = "select P.sexo as sexo from proveedor Pro inner join persona P on Pro.cod_persona=P.codigo where P.numero_documento ='" + ndoc + "'";
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {
                return rs.getBoolean("sexo");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar el sexo--->" + e.getMessage());
        }
        return true;
    }

    public ResultSet obtenerProveedorPorDoc(String doc) throws Exception {
        strSQL = "select P.*, Pro.estado as estado from proveedor Pro inner join persona P on Pro.cod_persona=P.codigo where P.numero_documento='" + doc + "'";
        try {
            rs = objConectar.consultar(strSQL);

            return rs;

        } catch (Exception e) {
            throw new Exception("Error al buscar Proveedor-->" + e.getMessage());
        }
    }
    
    public ResultSet buscarProveedorPorCodigo(Integer cod) throws Exception {
        strSQL = "select pro.codigo as cod_proveedor, pro.estado, per.* from \n"
                + "proveedor pro inner join persona per on per.codigo = pro.cod_persona"
                + " where pro.codigo =" + cod;
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar cliente: " + e.getMessage());
        }
    }

    public ResultSet listarProveedorN() throws Exception {
        strSQL = "select PR.codigo, Pa.nombre as pais, TD.nombre as tipo_documento, Pe.numero_documento as numero_documento, Pe.nombres, (Pe.ape_paterno || ' ' || Pe.ape_materno) as apellidos,\n" +
"	Pe.sexo, Pe.f_nacimiento, Pe.direccion, Pe.telefono, Pe.correo, PR.estado\n" +
"	from persona Pe    \n" +
"	inner join proveedor PR on Pe.codigo=PR.cod_persona \n" +
"	inner join pais Pa on Pa.codigo=Pe.cod_pais\n" +
"	inner join tipo_documento TD on Pe.cod_tipo_doc=TD.codigo\n" +
"	where Pe.cod_tipo_doc !=2 \n"
                + "order by 1";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar Proveedor Natural-->" + e.getMessage());
        }
    }

    public ResultSet listarProveedorJ() throws Exception {
        strSQL = "select PR.codigo, Pa.nombre as pais, TD.nombre as tipo_documento, Pe.numero_documento as numero_documento, Pe.nombres, Pe.razon_social,\n" +
"	Pe.direccion, Pe.telefono, Pe.correo, PR.estado\n" +
"	from persona Pe    \n" +
"	inner join proveedor PR on Pe.codigo=PR.cod_persona \n" +
"	inner join pais Pa on Pa.codigo=Pe.cod_pais\n" +
"	inner join tipo_documento TD on Pe.cod_tipo_doc=TD.codigo\n" +
"	where Pe.cod_tipo_doc =2 \n"
                + "order by 1";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar cliente Juridico-->" + e.getMessage());
        }
    }

    public void insertarProveedorJuridico(int cpais, int ctipodoc, String ndoc, String nom, String rs, String dir, String cel, String f_reg, String correo, String estado) throws Exception {
        int cod = generarCodigoProveedor();

        strSQL = "SELECT pa_insert_proveedorjuridica(" + cod + "," + cpais + "," + ctipodoc + ",'" + ndoc + "' , '" + nom + "', '" + rs + "', '" + dir + "', '" + cel + "' , '" + f_reg + "', '" + correo + "' , '" + estado + "')";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al insertar Cliente Juridico-->" + e.getMessage());
        }
    }

    public void insertarProveedorNatural(int cpais, int ctipodoc, String ndoc, String nom, String ape_pat, String ape_mat, boolean sex, String f_nac, String dir, String cel, String f_reg, String correo, String estado) throws Exception {
        int cod = generarCodigoProveedor();
        strSQL = "SELECT pa_insert_proveedornatural(" + cod + "," + cpais + "," + ctipodoc + ", '" + ndoc + "' , '" + nom + "' , '" + ape_pat + "' , '" + ape_mat + "' ," + sex + ", '" + f_nac + "' , '" + dir + "', '" + cel + "', '" + f_reg + "' , '" + correo + "' ,'" + estado + "')";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al insertar Proveedor Natural-->" + e.getMessage());
        }
    }

    public void modificarProveedorJuridico(int cpais, int ctipodoc, String ndoc, String nom, String rs, String dir, String cel, String f_reg, String correo, String estado) throws Exception {
        int cod = buscarCodigoProveedor(ndoc);
        strSQL = "select pa_update_proveedorjuridica(" + cod + "," + cpais + "," + ctipodoc + ", '" + ndoc + "' , '" + nom + "', '" + rs + "' , '" + dir + "' , '" + cel + "' , '" + f_reg + "' , '" + correo + "','" + estado + "')";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al modificar cliente juridico-->" + e.getMessage());
        }
    }

    public void modificarProveedorNatural(int cpais, int ctipodoc, String ndoc, String nom, String ape_pa, String ape_ma, String f_nac, String direccion, String telefono, String f_reg, String correo, String estado,boolean sex) throws Exception {
        int cod = buscarCodigoProveedor(ndoc);
        strSQL = "select pa_update_proveedornatural(" + cod + "," + cpais + "," + ctipodoc + ",'" + ndoc + "', '" + nom + "', '" + ape_pa + "', '" + ape_ma + "', " + sex + ", '" + f_nac + "', '" + direccion + "', '" + telefono + "', '" + f_reg + "','" + correo + "', '" + estado + "')";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al modificar cliente natural-->" + e.getMessage());
        }
    }

    public void darBajaProveedor(String ndoc) throws Exception {
        int cod = buscarCodigoProveedor(ndoc);
        strSQL = "select pa_disabled_proveedor(" + cod + ")";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al dar de baja cliente-->" + e.getMessage());
        }
    }

    public void eliminarProveedor(String ndoc) throws Exception {
        int cod = buscarCodigoProveedor(ndoc);
        strSQL = "delete from proveedor where codigo=" + cod;
        try {
            objConectar.ejecutar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al dar de baja proveedor-->" + e.getMessage());
        }
    }

    public int buscarProveedor(int codigo) throws Exception {
        strSQL = "select Pro.codigo as codigo from proveedor Pro inner join persona P on Pro.cod_persona=P.codigo where P.numero_documento = '" + codigo + "'";
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar codigo de Persona---->" + e.getMessage());
        }
        return 0;
    }
}
