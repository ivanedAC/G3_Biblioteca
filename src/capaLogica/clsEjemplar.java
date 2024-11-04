/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.*;

/**
 *
 * @author ander
 */
public class clsEjemplar {

    String strSQL;
    clsJDBC objConetar = new clsJDBC();
    ResultSet rs = null;

    public ResultSet listarEjemplarSedeIndiv() throws Exception{
        strSQL = "select * from listadoEjemplares";
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar Ejemplares de manera individual --->" +e.getMessage());
        }
    }

    public ResultSet buscarPorCodigo(int cod) throws Exception {
        strSQL = "select * from listadoEjemplares where codigo="+cod;
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al obterner codigo ---> " + e.getMessage());
        }
    }
    
    public void darBajaEditorial(int cod) throws Exception{
        strSQL = "select pa_disabled_ejemplar("+cod+")";
        try {
            objConetar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al dar de baja ---> " +e.getMessage());
        }
    }
    
    public void modificarEjemplar(int cod, int sede, String est) throws Exception{
        strSQL = "select pa_update_ejemplar("+cod+","+sede+",'"+est+"')";
        try {
            objConetar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al modificar ---> "+e.getMessage());
        }
    }
    
    public ResultSet obtenerEjemplares(String isbn) throws Exception {
        strSQL = "select * from listadoEjemplares where estado = 'D' and isbn ilike '" + isbn + "';";
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al obtener primer ejemplar: " + e.getMessage());
        }
    }

}
