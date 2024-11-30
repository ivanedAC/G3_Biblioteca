/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JTable;
/**
 *
 * @author ander
 */
public class clsReserva {
    
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;
    Connection con = null;
    Statement sent = null;
    
    
    public Integer generarCodReserva() throws Exception{
        strSQL = "select coalesce(max(codigo),0)+1 as codigo from reserva";
        try {
            rs = objConectar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar el codigo de reserva -->" +e.getMessage());
        }
        return 0;
    }
    
    public ResultSet buscarReserva(Integer codRes) throws Exception {
        strSQL = "select * from reserva where codigo = " + codRes;
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar reserva: " + e.getMessage());
        }
    }
    
    public boolean verificarExistenciaReserva(int codCli) throws Exception{
        strSQL = "select * from reserva where cod_cliente="+codCli+" and estado='P'";
        try {
            rs = objConectar.consultar(strSQL);
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            throw new Exception("Error al verificar existencia de reserva de cliente "+e.getMessage());
        }
        return false;
    }
    
    public ResultSet verificarReservaLibro(String isbn, int codSede) throws Exception{
        strSQL = "select distinct(det.isbn) as isbn, re.cod_cliente, re.codigo as cod_reserva,\n" +
            "(pe.nombres || ' ' || pe.ape_paterno || ' ' || pe.ape_materno) as nombres,\n" +
            "re.f_reserva\n" +
            "from detalle_reserva det\n" +
            "inner join reserva re on re.codigo=det.cod_reserva\n" +
            "inner join cliente cl on cl.codigo = re.cod_cliente\n" +
            "inner join persona pe on pe.codigo = cl.cod_persona\n" +
            "inner join libro li on li.isbn=det.isbn\n" +
            "inner join ejemplar ej on ej.isbn=li.isbn\n" +
            "where re.estado='P' and det.isbn='"+isbn+"' and ej.estado='P' and ej.cod_sede="+codSede+"\n" +
            "order by re.codigo asc\n" +
            "limit 1;";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;

        } catch (Exception e) {
            throw new Exception("Error al verificar prestamo del libro "+ e.getMessage());
        }
    }
    
    public Date obtenerFechaAproximada(String isbn) throws Exception {
        strSQL = "SELECT (MIN(pr.f_limite) + (MAX(pr.f_limite) - MIN(pr.f_limite)) / 2) + 1 AS fecha_aprox " +
                        "FROM detalle_prestamo dep " +
                        "INNER JOIN prestamo pr ON pr.codigo = dep.cod_prestamo " +
                        "INNER JOIN ejemplar ej ON ej.codigo = dep.cod_ejemplar " +
                        "INNER JOIN libro li ON li.isbn = ej.isbn " +
                        "WHERE li.isbn = '" + isbn + "' AND ej.estado = 'P';";

        try {
            ResultSet rs = objConectar.consultar(strSQL);

            if (rs.next()) {
                Date fechaAprox = rs.getDate("fecha_aprox");
                return fechaAprox;
            } else {
                throw new Exception("No se encontraron resultados.");
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener la fecha: " + e.getMessage());
        }
    }
    
    public Integer validarCantidadReservasEjemplar(String isbn, int codSede) throws Exception{
        strSQL = "SELECT e.isbn, COUNT(e.isbn) - COALESCE(dr.cantidad, 0) AS cantidad\n" +
            "FROM ejemplar e\n" +
            "LEFT JOIN (\n" +
            "    SELECT isbn, COUNT(isbn) AS cantidad\n" +
            "    FROM detalle_reserva\n" +
            "    GROUP BY isbn\n" +
            ") dr ON e.isbn = dr.isbn\n" +
            "WHERE \n" +
            "    e.cod_sede="+codSede+"\n" +
            "	and e.isbn = '"+isbn+"'\n" +
            "GROUP BY \n" +
            "    e.isbn, dr.cantidad;";
        try {
            rs = objConectar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("cantidad");
            }
        } catch (Exception e) {
            throw new Exception("Error al calcular la cantidad de ejemplares reservados  ->"+e.getMessage());
        }
        return -1;    
    } 
    
    public void registrarReserva(Integer codReserva, Integer codCli, JTable tblDetalles,Integer codSede) throws Exception {
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            con.setAutoCommit(false);
            sent = con.createStatement();

            clsCliente objCli = new clsCliente();

            ResultSet rsCliente = objCli.buscarClientePorCodigo(codCli);
            int cantidadPres = validarCantidadReservasEjemplar(tblDetalles.getValueAt(0, 2).toString(), codSede);
            
            if (rsCliente.next()) {
                
                if (verificarExistenciaReserva(codCli)) {
                    throw new Exception("El cliente ya tiene una reserva vigente.");
                } else {
                    if (cantidadPres==0) {
                        throw new Exception("Límite de reservas alcanzados para este ejemplar.");
                    }else{
                        int c = tblDetalles.getRowCount();
                    if (c != 1) {
                        throw new Exception("Solo se puede reservar un libro a la vez.");
                    }

                    strSQL = "INSERT INTO reserva (codigo, cod_cliente, f_reserva) " +
                             "VALUES (" + codReserva + ", " + codCli + ", CURRENT_DATE);";
                    sent.executeUpdate(strSQL);

                    String isbnLibro = tblDetalles.getValueAt(0, 2).toString();

                    strSQL = "INSERT INTO detalle_reserva (cod_reserva, isbn) " +
                             "VALUES (" + codReserva + ", '" + isbnLibro + "');";
                    sent.executeUpdate(strSQL);

                    con.commit();
                    }
                }
            } else {
                throw new Exception("Error, el cliente ingresado no existe.");
            }
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al registrar reserva: " + e.getMessage());
        } finally {
            objConectar.desconectar();
        }
    }

    public void insertarDetalleReserva(Integer codRese, Integer codDev, Integer codCli, Integer codEjemplar, String isbn) throws Exception {
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            con.setAutoCommit(false);
            sent = con.createStatement();

            clsCliente objCli = new clsCliente();
            clsDevolucion objDev = new clsDevolucion();

            ResultSet rsCliente = objCli.buscarClientePorCodigo(codCli);
            if (rsCliente.next()) {

                if (verificarExistenciaReserva(codCli)) {                    
                    ResultSet rsDevolucion = objDev.datosDevolucion(codDev);
                    if (!rsDevolucion.next()) {
                        throw new Exception("Error, la devolución indicada no existe.");
                    } else {
                        String fDevolucion = rsDevolucion.getString("f_devolucion");
                        String hDevolucion = rsDevolucion.getString("h_devolucion");

                        String fInicio, fFin;
                        if (LocalTime.parse(hDevolucion).isAfter(LocalTime.of(14, 0))) { 
                            fInicio = LocalDate.parse(fDevolucion).plusDays(1).toString();
                            fFin = LocalDate.parse(fDevolucion).plusDays(2).toString();
                        } else {
                            fInicio = fDevolucion;
                            fFin = LocalDate.parse(fDevolucion).plusDays(1).toString();
                        }

                        String strSQL = "UPDATE detalle_reserva SET cod_ejemplar=" + codEjemplar + ", f_inicio='" + fInicio + "', "
                                        + "f_fin='" + fFin + "' WHERE cod_reserva=" + codRese + " AND isbn='" + isbn + "';";
                        sent.executeUpdate(strSQL);

                        strSQL = "UPDATE ejemplar SET estado='R' WHERE codigo=" + codEjemplar + " AND isbn='" + isbn + "';";
                        sent.executeUpdate(strSQL);

                        strSQL = "UPDATE ejemplar SET estado='C' WHERE codigo=" + codEjemplar +
                                 " AND isbn='" + isbn + "' AND CURRENT_DATE > '" + fFin + "';";
                        sent.executeUpdate(strSQL);

                        con.commit();
                    }
                }else{
                    throw new Exception("El cliente no tiene una reserva vigente.");
                }
  
            } else {
                throw new Exception("Error, el cliente ingresado no existe.");
            }
        } catch (Exception e) {
            con.rollback(); 
            throw new Exception("Error al registrar detalle de la reserva: " + e.getMessage());
        } finally {
            objConectar.desconectar(); 
        }
    }



    
}
