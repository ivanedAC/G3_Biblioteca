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

    public ResultSet listarEjemplaresSede() throws Exception {
        strSQL = "select * from  listarEjemplaresAsignados";
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar Ejemplares en sede ---> " + e.getMessage());
        }
    }

    public ResultSet listarEjemplaresSinA() throws Exception {
        strSQL = "select * from  listarEjemplaresSinA";
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar Ejemplares sin asignar ---> " + e.getMessage());
        }
    }
    
    public ResultSet listarEjemplarSedeIndiv() throws Exception{
        strSQL = "select * from listarEjemplaresIndiv";
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar Ejemplares de manera individual --->" +e.getMessage());
        }
    }

    public ResultSet obtenerCodigoEjemplar(String libro, String sede) throws Exception {
        strSQL = "select * from obtenerCodigosLibros('" + libro + "','" + sede + "')";
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al obterner codigo(os) ---> " + e.getMessage());
        }
    }
    
    public ResultSet obtenerCodigoEjemplar(String libro) throws Exception{
        strSQL = "select * from obtenerCodigosLibros('" + libro+"')";
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al obtener codigo(os) ---> " + e.getMessage());
        }
    }

    public ResultSet buscarPorCodigo(int[] cod) throws Exception {
        StringBuilder codigosConcatenados = new StringBuilder();
        codigosConcatenados.append("ARRAY[");

        for (int i = 0; i < cod.length; i++) {
            codigosConcatenados.append(cod[i]);
            if (i < cod.length - 1) {
                codigosConcatenados.append(", ");
            }
        }

        codigosConcatenados.append("]");

        String strSQL = "select * from pa_buscar_libros_por_codigos(" + codigosConcatenados.toString() + ")";

        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar por códigos --> " + e.getMessage());
        }
    }
    
    public Integer generarCodigo() throws Exception{
        strSQL = "select COALESCE(MAX(codigo), 0) + 1 as codigo from ejemplar";
        try {
            rs = objConetar.consultar(strSQL);
            while (rs.next()) {                
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar codigo --->" +e.getMessage());
        }
        return 0;
    }

}
