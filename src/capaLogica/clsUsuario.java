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
    
    public ResultSet login(String us, String pass) throws Exception {
        strSQL = "SELECT * FROM fn_login('"+us+"','"+pass+"')";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al intentar logearse" + e.getMessage());
        }
    }
    public int validar_vigencia(int id) throws Exception {
        strSQL = "SELECT fn_validar_vigencia("+id+") as resultado";
        try {
            rs = objConectar.consultar(strSQL);
            if(rs.next()){
                return rs.getInt("resultado");
            }
            else {
                return -1;
            }
        } catch (Exception e) {
            throw new Exception("Error al validadr vigencia" + e.getMessage());
        }
    }
}
