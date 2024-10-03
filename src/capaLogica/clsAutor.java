/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.ResultSet;

/**
 *
 * @author Louis
 */
public class clsAutor {

    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;

    public ResultSet listarAutor() throws Exception {
        strSQL = "select *from listado_autor";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar autores -->" + e.getMessage());
        }
    }

    public Integer generarCodAutor() throws Exception {
        strSQL = "select COALESCE(MAX(codigo),0)+1 as codigo from autor";
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código -->" + e.getMessage());
        }
        return 0;
    }

    public void registrarAutor(int au, int pa, String nom, char sex) throws Exception {
        strSQL = "SELECT pa_insert_autor(" + au + ", " + pa + ", '" + nom + "', '" + sex + "')";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar autor -->" + e.getMessage());
        }
    }

    public ResultSet buscarAutor(int cod) throws Exception {
        strSQL = "select * from autor where codigo =" + cod;
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar autor -->" + e.getMessage());
        }
    }

    public void eliminarAutor(int cod) throws Exception {
        strSQL = "select pa_delete_autor (" + cod + ")";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al eliminar autor --> " + e.getMessage());
        }
    }

//    public int buscarAutorN(String nombre) throws Exception {
//        strSQL = "select codigo from autor where nombre ='" + nombre + "'";
//        try {
//            rs = objConectar.consultar(strSQL);
//            if (rs.next()) {
//                return rs.getInt("codigo");
//            }
//        } catch (Exception e) {
//            throw new Exception("Error al buscar autor -->" + e.getMessage());
//        }
//        return 0;
//    }

    public void actualizarAutor(int au, int pa, String nom, char sex) throws Exception {
        strSQL = "SELECT pa_update_autor(" + au + "," + pa + ", '" + nom + "', '" + sex + "')";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al actualizar autor --> " + e.getMessage());
        }
    }
    public ResultSet buscarAutorPorNombre(String nombre) throws Exception {
        strSQL = "select * from autor where nombre ilike '%"+nombre+"%'";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar autor -->" + e.getMessage());
        }
    }
}
