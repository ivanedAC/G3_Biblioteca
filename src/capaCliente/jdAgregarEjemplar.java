/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.clsAutor;
import capaLogica.clsCategoria;
import capaLogica.clsEditorial;
import capaLogica.clsFormato;
import capaLogica.clsIdioma;
import capaLogica.clsLibro;
import capaLogica.clsTipoLibro;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Edgar
 */
public class jdAgregarEjemplar extends javax.swing.JDialog {

    /**
     * Creates new form jdManLibro
     */
    clsLibro objLibro = new clsLibro();
    public String isbn = "";

    /**
     * Creates new form jdManLibro
     */
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
            if (rbtnNombre.isSelected()) {
                rsLibro = objLibro.listarLibrosParaPrestamosbusquedaAvanzadaNombre(nombre);
            } else if (rbtnISBN.isSelected()) {
                rsLibro = objLibro.listarLibrosParaPrestamosbusquedaAvanzadaISBN(nombre);
            } else {
                rsLibro = objLibro.listarLibrosParaPrestamosbusquedaAvanzadaEditorial(nombre);
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
            tblDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
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
            rsLibro = objLibro.listarLibrosParaPrestamos();
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
            tblDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tblDatos.getTableHeader().setReorderingAllowed(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error al listar en tabla" + e.getMessage());
        }
    }

    public jdAgregarEjemplar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        listarTabla();
        rbtnNombre.setSelected(true);
        tblDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setTitle("Menú - Agregar Ejemplar");
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
        setTitle("Consulta de ejemplares");

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
                .addGap(162, 162, 162)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(243, 226, 210));

        tblDatos.setBackground(new java.awt.Color(255, 255, 255));
        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tblDatos);

        txtbusquedaAvanzada.setBackground(new java.awt.Color(255, 255, 255));
        txtbusquedaAvanzada.setForeground(new java.awt.Color(0, 0, 0));
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

        rbtnNombre.setBackground(new java.awt.Color(243, 226, 210));
        buttonGroup1.add(rbtnNombre);
        rbtnNombre.setForeground(new java.awt.Color(0, 0, 0));
        rbtnNombre.setText("Nombre");
        rbtnNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnNombreActionPerformed(evt);
            }
        });

        rbtnISBN.setBackground(new java.awt.Color(243, 226, 210));
        buttonGroup1.add(rbtnISBN);
        rbtnISBN.setForeground(new java.awt.Color(0, 0, 0));
        rbtnISBN.setText("ISBN");
        rbtnISBN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnISBNActionPerformed(evt);
            }
        });

        rbtnEditorial.setBackground(new java.awt.Color(243, 226, 210));
        buttonGroup1.add(rbtnEditorial);
        rbtnEditorial.setForeground(new java.awt.Color(0, 0, 0));
        rbtnEditorial.setText("Editorial");
        rbtnEditorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnEditorialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtbusquedaAvanzada, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(rbtnNombre)
                        .addGap(51, 51, 51)
                        .addComponent(rbtnISBN)
                        .addGap(62, 62, 62)
                        .addComponent(rbtnEditorial)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        if (tblDatos.getSelectedRow() != -1) {
            isbn = tblDatos.getValueAt(tblDatos.getSelectedRow(), 0).toString();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un libro a agregar!");
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void txtbusquedaAvanzadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbusquedaAvanzadaActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_txtbusquedaAvanzadaActionPerformed

    private void txtbusquedaAvanzadaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbusquedaAvanzadaKeyReleased
        busquedaAvanzada(txtbusquedaAvanzada.getText());
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbusquedaAvanzadaKeyReleased

    private void rbtnNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnNombreActionPerformed
        // TODO add your handling code here:
        if(rbtnNombre.isSelected()){
            txtbusquedaAvanzada.setText("");
            listarTabla();
        }
    }//GEN-LAST:event_rbtnNombreActionPerformed

    private void rbtnISBNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnISBNActionPerformed
        // TODO add your handling code here:
        if(rbtnISBN.isSelected()){
            txtbusquedaAvanzada.setText("");
            listarTabla();
        }
    }//GEN-LAST:event_rbtnISBNActionPerformed

    private void rbtnEditorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnEditorialActionPerformed
        // TODO add your handling code here:
        if(rbtnEditorial.isSelected()){
            txtbusquedaAvanzada.setText("");
            listarTabla();
        }
    }//GEN-LAST:event_rbtnEditorialActionPerformed

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
