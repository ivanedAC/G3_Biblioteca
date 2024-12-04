/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.clsProveedor;
import capaLogica.clsReporte;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author reque
 */
public class jdMnuReportes extends javax.swing.JDialog {

    clsReporte objReporte = new clsReporte();
    clsProveedor objProveedor = new clsProveedor();
    String format = "";
    String format2 = "";

    /**
     * Creates new form jdMnuReportes
     */
    public jdMnuReportes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Menú Reportes - Biblioteca Ricardo Palma");
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

        if (jdInicio.getDate() == null || jdFinal.getDate() == null) {
            return;
        }
        Calendar selectedCalendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        selectedCalendar.setTime(jdInicio.getDate());
        format = sdf.format(selectedCalendar.getTime());
        selectedCalendar.setTime(jdFinal.getDate());
        format2 = sdf.format(selectedCalendar.getTime());

    }

    private void listarProveedor() {
        ResultSet rsP = null;
        DefaultComboBoxModel modeloP = new DefaultComboBoxModel();
        cmbAuxiliar.setModel(modeloP);
        try {
            rsP = objProveedor.listadoProveedores();
            while (rsP.next()) {
                modeloP.addElement(rsP.getString("razon_social"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void validarFechas() {
        Date fechaInicial = jdInicio.getDate();
        Date fechaFinal = jdFinal.getDate();

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
        String opcion = cmbReporte.getSelectedItem().toString();
        switch (opcion) {
            case "Préstamos y devoluciones por cliente":
                lblInicio.setVisible(true);
                lblFinal.setVisible(true);
                jdInicio.setVisible(true);
                jdFinal.setVisible(true);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);
                break;
            case "Libros más solicitados":
                lblInicio.setVisible(true);
                lblFinal.setVisible(true);
                jdInicio.setVisible(true);
                jdFinal.setVisible(true);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);

                break;
            case "Clientes con préstamos vencidos":
                lblInicio.setVisible(false);
                lblFinal.setVisible(false);
                jdInicio.setVisible(false);
                jdFinal.setVisible(false);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);

                break;
            case "Ejemplares dañados":
                lblInicio.setVisible(false);
                lblFinal.setVisible(false);
                jdInicio.setVisible(false);
                jdFinal.setVisible(false);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);

                break;
            case "Sanciones a clientes":
                lblInicio.setVisible(false);
                lblFinal.setVisible(false);
                jdInicio.setVisible(false);
                jdFinal.setVisible(false);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);

                break;
            case "Detalle de compra":
                cmbAuxiliar.removeAllItems();
                lblInicio.setVisible(false);
                lblFinal.setVisible(false);
                jdInicio.setVisible(false);
                jdFinal.setVisible(false);
                lblCodigo.setText("Selecciona proveedor");
                listarProveedor();
                lblCodigo.setVisible(true);
                cmbAuxiliar.setVisible(true);

                break;
            case "Usabilidad por sede":
                lblInicio.setVisible(false);
                lblFinal.setVisible(false);
                jdInicio.setVisible(false);
                jdFinal.setVisible(false);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);

                break;
            case "Libros con mayor reservas":
                lblInicio.setVisible(false);
                lblFinal.setVisible(false);
                jdInicio.setVisible(false);
                jdFinal.setVisible(false);
                lblCodigo.setVisible(false);
                cmbAuxiliar.removeAllItems();
                for (int i = 1; i <= 10; i++) {
                    cmbAuxiliar.addItem(String.valueOf(i));
                }
                cmbAuxiliar.setVisible(true);

                break;
            case "Reservas Completadas":
                lblInicio.setVisible(true);
                lblFinal.setVisible(true);
                jdInicio.setVisible(true);
                jdFinal.setVisible(true);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);

                break;
            case "Categoría más destacada":
                lblInicio.setVisible(true);
                lblFinal.setVisible(true);
                jdInicio.setVisible(true);
                jdFinal.setVisible(true);
                lblCodigo.setVisible(false);
                cmbAuxiliar.setVisible(false);

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
            parametros.put("parRazonSocial", cmbAuxiliar.getSelectedItem().toString());

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

    private void visualizarReporte2(String rpt) {
        try {
            Container contenedor = this.vistaReporte;
            contenedor.setLayout(new BorderLayout());
            contenedor.removeAll();

            Map<String, Object> parametros = new HashMap();
            parametros.put("parCantidad", Integer.parseInt(cmbAuxiliar.getSelectedItem().toString()));

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

        cmbReporte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Préstamos y devoluciones por cliente", "Libros más solicitados", "Clientes con préstamos vencidos", "Ejemplares dañados", "Sanciones a clientes", "Detalle de compra", "Usabilidad por sede", "Libros con mayor reservas", "Reservas Completadas", "Categoría más destacada" }));
        cmbReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbReporteActionPerformed(evt);
            }
        });

        jdInicio.setDateFormatString("dd-MM-yyyy");

        jdFinal.setDateFormatString("dd-MM-yyyy");

        lblFinal.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        lblFinal.setForeground(new java.awt.Color(113, 49, 18));
        lblFinal.setText("Fecha Final:");

        lblInicio.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        lblInicio.setForeground(new java.awt.Color(113, 49, 18));
        lblInicio.setText("Fecha Inicio:");

        lblCodigo.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(contenedorInformacionLayout.createSequentialGroup()
                        .addComponent(lblCodigo)
                        .addGap(18, 18, 18)
                        .addComponent(cmbAuxiliar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(contenedorInformacionLayout.createSequentialGroup()
                        .addGroup(contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(contenedorInformacionLayout.createSequentialGroup()
                                .addComponent(lblInicio)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, contenedorInformacionLayout.createSequentialGroup()
                                .addComponent(lblFinal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jdInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(jdFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(14, 14, 14))
        );
        contenedorInformacionLayout.setVerticalGroup(
            contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedorInformacionLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdInicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInicio, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCodigo)
                    .addComponent(cmbAuxiliar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(contenedorInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFinal))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout vistaReporteLayout = new javax.swing.GroupLayout(vistaReporte);
        vistaReporte.setLayout(vistaReporteLayout);
        vistaReporteLayout.setHorizontalGroup(
            vistaReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        vistaReporteLayout.setVerticalGroup(
            vistaReporteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 541, Short.MAX_VALUE)
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
        String opcion = cmbReporte.getSelectedItem().toString();

        switch (opcion) {
            case "Préstamos y devoluciones por cliente":
                setFechas();
                if ((format == null || format.isEmpty()) && (format2 == null || format2.isEmpty())) {
                    JOptionPane.showMessageDialog(rootPane, "Debe escoger una fecha para este reporte");
                    return;
                }
                visualizarReporteConFechas("rpt1.jasper");
                break;
            case "Libros más solicitados":
                setFechas();
                if ((format == null || format.isEmpty()) && (format2 == null || format2.isEmpty())) {
                    JOptionPane.showMessageDialog(rootPane, "Debe escoger una fecha para este reporte");
                    return;
                }
                visualizarReporteConFechas("rpt2.jasper");
                break;
            case "Clientes con préstamos vencidos":
                visualizarReporte("rpt3.jasper");
                break;
            case "Ejemplares dañados":
                visualizarReporte("RptLibrosDañados.jasper");
                break;
            case "Sanciones a clientes":
                visualizarReporte("rpt4.jasper");
                break;
            case "Detalle de compra":
                visualizarReporte1("rpt5_subreport1.jasper");
                break;
            case "Usabilidad por sede":
                visualizarReporte("RptUsabilidad.jasper");
                break;
            case "Libros con mayor reservas":
                visualizarReporte2("RptTopLxR.jasper");
                break;
            case "Reservas Completadas":
                visualizarReporte("RptReservasCompletadas.jasper");
                break;
            case "Categoría más destacada":
                visualizarReporte("RptCategorias.jasper");
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
