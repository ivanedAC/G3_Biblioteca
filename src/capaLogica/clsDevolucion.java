/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JTable;

/**
 *
 * @author ACER
 */
public class clsDevolucion {

    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;
    Connection con = null;
    Statement sent = null;

    clsSede objSede = new clsSede();
    clsCliente objCli = new clsCliente();
    clsPrestamo objPre = new clsPrestamo();
    clsEjemplar objEjem = new clsEjemplar();

    public Integer generarCodDevolucion() throws Exception {
        try {
            String cod = "" + clsUsuarioSTATIC.codigo + objSede.obtenerSede(clsUsuarioSTATIC.sede) + generarCorrelativo();
            return Integer.valueOf(cod);
        } catch (Exception e) {
            throw new Exception("Error al generar código de devolución: " + e.getMessage());
        }
    }

    public Integer generarCorrelativo() throws Exception {
        strSQL = "select COALESCE(Max(correlativo),0)+1 as codigo from devolucion";
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar correlativo de devolución: " + e.getMessage());
        }
        return 0;
    }

    public ResultSet listarDetallesPendientes(Integer cod) throws Exception {
        strSQL = "select * from listadoejemplares where codigo in ("
                + "select cod_ejemplar from detalle_prestamo dp where dp.cod_prestamo =  " + cod
                + " and cod_ejemplar not in (\n"
                + "select dv.cod_ejemplar from detalle_devolucion dv inner join devolucion dev\n"
                + "on dev.codigo = dv.cod_devolucion where dev.cod_prestamo = " + cod + "));";
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar detalles: " + e.getMessage());
        }
    }

    public boolean validarHora120Horas(String fecha, String hora) throws Exception {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {
            LocalDate fechaIngresada = LocalDate.parse(fecha, formatoFecha);
            LocalTime horaIngresada = LocalTime.parse(hora, formatoHora).withNano(0);

            LocalDateTime fechaHoraIngresada = LocalDateTime.of(fechaIngresada, horaIngresada);

            LocalDateTime fechaHoraActual = LocalDateTime.now();

            if (fechaHoraIngresada.isAfter(fechaHoraActual)) {
                return false;
            } else {
                return true;
            }

        } catch (DateTimeParseException e) {
            System.err.println("Error de formato en la fecha o la hora: " + e.getMessage());
            return false;
        }
    }

    public void registrarDevolucion(Integer codDev, Integer codPre, JTable tblDetalles) throws Exception {
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            con.setAutoCommit(false);
            sent = con.createStatement();
            ResultSet rsPre = objPre.buscarPrestamo(codPre);
            ResultSet rsEjem;

            if (rsPre.next()) {
                if (!rsPre.getString("estado").equals("P")) {
                    throw new Exception("Error, el préstamo del cliente no se encuentra vigente");
                } else {
                    strSQL = "INSERT INTO DEVOLUCION values (" + codDev + "," + codPre + ","
                            + "CURRENT_DATE,(SELECT CURRENT_TIME::time(0))," + generarCorrelativo() + "," + objSede.obtenerSede(clsUsuarioSTATIC.sede) + "," + clsUsuarioSTATIC.codigo + ");";
                    sent.executeUpdate(strSQL);

                    for (int i = 0; i < tblDetalles.getRowCount(); i++) {
                        rsEjem = objEjem.buscarPorCodigo(Integer.parseInt(tblDetalles.getValueAt(i, 0).toString()));
                        int codEjem = Integer.parseInt(tblDetalles.getValueAt(i, 0).toString());
                        if (rsEjem.next()) {
                            if (tblDetalles.getValueAt(i, 6).toString().equals("Ninguna")) {
                                String sancion = "null";

                                if (validarHora120Horas(rsPre.getString("f_limite"), rsPre.getString("h_limite"))) {
                                    sancion = "1";
                                }

                                if (rsEjem.getString("sede").equals(clsUsuarioSTATIC.sede)) {
                                    strSQL = "INSERT INTO DETALLE_DEVOLUCION values (" + codEjem + "," + codDev + "," + sancion + ");";
                                    sent.executeUpdate(strSQL);

                                    strSQL = "UPDATE EJEMPLAR SET ESTADO = 'D' WHERE CODIGO = " + codEjem + ";";
                                    sent.executeUpdate(strSQL);

                                } else {
                                    throw new Exception("El ejemplar que intenta devolver no ha sido prestado en esta sede");
                                }
                            } else {
                                if (rsEjem.getString("sede").equals(clsUsuarioSTATIC.sede)) {
                                    strSQL = "INSERT INTO DETALLE_DEVOLUCION values (" + codEjem + "," + codDev + "," + tblDetalles.getValueAt(i, 6).toString() + ");";
                                    sent.executeUpdate(strSQL);

                                    strSQL = "UPDATE EJEMPLAR SET ESTADO = 'X' WHERE CODIGO = " + codEjem + ";";
                                    sent.executeUpdate(strSQL);

                                    strSQL = "UPDATE CLIENTE SET ESTADO = 'V' WHERE CODIGO = " + rsPre.getString("cod_cliente") + ";";
                                    sent.executeUpdate(strSQL);
                                } else {
                                    throw new Exception("El ejemplar que intenta devolver no ha sido prestado en esta sede");
                                }
                            }
                        } else {
                            throw new Exception("Ejemplar ingresdo no válido");
                        }
                    }

                    strSQL = "SELECT cod_ejemplar\n"
                            + "FROM prestamo pre\n"
                            + "INNER JOIN detalle_prestamo dp ON dp.cod_prestamo = pre.codigo\n"
                            + "WHERE pre.codigo =" + codPre
                            + " EXCEPT\n"
                            + "SELECT dd.cod_ejemplar\n"
                            + "FROM devolucion dev\n"
                            + "INNER JOIN detalle_devolucion dd ON dev.codigo = dd.cod_devolucion\n"
                            + "WHERE dev.codigo = " + codPre + ";";
                    ResultSet rsEjemPen = sent.executeQuery(strSQL);

                    if (!rsEjemPen.next()) {
                        strSQL = "UPDATE PRESTAMO SET ESTADO = 'C' where codigo =" + codPre + ";";
                        sent.executeUpdate(strSQL);
                    }
                    
                    con.commit();
                    
                }
            } else {
                throw new Exception("Error, el préstamo ingresado no existe");
            }
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al registrar devolución: " + e.getMessage());
        } finally {
            objConectar.desconectar();
        }
    }

}
