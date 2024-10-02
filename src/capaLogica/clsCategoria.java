/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.*;

/**
 *
 * @author Louis
 */
public class clsCategoria {

    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;

    public ResultSet listarCategoria() throws Exception {
        strSQL = "select *from categoria order by codigo";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar categorias -->" + e.getMessage());
        }
    }

    public Integer generarCodCategoria() throws Exception {
        strSQL = "select COALESCE(MAX(codigo),0)+1 as codigo from categoria";
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar codigo -->" + e.getMessage());
        }
        return 0;
    }

    public void registrarCategoria(int cod, String nombre) throws Exception {
        strSQL = "insert into categoria (codigo, nombre) values (" + cod + ", '" + nombre + "')";
        try {
            objConectar.ejecutar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar categoria -->" + e.getMessage());
        }
    }

    public ResultSet buscarCategoria(int cod) throws Exception {
        strSQL = "select * from categoria where codigo =" + cod;
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar categoria -->" + e.getMessage());
        }
    }

    public void eliminarCategoria(int cod) throws Exception {
        strSQL = "delete from categoria where codigo =" + cod;
        try {
            objConectar.ejecutar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al eliminar categoria --> " + e.getMessage());
        }
    }

    public void actualizarCategoria(int cod, String nom) throws Exception {
        strSQL = "update categoria set nombre ='" + nom + "' where codigo =" +cod;
        try {
            objConectar.ejecutar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al actualizar categoria --> " + e.getMessage());
        }
    }
    public ResultSet buscarCategoriaPorNombre(String nombre) throws Exception{
        strSQL = "SELECT * FROM CATEGORIA WHERE nombre iLike '%"+nombre+"%';";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar la categoria: " + e.getMessage());
        }
    }
    
}
