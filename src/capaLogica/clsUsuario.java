/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;
import capaDatos.clsJDBC;
import java.sql.ResultSet;

/**
 *
 * @author IVAN
 */
public class clsUsuario {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;
    
    public ResultSet login(String us, String pass) {
        strSQL = "";
        return rs;
    }
}
