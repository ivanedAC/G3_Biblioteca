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
public class clsEditorial {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;
    
    public ResultSet listarEditorial() throws Exception {
        strSQL = "SELECT * FROM EDITORIAL";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar las editoriales: "+e.getMessage());
        }
    }
    
    public void insertarEditorial(int cod, String nom, boolean vig) throws Exception{
        strSQL = "select pa_insert_editorial ("+cod+", '"+nom+"', "+vig+")";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Erro al regitrar --->" +e.getMessage());
        }
    }
    
    public void modficarEditorial(int cod, String nom, boolean vig) throws Exception{
        strSQL = "select pa_update_editorial ("+cod+", '"+nom+"', "+vig+")";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error a modificar --->" + e.getMessage());
        }
    }
    
    public void eliminarEditorial(int cod) throws Exception{
        strSQL= "select pa_delete_editorial("+cod+")";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al eliminar ---> " + e.getMessage());
        }
    }
    
    public void darBajaEditorial(int cod) throws Exception{
        strSQL= "select pa_disabled_editorial("+cod+")";
        try {
            objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al dar de baja --->" +e.getMessage());
        }
    }
    
    public ResultSet buscarEditorial(int cod) throws Exception{
        strSQL = "select * from editorial where codigo="+cod;
        try {
            rs=objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar --->" +e.getMessage());
        }
    }
    
    public Integer generarCodigoEditorial() throws Exception{
        strSQL = "select COALESCE(MAX(codigo), 0) + 1 as codigo from editorial";
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {                
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar codigo --->" +e.getMessage());
        }
        return 0;
    }
}
