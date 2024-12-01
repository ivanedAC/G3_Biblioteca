/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capaLogica;

import capaDatos.clsJDBC;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Grupo_Biblioteca
 */
public class clsReporte {

    clsJDBC objConectar = new clsJDBC();

    public static final String RUTA_REPORTES = "src//reportes//";

    public JRViewer reporteInterno(String archivoReporte, Map<String, Object> parametros) throws Exception {
        try {
            objConectar.conectar();
            JasperPrint reporte = JasperFillManager.fillReport(this.RUTA_REPORTES + archivoReporte, parametros, objConectar.getCon());
            JRViewer visor = new JRViewer(reporte);
            return visor;

        } catch (Exception e) {
            System.out.println("Error en clsReporte " + e.getMessage());
        }
        return null;
    }
}
