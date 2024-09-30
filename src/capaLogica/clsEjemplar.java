/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.*;

/**
 *
 * @author ander
 */
public class clsEjemplar {
    String strSQL;
    clsJDBC objConetar = new clsJDBC();
    ResultSet rs = null;
    
    public ResultSet listar() throws Exception{
        strSQL = "select * from ejemplar";
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar ejemplares --> " +e.getMessage());
        }
    }
    
    
    
    
}
