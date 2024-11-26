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
    
    public ResultSet obtenerEjemplaresPrestados(String isbn) throws Exception {
        strSQL = "select * from listadoEjemplares where estado = 'P' and isbn ilike '" + isbn + "';";
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al obtener primer ejemplar: " + e.getMessage());
        }
    }
    
    public ResultSet buscarEjemplares(String isbn, String libro, String autor, int codEditorial, int codTipoL, int codSede) throws Exception{
        strSQL = "SELECT "
                + "li.isbn, "
                + "li.nombre AS libro, "
                + "au.nombre AS autor, "
                + "li.edicion, "
                + "ed.nombre AS editorial, "
                + "SUM(CASE WHEN ej.estado = 'D' THEN 1 ELSE 0 END) AS Disponible, "
                + "SUM(CASE WHEN ej.estado = 'P' THEN 1 ELSE 0 END) AS Prestados, "
                + "SUM(CASE WHEN ej.estado = 'R' THEN 1 ELSE 0 END) AS Reservas "
                + "FROM (SELECT * FROM libro WHERE isbn LIKE '%"+isbn+"%' AND UPPER(nombre) LIKE UPPER('%"+libro+"%') AND cod_tipo_libro="+codTipoL+") li "
                + "INNER JOIN ejemplar ej ON ej.isbn = li.isbn "
                + "INNER JOIN autor_libro auli ON auli.isbn = li.isbn "
                + "INNER JOIN autor au ON au.codigo = auli.autorcodigo "
                + "INNER JOIN sede se on se.codigo = ej.cod_sede "
                + "INNER JOIN editorial ed on ed.codigo = li.cod_editorial "
                + "WHERE ej.estado IN ('D', 'P','R') AND se.codigo="+codSede+"  AND UPPER(au.nombre) LIKE UPPER('%"+autor+"%') AND ed.codigo="+codEditorial+" "
                + "GROUP BY li.isbn, li.nombre, au.nombre, li.edicion, ed.nombre;";
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar ejemplares -->"+e.getMessage());
        }
    }
    
    public ResultSet buscarEjemplaresTexto(String isbn, String libro, String autor, int codSede) throws Exception{
        strSQL = "SELECT "
                + "li.isbn, "
                + "li.nombre AS libro, "
                + "au.nombre AS autor, "
                + "li.edicion, "
                + "ed.nombre AS editorial, "
                + "SUM(CASE WHEN ej.estado = 'D' THEN 1 ELSE 0 END) AS Disponible, "
                + "SUM(CASE WHEN ej.estado = 'P' THEN 1 ELSE 0 END) AS Prestados, "
                + "SUM(CASE WHEN ej.estado = 'R' THEN 1 ELSE 0 END) AS Reservas "
                + "FROM (SELECT * FROM libro WHERE isbn LIKE '%"+isbn+"%' AND UPPER(nombre) LIKE UPPER('%"+libro+"%')) li "
                + "INNER JOIN ejemplar ej ON ej.isbn = li.isbn "
                + "INNER JOIN autor_libro auli ON auli.isbn = li.isbn "
                + "INNER JOIN autor au ON au.codigo = auli.autorcodigo "
                + "INNER JOIN sede se on se.codigo = ej.cod_sede "
                + "INNER JOIN editorial ed on ed.codigo = li.cod_editorial "
                + "WHERE ej.estado IN ('D', 'P','R') AND se.codigo="+codSede+"  AND UPPER(au.nombre) LIKE UPPER('%"+autor+"%') "
                + "GROUP BY li.isbn, li.nombre, au.nombre, li.edicion, ed.nombre;";
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar ejemplares por datos ingresados -->"+e.getMessage());
        }
    }
    
    public ResultSet buscarEjemplaresTipoLibro(String isbn, String libro, String autor, int codTipoL, int codSede) throws Exception{
        strSQL = "SELECT "
                + "li.isbn, "
                + "li.nombre AS libro, "
                + "au.nombre AS autor, "
                + "li.edicion, "
                + "ed.nombre AS editorial, "
                + "SUM(CASE WHEN ej.estado = 'D' THEN 1 ELSE 0 END) AS Disponible, "
                + "SUM(CASE WHEN ej.estado = 'P' THEN 1 ELSE 0 END) AS Prestados, "
                + "SUM(CASE WHEN ej.estado = 'R' THEN 1 ELSE 0 END) AS Reservas "
                + "FROM (SELECT * FROM libro WHERE isbn LIKE '%"+isbn+"%' AND UPPER(nombre) LIKE UPPER('%"+libro+"%') AND cod_tipo_libro="+codTipoL+") li "
                + "INNER JOIN ejemplar ej ON ej.isbn = li.isbn "
                + "INNER JOIN autor_libro auli ON auli.isbn = li.isbn "
                + "INNER JOIN autor au ON au.codigo = auli.autorcodigo "
                + "INNER JOIN sede se on se.codigo = ej.cod_sede "
                + "INNER JOIN editorial ed on ed.codigo = li.cod_editorial "
                + "WHERE ej.estado IN ('D', 'P','R') AND se.codigo="+codSede+"  AND UPPER(au.nombre) LIKE UPPER('%"+autor+"%') "
                + "GROUP BY li.isbn, li.nombre, au.nombre, li.edicion, ed.nombre;";
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar ejemplares -->"+e.getMessage());
        }
    }
    
    public ResultSet buscarEjemplaresEditorial(String isbn, String libro, String autor, int codEditorial, int codSede) throws Exception{
        strSQL = "SELECT "
                + "li.isbn, "
                + "li.nombre AS libro, "
                + "au.nombre AS autor, "
                + "li.edicion, "
                + "ed.nombre AS editorial, "
                + "SUM(CASE WHEN ej.estado = 'D' THEN 1 ELSE 0 END) AS Disponible, "
                + "SUM(CASE WHEN ej.estado = 'P' THEN 1 ELSE 0 END) AS Prestados,"
                + "SUM(CASE WHEN ej.estado = 'R' THEN 1 ELSE 0 END) AS Reservas "
                + "FROM (SELECT * FROM libro WHERE isbn LIKE '%"+isbn+"%' AND UPPER(nombre) LIKE UPPER('%"+libro+"%')) li "
                + "INNER JOIN ejemplar ej ON ej.isbn = li.isbn "
                + "INNER JOIN autor_libro auli ON auli.isbn = li.isbn "
                + "INNER JOIN autor au ON au.codigo = auli.autorcodigo "
                + "INNER JOIN sede se on se.codigo = ej.cod_sede "
                + "INNER JOIN editorial ed on ed.codigo = li.cod_editorial "
                + "WHERE ej.estado IN ('D', 'P','R') AND se.codigo="+codSede+"  AND UPPER(au.nombre) LIKE UPPER('%"+autor+"%') AND ed.codigo="+codEditorial+" "
                + "GROUP BY li.isbn, li.nombre, au.nombre, li.edicion, ed.nombre;";
        try {
            rs = objConetar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar ejemplares -->"+e.getMessage());
        }
    }
    
    public Integer cantidadEjemplares(String isbn, int codSede) throws Exception{
        strSQL = "select count(isbn) as cantidad from ejemplar where isbn='"+isbn+"' and cod_sede="+codSede+";";
        try {
            rs = objConetar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("cantidad");
            }
        } catch (Exception e) {
            throw new Exception("Error al hallar la cantidad de ejemplares en la sede -->"+e.getMessage());
        }
        return 0;
    }
    
    
}
