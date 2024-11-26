/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.clsLibro;
import capaLogica.clsSede;
import capaLogica.clsUsuarioSTATIC;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ander
 */
public class jdAgregarEjemplarReserva extends javax.swing.JDialog {

    /**
     * Creates new form jdAgregarEjemplarReserva
     */
    public jdAgregarEjemplarReserva(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    clsLibro objLibro = new clsLibro();
    clsSede objSede = new clsSede();
    public String isbn = "";
    
    public void busquedaAvanzada(String nombre) {
        ResultSet rsLibro = null;
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("ISBN");
        modelo.addColumn("Editorial");
        modelo.addColumn("Nombre");
        modelo.addColumn("N° de paginas");
        modelo.addColumn("Edicion");  
        modelo.addColumn("Tipo");
        modelo.addColumn("Formato");
        modelo.addColumn("Idioma");
        modelo.addColumn("Autores");
        modelo.addColumn("Categorias");

        try {
            int codSede = objSede.obtenerSede(clsUsuarioSTATIC.sede);

            if (rbtnNombre.isSelected()) {
                rsLibro = objLibro.listarLibrosPrestadosBusquedaAvanzadaNombre(nombre, codSede);
            } else if (rbtnISBN.isSelected()) {
                rsLibro = objLibro.listarLibrosPrestadosBusquedaAvanzadaISBN(nombre, codSede);
            } else {
                rsLibro = objLibro.listarLibrosPrestadosParaBusquedaAvanzadaEditorial(nombre, codSede);
            }

            while (rsLibro.next()) {
                modelo.addRow(new Object[]{rsLibro.getString("isbn"),
                    rsLibro.getString("editorial"),
                    rsLibro.getString("libro_nombre"),
                    rsLibro.getString("num_paginas"),
                    rsLibro.getString("edicion"),
                    rsLibro.getString("tipo"),
                    rsLibro.getString("formato"),
                    rsLibro.getString("idioma"),
                    rsLibro.getString("autores"),
                    rsLibro.getString("categorias")});
            }
            tblDatos.setModel(modelo);
            tblDatos.getTableHeader().setReorderingAllowed(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error al listar en tabla" + e.getMessage());
        }
    }

    public void listarTabla() {
        ResultSet rsLibro = null;
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("ISBN");
        modelo.addColumn("Editorial");
        modelo.addColumn("Nombre");
        modelo.addColumn("N° de paginas");
        modelo.addColumn("Edicion");
        modelo.addColumn("Tipo");
        modelo.addColumn("Formato");
        modelo.addColumn("Idioma");
        modelo.addColumn("Autores");
        modelo.addColumn("Categorias");

        try {
            int codSede = objSede.obtenerSede(clsUsuarioSTATIC.sede);

            rsLibro = objLibro.listarLibrosSoloReserva(codSede);
            while (rsLibro.next()) {
                modelo.addRow(new Object[]{rsLibro.getString("isbn"),
                    rsLibro.getString("editorial"),
                    rsLibro.getString("libro_nombre"),
                    rsLibro.getString("num_paginas"),
                    rsLibro.getString("edicion"),
                    rsLibro.getString("tipo"),
                    rsLibro.getString("formato"),
                    rsLibro.getString("idioma"),
                    rsLibro.getString("autores"),
                    rsLibro.getString("categorias")});
            }
            tblDatos.setModel(modelo);
            tblDatos.getTableHeader().setReorderingAllowed(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error al listar en tabla" + e.getMessage());
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        txtbusquedaAvanzada = new javax.swing.JTextField();
        rbtnNombre = new javax.swing.JRadioButton();
        rbtnISBN = new javax.swing.JRadioButton();
        rbtnEditorial = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(251, 230, 211));

        btnNuevo.setBackground(new java.awt.Color(113, 49, 18));
        btnNuevo.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/nuevo_32px.png"))); // NOI18N
        btnNuevo.setText("SELECCIONAR");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(113, 49, 18));
        btnSalir.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/salir_32px.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(292, 292, 292)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        tblDatos.setBackground(new java.awt.Color(245, 224, 206));
        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tblDatos);

        txtbusquedaAvanzada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbusquedaAvanzadaActionPerformed(evt);
            }
        });
        txtbusquedaAvanzada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbusquedaAvanzadaKeyReleased(evt);
            }
        });

        buttonGroup1.add(rbtnNombre);
        rbtnNombre.setText("Nombre");

        buttonGroup1.add(rbtnISBN);
        rbtnISBN.setText("ISBN");

        buttonGroup1.add(rbtnEditorial);
        rbtnEditorial.setText("Editorial");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtbusquedaAvanzada, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(rbtnNombre)
                        .addGap(70, 70, 70)
                        .addComponent(rbtnISBN)
                        .addGap(71, 71, 71)
                        .addComponent(rbtnEditorial)
                        .addGap(0, 253, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbusquedaAvanzada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtnNombre)
                    .addComponent(rbtnISBN)
                    .addComponent(rbtnEditorial))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        if (tblDatos.getSelectedRow() != -1) {
            isbn = tblDatos.getValueAt(tblDatos.getSelectedRow(), 0).toString();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un libro a agregar!");
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtbusquedaAvanzadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbusquedaAvanzadaActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_txtbusquedaAvanzadaActionPerformed

    private void txtbusquedaAvanzadaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbusquedaAvanzadaKeyReleased
        busquedaAvanzada(txtbusquedaAvanzada.getText());
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbusquedaAvanzadaKeyReleased

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        listarTabla();
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JRadioButton rbtnEditorial;
    private javax.swing.JRadioButton rbtnISBN;
    private javax.swing.JRadioButton rbtnNombre;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtbusquedaAvanzada;
    // End of variables declaration//GEN-END:variables
}
