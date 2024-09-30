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
public class clsTipoUsuario {

    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;

    public ResultSet listarTipoUsuario() throws Exception {
        strSQL = "SELECT * FROM TIPO_USUARIO";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar tipos de usuario: " + e.getMessage());
        }
    }

    public Integer obtenerTipoUsuario(String nom) throws Exception {
        strSQL = "SELECT * FROM TIPO_USUARIO WHERE NOMBRE ilike " + nom;
        try {
            rs = objConectar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener tipo usuario: " + e.getMessage());
        }
        return 0;
    }
}
