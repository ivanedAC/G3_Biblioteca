/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Edgar
 */
public class clsLibro {
    String strSQL;
    clsJDBC objConectar = new clsJDBC();
    ResultSet rs;
    
    public String generarEstructuraArray(ArrayList x){
        String normal = "Array[";
        int i = 0;
        for (Object indice : x) {
           normal += indice;
           if(i!=x.size()-1){
               normal += ",";
           } else {
               normal += "]";
           }
           i++;
        }
        return normal;
    }
    
    public ResultSet listarLibro() throws Exception {
        strSQL = "select * from listadoLibros";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar libros -->" + e.getMessage());
        }
    }

    public ResultSet registrarCategoria(String isbn,Integer editorial, String nombre,Integer num_pagina, Integer edicion, Integer cod_form, Integer cod_tipo, Integer idioma, ArrayList autores, ArrayList categorias) throws Exception {
        strSQL = String.format("SELECT pa_insert_libro('%s', %d, '%s', %d, %d, %d, %d, %d,",isbn,editorial,nombre, num_pagina, edicion, cod_form,cod_tipo, idioma);
        if(autores.isEmpty()){
            strSQL += "null,";
            if(categorias.isEmpty()){
                strSQL+= "null";
            } else {
                strSQL+= generarEstructuraArray(categorias);
            }
            
        } else {
            strSQL += generarEstructuraArray(autores) + ",";
            if(categorias.isEmpty()){
                strSQL+= "null";
            } else {
                strSQL+= generarEstructuraArray(categorias);
            }
        }
        strSQL += ")";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al registrar categoria -->" + e.getMessage());
        }
    }

    public ResultSet buscarLibro(String isbn) throws Exception {
        strSQL = "select * from libro where isbn =" + isbn;
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar libro -->" + e.getMessage());
        }
    }

    public void eliminarLibro(String isbn) throws Exception {
        strSQL = String.format("SELECT pa_delete_libro('%s')",isbn);
        try {
            rs = objConectar.consultar(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al eliminar libro --> " + e.getMessage());
        }
    }

    public ResultSet actualizarLibro(String isbn,Integer editorial, String nombre,Integer num_pagina, Integer edicion, Integer cod_form, Integer cod_tipo, Integer idioma, ArrayList autores, ArrayList categorias) throws Exception {
        strSQL = String.format("SELECT pa_update_libro('%s', %d, '%s', %d, %d, %d, %d, %d,",isbn,editorial,nombre, num_pagina, edicion, cod_form,cod_tipo, idioma);
        if(autores.isEmpty()){
            strSQL += "null,";
            if(categorias.isEmpty()){
                strSQL+= "null";
            } else {
                strSQL+= generarEstructuraArray(categorias);
            }
            
        } else {
            strSQL += generarEstructuraArray(autores) + ",";
            if(categorias.isEmpty()){
                strSQL+= "null";
            } else {
                strSQL+= generarEstructuraArray(categorias);
            }
        }
        strSQL += ")";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al registrar categoria -->" + e.getMessage());
        }
    }
}
