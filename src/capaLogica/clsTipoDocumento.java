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
}
