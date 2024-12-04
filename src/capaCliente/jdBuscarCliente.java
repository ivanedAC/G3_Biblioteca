/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.clsCliente;
import capaLogica.clsPais;
import capaLogica.clsTipoDocumento;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Chinc
 */
public class jdBuscarCliente extends javax.swing.JDialog {

    /**
     * Creates new form jdManCliente
     */
    public Integer codCli = -1;
    clsCliente objCliente = new clsCliente();

    public jdBuscarCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        listarClientesNaturales();
        setTitle("Menú - Buscar Cliente");
        rbtnPN.setSelected(true);
    }

    public void listarClientesNaturales() {
        ResultSet rsClientesN = null;
        String sexo = "";
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("Codigo");
        modelo.addColumn("Pais");
        modelo.addColumn("Tipo doc");
        modelo.addColumn("N° documento");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Sexo");
        modelo.addColumn("Nacimiento");
        modelo.addColumn("direccion");
        modelo.addColumn("Telefono");
        modelo.addColumn("Correo");
        modelo.addColumn("Estado");

        try {
            rsClientesN = objCliente.listarClientesN(txtNombre.getText(), txtApellido.getText(), txtNumDocumento.getText());
            while (rsClientesN.next()) {
                Object datos[][] = new Object[1][12];
                datos[0][0] = rsClientesN.getString("codigo");
                datos[0][1] = rsClientesN.getString("pais");
                datos[0][2] = rsClientesN.getString("tipo_documento");
                datos[0][3] = rsClientesN.getString("numero_documento");
                datos[0][4] = rsClientesN.getString("nombres");
                datos[0][5] = rsClientesN.getString("apellidos");
                if (rsClientesN.getBoolean("sexo")) {
                    sexo = "Masculino";
                } else {
                    sexo = "Femenino";
                }
                datos[0][6] = sexo;
                datos[0][7] = rsClientesN.getString("f_nacimiento");
                datos[0][8] = rsClientesN.getString("direccion");
                datos[0][9] = rsClientesN.getString("telefono");
                datos[0][10] = rsClientesN.getString("correo");
                datos[0][11] = rsClientesN.getString("estado");

                modelo.addRow(datos[0]);
            }
            tblPersonaNatural.setModel(modelo);
            tblPersonaNatural.getTableHeader().setReorderingAllowed(false);
            adjustColumnSizes(tblPersonaNatural);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error al listar en tabla" + e.getMessage());
        }
    }

    public void listarClientesJuridicos() {
        ResultSet rsClientesJ = null;
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Definir las columnas del modelo
        modelo.addColumn("Código");
        modelo.addColumn("País");
        modelo.addColumn("Tipo doc");
        modelo.addColumn("N° documento");
        modelo.addColumn("Nombres");
        modelo.addColumn("Razón Social");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Correo");
        modelo.addColumn("Estado");


        try {
            rsClientesJ = objCliente.listarClientesJ(txtNombre.getText(), txtApellido.getText(), txtNumDocumento.getText());

            // Recorrer el ResultSet y llenar el modelo
            while (rsClientesJ.next()) {
                // Crear un array para la fila y llenarlo con datos
                Object[] datos = new Object[10];
                datos[0] = rsClientesJ.getString("codigo");
                datos[1] = rsClientesJ.getString("pais");
                datos[2] = rsClientesJ.getString("nombres");
                datos[3] = rsClientesJ.getString("numero_documento");
                datos[4] = rsClientesJ.getString("nombres");
                datos[5] = rsClientesJ.getString("razon_social");
                datos[6] = rsClientesJ.getString("direccion");
                datos[7] = rsClientesJ.getString("telefono");
                datos[8] = rsClientesJ.getString("correo");
                datos[9] = rsClientesJ.getString("estado");

                // Añadir la fila al modelo
                modelo.addRow(datos);
            }

            // Establecer el modelo en la tabla
            tblPersonaNatural.setModel(modelo);
            tblPersonaNatural.getTableHeader().setReorderingAllowed(false);

            // Ajustar el tamaño de las columnas al contenido
            adjustColumnSizes(tblPersonaNatural);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar en tabla: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage());
        }
    }

    private void adjustColumnSizes(JTable table) {
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = table.getColumnModel().getColumn(column).getHeaderValue().toString().length() * 10; // Estimar el ancho del encabezado

            // Recorrer cada fila para calcular el ancho de las celdas
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component cellComp = table.prepareRenderer(cellRenderer, row, column);
                width = Math.max(cellComp.getPreferredSize().width + 5, width); // +5 para un poco de margen
            }

            // Establecer el ancho preferido de la columna
            table.getColumnModel().getColumn(column).setPreferredWidth(width);
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPersonaNatural = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtNumDocumento = new javax.swing.JTextField();
        lblApeRaz = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        rbtnPN = new javax.swing.JRadioButton();
        rbtnPJ = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Elegir Cliente");
        setBackground(new java.awt.Color(255, 215, 171));

        jPanel1.setBackground(new java.awt.Color(243, 226, 210));
        jPanel1.setPreferredSize(new java.awt.Dimension(1332, 725));

        tblPersonaNatural.setBackground(new java.awt.Color(255, 255, 255));
        tblPersonaNatural.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        tblPersonaNatural.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblPersonaNatural.setGridColor(new java.awt.Color(0, 0, 0));
        tblPersonaNatural.setSelectionForeground(new java.awt.Color(230, 182, 139));
        jScrollPane1.setViewportView(tblPersonaNatural);

        btnGuardar.setBackground(new java.awt.Color(113, 49, 18));
        btnGuardar.setForeground(new java.awt.Color(245, 224, 206));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/guardar_32px.png"))); // NOI18N
        btnGuardar.setText("SELECCIONAR");
        btnGuardar.setPreferredSize(new java.awt.Dimension(115, 41));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(113, 49, 18));
        btnSalir.setForeground(new java.awt.Color(245, 224, 206));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/salir_32px.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Num. Documento:");

        txtNumDocumento.setBackground(new java.awt.Color(255, 255, 255));
        txtNumDocumento.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        txtNumDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocumentoActionPerformed(evt);
            }
        });
        txtNumDocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNumDocumentoKeyReleased(evt);
            }
        });

        lblApeRaz.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblApeRaz.setForeground(new java.awt.Color(0, 0, 0));
        lblApeRaz.setText("Apellido:");

        txtApellido.setBackground(new java.awt.Color(255, 255, 255));
        txtApellido.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtApellidoKeyReleased(evt);
            }
        });

        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nombre:");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Tipo persona:");

        rbtnPN.setBackground(new java.awt.Color(243, 226, 210));
        buttonGroup1.add(rbtnPN);
        rbtnPN.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        rbtnPN.setForeground(new java.awt.Color(0, 0, 0));
        rbtnPN.setText("Persona Natural");
        rbtnPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnPNActionPerformed(evt);
            }
        });

        rbtnPJ.setBackground(new java.awt.Color(243, 226, 210));
        buttonGroup1.add(rbtnPJ);
        rbtnPJ.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        rbtnPJ.setForeground(new java.awt.Color(0, 0, 0));
        rbtnPJ.setText("Persona Juridica");
        rbtnPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnPJActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(lblApeRaz)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNumDocumento)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rbtnPN)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rbtnPJ))
                                    .addComponent(txtNombre)
                                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(351, 351, 351)
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 357, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(rbtnPN)
                    .addComponent(rbtnPJ))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApeRaz)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1069, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (tblPersonaNatural.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Debe elegir un cliente!", "Mensaje de Sistema", JOptionPane.WARNING_MESSAGE);
        } else {
            codCli = Integer.valueOf(tblPersonaNatural.getValueAt(tblPersonaNatural.getSelectedRow(), 0).toString());
            this.dispose();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void rbtnPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnPNActionPerformed
        if (rbtnPN.isSelected()) {
            lblApeRaz.setText("Apellido:");
            listarClientesNaturales();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnPNActionPerformed

    private void rbtnPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnPJActionPerformed
        if (rbtnPJ.isSelected()) {
            lblApeRaz.setText("Razón Social:");
            listarClientesJuridicos();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnPJActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
        if (rbtnPN.isSelected()) {
            listarClientesNaturales();
        }else{
            listarClientesJuridicos();
        }
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
        if (rbtnPN.isSelected()) {
            listarClientesNaturales();
        }else{
            listarClientesJuridicos();
        }
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void txtNumDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocumentoActionPerformed
        // TODO add your handling code here:
        if (rbtnPN.isSelected()) {
            listarClientesNaturales();
        }else{
            listarClientesJuridicos();
        }
    }//GEN-LAST:event_txtNumDocumentoActionPerformed

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
        // TODO add your handling code here:
        if (rbtnPN.isSelected()) {
            listarClientesNaturales();
        }else{
            listarClientesJuridicos();
        }
    }//GEN-LAST:event_txtNombreKeyReleased

    private void txtApellidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyReleased
        // TODO add your handling code here:
        if (rbtnPN.isSelected()) {
            listarClientesNaturales();
        }else{
            listarClientesJuridicos();
        }
    }//GEN-LAST:event_txtApellidoKeyReleased

    private void txtNumDocumentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumDocumentoKeyReleased
        // TODO add your handling code here:
        if (rbtnPN.isSelected()) {
            listarClientesNaturales();
        }else{
            listarClientesJuridicos();
        }
    }//GEN-LAST:event_txtNumDocumentoKeyReleased

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblApeRaz;
    private javax.swing.JRadioButton rbtnPJ;
    private javax.swing.JRadioButton rbtnPN;
    private javax.swing.JTable tblPersonaNatural;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumDocumento;
    // End of variables declaration//GEN-END:variables
}
