/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



package clsDatos;

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
        this.url = "jdbc:postgresql://localhost:5432/*";
        this.user = "postgres";
        this.password = "*";
        this.con = null;
    }
}
