/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.*;

/**
 *
 * @author ACER
 */
public class clsSancion {

    String strSQL;
    clsJDBC objConetar = new clsJDBC();
    ResultSet rs = null;

    public ResultSet listarSancionesSin1() throws Exception {
        strSQL = "select * from sancion where codigo != 1";
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar sanciones --->" + e.getMessage());
        }
    }
    
    public ResultSet listarSanciones() throws Exception {
        strSQL = "select * from sancion";
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar sanciones --->" + e.getMessage());
        }
    }
}
