/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.ResultSet;

/**
 *
 * @author Louis
 */
public class clsPais {

    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;

    public ResultSet listarPais() throws Exception {
        strSQL = "select *from pais";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar paises -->" + e.getMessage());
        }
    }

    public String buscarPaisPorCodigo(int codigo) throws Exception {
        strSQL = "select nombre from pais where codigo=" + codigo;
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {
                String nombrePais = rs.getString("nombre");
                return nombrePais;
            }
        } catch (Exception e) {
            throw new Exception("Error al encontrar pais por codigo-->" + e.getMessage());
        }
        return "";
    }
}
