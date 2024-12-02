/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.clsReporte;
import java.awt.BorderLayout;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Grupo_Biblioteca
 */
public class jdReportePrestamosDevoluciones extends javax.swing.JDialog {

    clsReporte objReporte = new clsReporte();

    /**
     * Creates new form jdReportePrestamosDevoluciones
     */
    public jdReportePrestamosDevoluciones(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Reporte de Préstamos y Devoluciones");
        ((JTextField) jdInicial.getDateEditor().getUiComponent()).setEditable(false);
        ((JTextField) jdFinal.getDateEditor().getUiComponent()).setEditable(false);
        this.vistaReporte.setVisible(false);
        jdInicial.setMaxSelectableDate(new Date());
        jdFinal.setMaxSelectableDate(new Date());
        jdInicial.getDateEditor().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                validarFechas();
            }
        });

        jdFinal.getDateEditor().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                validarFechas();
            }
        });

    }

    private void validarFechas() {
        Date fechaInicial = jdInicial.getDate();
        Date fechaFinal = jdFinal.getDate();
        if (fechaFinal != null && fechaInicial == null) {
            jdInicial.setDate(fechaFinal);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jdInicial = new com.toedter.calendar.JDateChooser();
        jdFinal = new com.toedter.calendar.JDateChooser();
        vistaReporte = new javax.swing.JDesktopPane();
        btnReporte = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Fecha Inicial:");

        jLabel2.setText("Fecha Final:");

        jdInicial.setDateFormatString("yyyy-MM-dd");

        jdFinal.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout vistaReporteLayout = new javax.swing.GroupLayout(vistaReporte);
        vistaReporte.setLayout(vistaReporteLayout);
        vistaReporteLayout.setHorizontalGroup(
            vistaReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        vistaReporteLayout.setVerticalGroup(
            vistaReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
        );

        btnReporte.setText("Visualizar Reporte");
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jdInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(jdFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 397, Short.MAX_VALUE)
                .addComponent(btnReporte)
                .addGap(47, 47, 47))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(vistaReporte)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jdInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jdFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnReporte)
                        .addGap(31, 31, 31)))
                .addComponent(vistaReporte)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed

        Calendar selectedCalendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        selectedCalendar.setTime(jdInicial.getDate());
        String format = sdf.format(selectedCalendar.getTime());
        selectedCalendar.setTime(jdFinal.getDate());
        String format2 = sdf.format(selectedCalendar.getTime());

        try {
            Container contenedor = this.vistaReporte;
            contenedor.setLayout(new BorderLayout());
            contenedor.removeAll();

            Map<String, Object> parametros = new HashMap();
            parametros.put("ParFechaInicial", format);
            parametros.put("ParFechaFinal", format2);

            for (Map.Entry<String, Object> entry : parametros.entrySet()) {
                System.out.println("Clave: " + entry.getKey() + ", Valor: " + entry.getValue());
            }

            JRViewer objRp = objReporte.reporteInterno("rpt1.jasper", parametros);

            contenedor.add(objRp);
            objRp.setVisible(true);
            this.vistaReporte.setVisible(true);
            contenedor.revalidate();
            contenedor.repaint();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnReporteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private com.toedter.calendar.JDateChooser jdFinal;
    private com.toedter.calendar.JDateChooser jdInicial;
    private javax.swing.JDesktopPane vistaReporte;
    // End of variables declaration//GEN-END:variables
}