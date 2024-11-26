/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JTable;

/**
 *
 * @author ACER
 */
public class clsPrestamo {

    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;
    Connection con = null;
    Statement sent = null;

    clsSede objSede = new clsSede();
    clsCliente objCli = new clsCliente();
    clsEjemplar objEjem = new clsEjemplar();

    public ResultSet buscarPrestamo(Integer cod) throws Exception {
        strSQL = "select * from prestamo where codigo = " + cod;
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar préstamo: " + e.getMessage());
        }
    }
    
    public ResultSet buscarPrestamos(Integer cli) throws Exception {
        strSQL = "select * from prestamo where cod_cliente = " + cli;
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar préstamo: " + e.getMessage());
        }
    }
    
    public ResultSet buscarPrestamoVigentes(Integer cli) throws Exception {
        strSQL = "select * from prestamo where estado = 'P' and cod_cliente = " + cli;
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar préstamo: " + e.getMessage());
        }
    }
    
    public Integer generarCorrelativo() throws Exception {
        strSQL = "select COALESCE(Max(correlativo),0)+1 as codigo from prestamo";
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar correlativo de préstamo: " + e.getMessage());
        }
        return 0;
    }

    public Integer generarCodPrestamo() throws Exception {
        try {
            String cod = "" + clsUsuarioSTATIC.codigo + objSede.obtenerSede(clsUsuarioSTATIC.sede) + generarCorrelativo();
            return Integer.valueOf(cod);
        } catch (Exception e) {
            throw new Exception("Error al generar código de préstamo: " + e.getMessage());
        }
    }

    public void registrarPrestamo(Integer codPre, Integer cli, String f_limite, String h_limite, JTable tblDetalles) throws Exception {
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            con.setAutoCommit(false);
            sent = con.createStatement();
            ResultSet rsCliente = objCli.buscarClientePorCodigo(cli);
            ResultSet rsPresCli = buscarPrestamos(cli);
            ResultSet rsEjem;

            if (rsCliente.next()) {
                if (!rsCliente.getString("estado").equals("A")) {
                    throw new Exception("Error, el estado del cliente no le permite tramitar préstamos");
                } else {
                    
                    while (rsPresCli.next()){
                        if (rsPresCli.getString("estado").equals("P")){
                            throw new Exception("El cliente seleccionado tiene algún préstamo pendientes");
                        }
                    }
                    
                    strSQL = "INSERT INTO PRESTAMO values (" + codPre + "," + clsUsuarioSTATIC.codigo + ","
                            + cli + ", CURRENT_DATE,(SELECT CURRENT_TIME::time(0)),'" + f_limite + "','" + h_limite + "'," + "'P'," + generarCorrelativo() + "," + objSede.obtenerSede(clsUsuarioSTATIC.sede)+");";
                    sent.executeUpdate(strSQL);
                    int c = tblDetalles.getRowCount();

                    if (c > 3) {
                        throw new Exception("No se puede llevar más de 3 ejemplares en un solo préstamo");
                    } else {
                        for (int i = 0; i < c; i++) {
                            rsEjem = objEjem.buscarPorCodigo(Integer.parseInt(tblDetalles.getValueAt(i, 0).toString()));
                            int codEjem = Integer.parseInt(tblDetalles.getValueAt(i, 0).toString());
                            if (rsEjem.next()) {
                                if (rsEjem.getString("estado").equals("D") && rsEjem.getString("sede").equals(clsUsuarioSTATIC.sede)) {
                                    if (validarEjemplaresRepetidos(codPre, codEjem) == 0) {
                                        strSQL = "INSERT INTO DETALLE_PRESTAMO values (" + codPre + "," + codEjem + ");";
                                        sent.executeUpdate(strSQL);

                                        strSQL = "UPDATE EJEMPLAR SET ESTADO = 'P' WHERE CODIGO = " + codEjem + ";";
                                        sent.executeUpdate(strSQL);
                                    } else {
                                        throw new Exception("No se puede llevar dos ejemplares del mismo libro");
                                    }
                                } else {
                                    throw new Exception("El ejemplar no se encuentra disponible para prestar o no se encuentra presente en esta sede");
                                }

                            } else {
                                throw new Exception("Ejemplar ingresdo no válido");
                            }
                        }
                        con.commit();
                    }
                }
            } else {
                throw new Exception("Error, el cliente ingresado no existe");
            }
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al registrar préstamo: " + e.getMessage());
        } finally {
            objConectar.desconectar();
        }
    }

    public boolean validarHoraAnularPrestamo(String fecha, String hora) throws Exception {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {
            LocalDate fechaIngresada = LocalDate.parse(fecha, formatoFecha);
            LocalTime horaIngresada = LocalTime.parse(hora, formatoHora).withNano(0); // Elimina precisión de microsegundos

            LocalDate fechaActual = LocalDate.now();
            LocalTime horaActual = LocalTime.now();

            if (!fechaIngresada.equals(fechaActual)) {
                return false;
            }

            if (horaIngresada.isAfter(horaActual)) {
                return false;
            }

            long minutosDiferencia = Duration.between(horaIngresada, horaActual).toMinutes();

            return minutosDiferencia <= 30;

        } catch (DateTimeParseException e) {
            System.err.println("Error de formato en la fecha o la hora: " + e.getMessage());
            return false;
        }
    }

    public void anularPrestamo(Integer cod) throws Exception {
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            con.setAutoCommit(false);
            sent = con.createStatement();

            ResultSet rsPre = buscarPrestamo(cod);
            String fecha = "", hora = "", estado = "";
            boolean validar = false;

            if (rsPre.next()) {
                fecha = rsPre.getString("f_registro");
                hora = rsPre.getString("h_registro");
                validar = validarHoraAnularPrestamo(fecha, hora);
                estado = rsPre.getString("estado");
                
                strSQL = "SELECT * FROM DEVOLUCION WHERE cod_prestamo = " + cod + ";";
                ResultSet rsDevs = sent.executeQuery(strSQL);
                
                if (rsDevs.next()){
                    throw new Exception("No se puede anular el préstamo si ya ha devuelto algún ejemplar, por favor tramite las devoluciones restantes");
                }
            } else {
                throw new Exception("El préstamo ingresado no existe");
            }

            if (validar) {
                if (estado.equals("P")) {
                    strSQL = "UPDATE PRESTAMO SET ESTADO = 'A' where codigo =" + cod + ";";
                    sent.executeUpdate(strSQL);
                    strSQL = "UPDATE EJEMPLAR SET ESTADO = 'D' where codigo in (select cod_ejemplar from detalle_prestamo where cod_prestamo ="
                            + cod + ");";
                    sent.executeUpdate(strSQL);
                    con.commit();
                } else {
                    throw new Exception("No se pueden anular préstamos que no están pendientes");
                }
            } else {
                throw new Exception("El préstamo elegido ya no se puede anular, debe tramitar la devolución");
            }
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al anular préstamo: " + e.getMessage());
        }
    }

    public Integer validarEjemplaresRepetidos(int codPre, int codEjem) throws Exception {
        strSQL = "select fn_comprobarlibrosrepetidos(" + codPre + "," + codEjem + ") as validar";
        try {
            rs = objConectar.consultar(strSQL);
            if (rs.next()) {
                return rs.getInt("validar");
            }
        } catch (Exception e) {
            throw new Exception("Error al listar detalles: " + e.getMessage());
        }
        return null;
    }

    public ResultSet listarDetalles(Integer cod) throws Exception {
        strSQL = "select * from detalle_prestamo where cod_prestamo = " + cod;
        try {
            rs = objConectar.consultar(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar detalles: " + e.getMessage());
        }
    }
}
