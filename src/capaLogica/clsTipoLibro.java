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
public class clsTipoLibro {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;

    public ResultSet listarTipoLibro() throws Exception {
        strSQL = "SELECT * FROM TIPO_LIBRO";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar los tipos de libro: " + e.getMessage());
        }
    }
    public ResultSet buscarTipoLibroPorNombre(String nom) throws Exception{
        strSQL = "select * from tipo_libro where nombre='"+nom+"'";
        try {
            rs=objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar el tipo de libro--->" +e.getMessage());
        }
    }
    public Integer obtenerCodigoTipoLibro(String nom) throws Exception{
        strSQL = "select * from tipo_libro where nombre='"+nom+"'";
        try {
            rs=objConectar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener codigo de tipo libro ---> "+e.getMessage());
        }
        return 0;
    }
}
