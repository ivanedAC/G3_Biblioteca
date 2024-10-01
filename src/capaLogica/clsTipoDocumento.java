/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.ResultSet;


public class clsTipoDocumento {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;

    public ResultSet listarTipoDocumento() throws Exception {
        strSQL = "select *from tipo_documento";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar tipodocumento -->" + e.getMessage());
        }
    }
    
    public String nombreTipoDocumento(int cod)throws Exception{
        strSQL="select nombre from tipo_documento where codigo="+cod;
        String nombretipoDoc="";
        try {
            rs=objConectar.consultar(strSQL);
            while (rs.next()) {                
                nombretipoDoc=rs.getString("nombre");
                return nombretipoDoc;
            }
        } catch (Exception e) {
            throw new Exception("error al obtener el nombre del tipo de documento-->"+e.getMessage());
        }
        return "";
    }
    
    public Integer obtenerTipoDocumento(String nom) throws Exception {
        strSQL = "SELECT * FROM TIPO_DOCUMENTO WHERE NOMBRE ilike '" + nom + "'";
        try {
            rs = objConectar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener tipo usuario: " + e.getMessage());
        }
        return 0;
    }
}
