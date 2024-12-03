/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.clsReporte;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
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
 * @author reque
 */
public class jdMnuReportes extends javax.swing.JDialog {

    clsReporte objReporte = new clsReporte();
    String format;
    String format2;

    /**
     * Creates new form jdMnuReportes
     */
    public jdMnuReportes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Menú Reportes - Biblioteca Ricardo Palma");
        lblInicio.setVisible(false);
        lblFinal.setVisible(false);
        jdInicio.setVisible(false);
        jdFinal.setVisible(false);
        lblCodigo.setVisible(false);
        cmbAuxiliar.setVisible(false);
        cmbReporte.setSelectedItem(0);
        contenedorInformacion.setPreferredSize(new Dimension(843, 111));
        contenedorInformacion.setMinimumSize(new Dimension(843, 111));
        contenedorInformacion.setMaximumSize(new Dimension(843, 111));
        ((JTextField) jdInicio.getDateEditor().getUiComponent()).setEditable(false);
        ((JTextField) jdFinal.getDateEditor().getUiComponent()).setEditable(false);
        this.vistaReporte.setVisible(false);
        jdInicio.setMaxSelectableDate(new Date());
        jdFinal.setMaxSelectableDate(new Date());
        jdInicio.getDateEditor().addPropertyChangeListener("date", new PropertyChangeListener() {
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

    private void setFechas() {
        Calendar selectedCalendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        selectedCalendar.setTime(jdInicio.getDate());
        format = sdf.format(selectedCalendar.getTime());
        selectedCalendar.setTime(jdFinal.getDate());
        format2 = sdf.format(selectedCalendar.getTime());
    }

    private void validarFechas() {
        Date fechaInicial = jdInicio.getDate();
        Date fechaFinal = jdFinal.getDate();
                if (format == null && format2 == null) {
            JOptionPane.showMessageDialog(rootPane, "Debe escoger una fecha para este reporte");
            return;
        }

        if (fechaFinal != null && fechaInicial == null) {
            jdInicio.setDate(fechaFinal);
        } else if (fechaInicial != null && fechaFinal != null) {
            if (fechaInicial.after(fechaFinal)) {
                JOptionPane.showMessageDialog(rootPane, "Debe escoger un rango válido de fechas");
                jdInicio.setDate(fechaFinal);
            }
        }
    }

    private void reporte() {
        int opcion = cmbReporte.getSelectedIndex();

        switch (opcion) {
            case 0:
                lblInicio.setVisible(true);
                lblFinal.setVisible(true);
                jdInicio.setVisible(true);
                jdFinal.setVisible(true);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);
                break;
            case 1:
                lblInicio.setVisible(true);
                lblFinal.setVisible(true);
                jdInicio.setVisible(true);
                jdFinal.setVisible(true);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);
                break;
            case 2:
                lblInicio.setVisible(false);
                lblFinal.setVisible(false);
                jdInicio.setVisible(false);
                jdFinal.setVisible(false);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);
                break;
            case 3:
                lblInicio.setVisible(false);
                lblFinal.setVisible(false);
                jdInicio.setVisible(false);
                jdFinal.setVisible(false);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);
                break;
            case 4:
                lblInicio.setVisible(false);
                lblFinal.setVisible(false);
                jdInicio.setVisible(false);
                jdFinal.setVisible(false);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);
                break;
            case 5:
                lblInicio.setVisible(false);
                lblFinal.setVisible(false);
                jdInicio.setVisible(false);
                jdFinal.setVisible(false);
                lblCodigo.setText("Selecciona proveedor");
                cmbAuxiliar.addItem("Proveedor 1");
                lblCodigo.setVisible(true);
                cmbAuxiliar.setVisible(true);
                break;
            case 6:
                lblInicio.setVisible(false);
                lblFinal.setVisible(false);
                jdInicio.setVisible(false);
                jdFinal.setVisible(false);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);
                break;
            case 7:
                lblInicio.setVisible(false);
                lblFinal.setVisible(false);
                jdInicio.setVisible(false);
                jdFinal.setVisible(false);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);
                break;
            case 8:
                lblInicio.setVisible(true);
                lblFinal.setVisible(true);
                jdInicio.setVisible(true);
                jdFinal.setVisible(true);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);
                break;
            case 9:
                break;
            default:
                throw new AssertionError();
        }

    }

    private void visualizarReporteConFechas(String rpt) {
        try {
            Container contenedor = this.vistaReporte;
            contenedor.setLayout(new BorderLayout());
            contenedor.removeAll();

            Map<String, Object> parametros = new HashMap();
            parametros.put("ParFechaInicial", format);
            parametros.put("ParFechaFinal", format2);

            JRViewer objRp = objReporte.reporteInterno(rpt, parametros);

            contenedor.add(objRp);
            objRp.setVisible(true);
            this.vistaReporte.setVisible(true);
            contenedor.revalidate();
            contenedor.repaint();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void visualizarReporte(String rpt) {
        try {
            Container contenedor = this.vistaReporte;
            contenedor.setLayout(new BorderLayout());
            contenedor.removeAll();

            Map<String, Object> parametros = new HashMap();
            parametros.put("ParFechaInicial", format);
            parametros.put("ParFechaFinal", format2);

            JRViewer objRp = objReporte.reporteInterno(rpt, null);

            contenedor.add(objRp);
            objRp.setVisible(true);
            this.vistaReporte.setVisible(true);
            contenedor.revalidate();
            contenedor.repaint();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void visualizarReporte1(String rpt) {
        try {
            Container contenedor = this.vistaReporte;
            contenedor.setLayout(new BorderLayout());
            contenedor.removeAll();

            Map<String, Object> parametros = new HashMap();
            parametros.put("ParFechaInicial", format);

            JRViewer objRp = objReporte.reporteInterno(rpt, parametros);

            contenedor.add(objRp);
            objRp.setVisible(true);
            this.vistaReporte.setVisible(true);
            contenedor.revalidate();
            contenedor.repaint();

        } catch (Exception e) {
            System.out.println(e.getMessage());
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

        jDialog1 = new javax.swing.JDialog();
        jProgressBar1 = new javax.swing.JProgressBar();
        contenedorInformacion = new javax.swing.JPanel();
        cmbReporte = new javax.swing.JComboBox<>();
        jdInicio = new com.toedter.calendar.JDateChooser();
        jdFinal = new com.toedter.calendar.JDateChooser();
        lblFinal = new javax.swing.JLabel();
        lblInicio = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        cmbAuxiliar = new javax.swing.JComboBox<>();
        btnReporte = new javax.swing.JButton();
        vistaReporte = new javax.swing.JDesktopPane();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        contenedorInformacion.setBackground(new java.awt.Color(230, 182, 139));

        cmbReporte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Préstamos y devoluciones por cliente", "Libros más solicitados", "Clientes con préstamos vencidos", "Ejemplares dañados", "Sanciones a clientes", "Detalle de compra", "Usabilidad por sede", "Frecuencia de reserva de libros", "Libros no prestados por periodos" }));
        cmbReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbReporteActionPerformed(evt);
            }
        });

        jdInicio.setDateFormatString("dd-MM-yyyy");

        jdFinal.setDateFormatString("dd-MM-yyyy");

        lblFinal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblFinal.setForeground(new java.awt.Color(113, 49, 18));
        lblFinal.setText("Fecha Final:");

        lblInicio.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblInicio.setForeground(new java.awt.Color(113, 49, 18));
        lblInicio.setText("Fecha Inicio:");

        lblCodigo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCodigo.setForeground(new java.awt.Color(113, 49, 18));
        lblCodigo.setText("Codigo:");

        btnReporte.setBackground(new java.awt.Color(113, 49, 18));
        btnReporte.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        btnReporte.setForeground(new java.awt.Color(230, 182, 139));
        btnReporte.setText("Generar Reporte");
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contenedorInformacionLayout = new javax.swing.GroupLayout(contenedorInformacion);
        contenedorInformacion.setLayout(contenedorInformacionLayout);
        contenedorInformacionLayout.setHorizontalGroup(
            contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contenedorInformacionLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(cmbReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnReporte)
                .addGap(80, 80, 80)
                .addGroup(contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCodigo)
                    .addComponent(lblFinal)
                    .addComponent(lblInicio))
                .addGap(18, 18, 18)
                .addGroup(contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                    .addComponent(jdFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbAuxiliar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );
        contenedorInformacionLayout.setVerticalGroup(
            contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorInformacionLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdInicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInicio, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contenedorInformacionLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbAuxiliar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCodigo))))
                .addGap(5, 5, 5)
                .addGroup(contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFinal))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout vistaReporteLayout = new javax.swing.GroupLayout(vistaReporte);
        vistaReporte.setLayout(vistaReporteLayout);
        vistaReporteLayout.setHorizontalGroup(
            vistaReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        vistaReporteLayout.setVerticalGroup(
            vistaReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedorInformacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(vistaReporte)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(contenedorInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vistaReporte))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbReporteActionPerformed
        reporte();
    }//GEN-LAST:event_cmbReporteActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        int opcion = cmbReporte.getSelectedIndex();
        setFechas();
        switch (opcion) {
            case 0:
                visualizarReporteConFechas("rpt1.jasper");
                break;
            case 1:
                visualizarReporteConFechas("rpt2.jasper");
                break;
            case 2:
                visualizarReporte("rpt3.jasper");
                break;
            case 3:
                visualizarReporte("rpt4.jasper");
                break;
            case 4:
                visualizarReporte("rpt5.jasper");
                break;
            case 5:
                visualizarReporte1("rpt6.jasper");
                break;
            case 6:
                visualizarReporte("rpt7.jasper");
                break;
            case 7:
                visualizarReporte("rpt8.jasper");
                break;
            case 8:
                visualizarReporteConFechas("rpt9.jasper");
                break;
            case 9:
                JOptionPane.showMessageDialog(rootPane, "En construcción...");
                break;
            default:
                throw new AssertionError();
        }
    }//GEN-LAST:event_btnReporteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReporte;
    private javax.swing.JComboBox<String> cmbAuxiliar;
    private javax.swing.JComboBox<String> cmbReporte;
    private javax.swing.JPanel contenedorInformacion;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JProgressBar jProgressBar1;
    private com.toedter.calendar.JDateChooser jdFinal;
    private com.toedter.calendar.JDateChooser jdInicio;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblFinal;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JDesktopPane vistaReporte;
    // End of variables declaration//GEN-END:variables
}
