/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.ResultSet;

/**
 *
 * @author ACER
 */
public class clsSede {

    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;

    public ResultSet listarSede() throws Exception {
        strSQL = "SELECT * FROM SEDE";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar sedes: " + e.getMessage());
        }
    }
    
    public Integer obtenerSede(String nom) throws Exception {
        strSQL = "SELECT * FROM SEDE WHERE NOMBRE ilike '" + nom + "'";
        try {
            rs = objConectar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener sede: " + e.getMessage());
        }
        return 0;
    }

}
