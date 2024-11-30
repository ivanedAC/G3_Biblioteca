/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;

/**
 *
 * @author ander
 */
public class clsOrdenCompra {

    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs = null;
    Connection con = null;
    Statement sent = null;

    clsSede objSede = new clsSede();
    clsProveedor objProv = new clsProveedor();
    clsLibro objLib = new clsLibro();
    clsEjemplar objEjem = new clsEjemplar();

    public Integer generarCorrelativo() throws Exception {
        strSQL = "select COALESCE(Max(correlativo),0)+1 as codigo from orden_compra";
        try {
            rs = objConectar.consultar(strSQL);
            while (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar correlativo de compra: " + e.getMessage());
        }
        return 0;
    }

    public Integer generarCodCompra() throws Exception {
        try {
            String cod = "" + clsUsuarioSTATIC.codigo + objSede.obtenerSede(clsUsuarioSTATIC.sede) + generarCorrelativo();
            return Integer.valueOf(cod);
        } catch (Exception e) {
            throw new Exception("Error al generar código de la compra: " + e.getMessage());
        }
    }

    public void registraOrdenCompra(Integer numCompra, Integer codPro, Float subtotal, Float igv, JTable tblDetalles) throws Exception {
        try {

            objConectar.conectar();
            con = objConectar.getCon();
            con.setAutoCommit(false);
            sent = con.createStatement();

            ResultSet rsProv = objProv.buscarProveedorPorCodigo(codPro);

            if (rsProv.next()) {
                strSQL = "INSERT INTO ORDEN_COMPRA VALUES (" + numCompra + "," + clsUsuarioSTATIC.codigo + "," + codPro + ", CURRENT_DATE,'C',"
                        + subtotal + "," + igv + "," + generarCorrelativo() + "," + objSede.obtenerSede(clsUsuarioSTATIC.sede) + ");";
                sent.executeUpdate(strSQL);

                for (int i = 0; i < tblDetalles.getRowCount(); i++) {
                    for (int j = 0; j < Integer.parseInt(tblDetalles.getValueAt(i, 2).toString()); j++) {
                        strSQL = "select COALESCE(Max(codigo),0)+1 as codigo from ejemplar";
                        ResultSet rsCod = sent.executeQuery(strSQL);
                        Integer codEje = -1;
                        
                        if (rsCod.next()) {
                            codEje = rsCod.getInt("codigo");
                        }
                        
                        String isbn = tblDetalles.getValueAt(i, 0).toString();
                        float precio = Float.parseFloat(tblDetalles.getValueAt(i, 3).toString());
                        

                        strSQL = "INSERT INTO EJEMPLAR VALUES (" + codEje + "," + objSede.obtenerSede(clsUsuarioSTATIC.sede) + ",'D'," + isbn + ");";
                        sent.executeUpdate(strSQL);
                        
                        strSQL = "INSERT INTO DETALLE_COMPRA VALUES (" + codEje + "," + numCompra + "," + precio + ");";
                        sent.executeUpdate(strSQL);
                    }
                }
                con.commit();
            } else {
                throw new Exception("El código del proveedor es inválido");
            }
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al registrar la Orden de Compra: " + e.getMessage());
        }
    }

}
