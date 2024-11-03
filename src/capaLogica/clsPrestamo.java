/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.*;
import javax.swing.JTable;

/**
 *
 * @author ACER
 */
public class clsPrestamo {

    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;
    Connection con = null;
    Statement sent = null;
    
    clsSede objSede = new clsSede();

    public Integer generarCodPrestamo() throws Exception {
        strSQL = "select COALESCE(Max(codigo),0)+1 as codigo from prestamo";
        Integer codPre = -1;
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {
                codPre = rs.getInt("codigo");
            }
            String cod = "" + clsUsuarioSTATIC.codigo + objSede.obtenerSede(clsUsuarioSTATIC.sede) + codPre;
            return Integer.valueOf(cod);
        } catch (Exception e) {
            throw new Exception("Error al generar código de préstamo: " + e.getMessage());
        }
    }
    
    public void registrarPrestamo(String codPre, String cli, String f_limite, String h_limite) throws Exception {
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            con.setAutoCommit(false);
            sent = con.createStatement();
            strSQL = "INSERT INTO PRESTAMO values ("+ codPre + "," + clsUsuarioSTATIC.codigo + "," + 
                    cli + ", CURRENT_DATE,CURRENT_TIME,'" + f_limite + "','" + h_limite + "','" + "P";
        } catch (Exception e) {
        }
    }
    
    

}
