/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author ander
 */
public class clsOrdenCompra {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;
    Connection con = null;
    Statement sent = null;

    clsSede objSede = new clsSede();
    clsCliente objCli = new clsCliente();
    clsEjemplar objEjem = new clsEjemplar();
    
    public Integer generarCorrelativo() throws Exception {
        strSQL = "select COALESCE(Max(correlativo),0)+1 as codigo from orden_compra";
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar correlativo de compra: " + e.getMessage());
        }
        return 0;
    }
    
    
    
    public Integer generarCodCompra() throws Exception {
        try {
            String cod = "" + clsUsuarioSTATIC.codigo + objSede.obtenerSede(clsUsuarioSTATIC.sede) + generarCorrelativo();
            return Integer.valueOf(cod);
        } catch (Exception e) {
            throw new Exception("Error al generar código de la compra: " + e.getMessage());
        }
    }
}
