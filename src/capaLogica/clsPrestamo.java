/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.*;
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

    public Integer generarCodPrestamo() throws Exception {
        strSQL = "select COALESCE(Max(codigo),0)+1 as codigo from prestamo";
        Integer codPre = -1;
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {
                codPre = rs.getInt("codigo");
            }
            String cod = "" + clsUsuarioSTATIC.codigo + objSede.obtenerSede(clsUsuarioSTATIC.sede) + codPre;
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
            ResultSet rsEjem;

            if (rsCliente.next()) {
                if (!rsCliente.getString("estado").equals("A")) {
                    throw new Exception("Error, el estado del cliente no le permite tramitar préstamos");
                } else {
                    strSQL = "INSERT INTO PRESTAMO values (" + codPre + "," + clsUsuarioSTATIC.codigo + ","
                            + cli + ", CURRENT_DATE, CURRENT_TIME,'" + f_limite + "','" + h_limite + "'," + "'P');";
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
