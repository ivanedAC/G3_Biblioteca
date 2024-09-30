/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



package capaDatos;

import java.sql.*;

/**
 *
 * @author ander
 */
public class clsJDBC {
    private String driver, url, user, password;
    private Connection con;
    private Statement sent=null;
    
    public clsJDBC(){
        this.driver = "org.postgresql.Driver";
        this.url = "jdbc:postgresql://25.59.167.63:5432/BDDAE_PROYECTO_BIBLIOTECA";
        this.user = "edgar";
        this.password = "1234";
        this.con = null;
    }
    
    public void conectar() throws Exception{
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new Exception("Error al conectar la base de datos " +ex);
        }
    }
    
    public void desconectar() throws Exception{
        try {
            con.close();
        } catch (SQLException ex) {
            throw new Exception("Error al desconectar la base de datos " +ex);
        }
    }
    
    public ResultSet consultar(String strSQL) throws Exception{
        ResultSet rs = null;
        try {
            conectar();
            sent = con.createStatement();
            rs = sent.executeQuery(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error en la conslulta  " + e.getMessage());
        } finally{
            if (con != null) {
                desconectar();
            }
        }    
    }
    
    //insert, update, delete
    public void ejecutar(String strSQL) throws Exception{
        try {
            conectar();
            sent = con.createStatement();
            sent.executeUpdate(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al ejecutar Update "+ e.getMessage());
        } finally {
            if (con != null) {
                desconectar();
            }
        }
    }
}
