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

    public String generarEstructuraArray(ArrayList x) {
        String normal = "Array[";
        int i = 0;
        for (Object indice : x) {
            normal += indice;
            if (i != x.size() - 1) {
                normal += ",";
            } else {
                normal += "]";
            }
            i++;
        }
        return normal;
    }

    public ResultSet buscarCodigosAutoresPorISBN(String ISBN) throws Exception {
        strSQL = String.format("select autorcodigo from libro li INNER JOIN autor_libro au ON li.isbn = au.isbn where li.isbn = '%s'", ISBN);
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar Autores por ISBN -->" + e.getMessage());
        }
    }

    public ResultSet buscarCodigosCategoriasPorISBN(String ISBN) throws Exception {
        strSQL = String.format("select cod_categoria from libro li INNER JOIN libro_categoria au ON li.isbn = au.isbn where li.isbn = '%s'", ISBN);
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar Categorias por ISBN -->" + e.getMessage());
        }
    }

    public ResultSet listarLibrosParaPrestamos() throws Exception {
        strSQL = "select distinct ll.* from listadolibros ll inner join ejemplar ejem on \n"
                + "ejem.isbn = ll.isbn";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar libros -->" + e.getMessage());
        }
    }
    public ResultSet listarLibrosParaPrestamosbusquedaAvanzadaNombre(String nombre) throws Exception {
        strSQL = "select distinct ll.* from listadolibros ll inner join ejemplar ejem on \n"
                + "ejem.isbn = ll.isbn \n"
                + "where upper(ll.libro_nombre) LIKE upper('%"+nombre+"%')";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar libros -->" + e.getMessage());
        }
    }
    
    public ResultSet listarLibrosParaPrestamosbusquedaAvanzadaISBN(String isbn) throws Exception {
        strSQL = "select distinct ll.* from listadolibros ll inner join ejemplar ejem on \n"
                + "ejem.isbn = ll.isbn \n"
                + "where upper(ll.isbn) LIKE upper('%"+isbn+"%')";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar libros -->" + e.getMessage());
        }
    }
    
    public ResultSet listarLibrosParaPrestamosbusquedaAvanzadaEditorial(String editorial) throws Exception {
        strSQL = "select distinct ll.* from listadolibros ll inner join ejemplar ejem on \n"
                + "ejem.isbn = ll.isbn \n"
                + "where upper(ll.editorial) LIKE upper('%"+editorial+"%')";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar libros -->" + e.getMessage());
        }
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

    public ResultSet listarLibro(String isbn) throws Exception {
        strSQL = "select * from listadoLibros where isbn ='" + isbn + "'";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar libros -->" + e.getMessage());
        }
    }

    public ResultSet registrarLibro(String isbn, Integer editorial, String nombre, Integer num_pagina, Integer edicion, Integer cod_form, Integer cod_tipo, Integer idioma, ArrayList autores, ArrayList categorias) throws Exception {
        strSQL = String.format("SELECT pa_insert_libro('%s', %d, '%s', %d, %d, %d, %d, %d,", isbn, editorial, nombre, num_pagina, edicion, cod_form, cod_tipo, idioma);
        if (autores.isEmpty()) {
            strSQL += "null,";
            if (categorias.isEmpty()) {
                strSQL += "null";
            } else {
                strSQL += generarEstructuraArray(categorias);
            }

        } else {
            strSQL += generarEstructuraArray(autores) + ",";
            if (categorias.isEmpty()) {
                strSQL += "null";
            } else {
                strSQL += generarEstructuraArray(categorias);
            }
        }
        strSQL += ") as resultado";
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

    public ResultSet eliminarLibro(String isbn) throws Exception {
        strSQL = String.format("SELECT pa_delete_libro('%s') as resultado", isbn);
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al eliminar libro --> " + e.getMessage());
        }
    }

    public ResultSet actualizarLibro(String isbn, Integer editorial, String nombre, Integer num_pagina, Integer edicion, Integer cod_form, Integer cod_tipo, Integer idioma, ArrayList autores, ArrayList categorias) throws Exception {
        strSQL = String.format("SELECT pa_update_libro('%s', %d, '%s', %d, %d, %d, %d, %d,", isbn, editorial, nombre, num_pagina, edicion, cod_form, cod_tipo, idioma);
        if (autores.isEmpty()) {
            strSQL += "null,";
            if (categorias.isEmpty()) {
                strSQL += "null";
            } else {
                strSQL += generarEstructuraArray(categorias);
            }

        } else {
            strSQL += generarEstructuraArray(autores) + ",";
            if (categorias.isEmpty()) {
                strSQL += "null";
            } else {
                strSQL += generarEstructuraArray(categorias);
            }
        }
        strSQL += ") as resultado";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al editar categoria -->" + e.getMessage());
        }
    }
}
