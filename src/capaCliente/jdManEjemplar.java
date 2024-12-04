/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.clsEjemplar;
import capaLogica.clsSede;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ander
 */
public class jdManEjemplar extends javax.swing.JDialog {

    clsSede objSede = new clsSede();
    clsEjemplar objEjemplar = new clsEjemplar();

    public jdManEjemplar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Mantenimiento de ejemplar");
        initComponents();
    }

    private void listarComboSede() {
        ResultSet rs = null;
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        cboSede.setModel(modelo);

        try {
            rs = objSede.listarSede();
            while (rs.next()) {
                modelo.addElement(rs.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al listar el combo Sede --->" + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtISBN.setText("");
        txtNombreLibro.setText("");
        txtEditorial.setText("");
        chkVigencia.setSelected(false);
    }

    private void camposNoEdit() {
        txtISBN.setEditable(false);
        txtNombreLibro.setEditable(false);
        txtEditorial.setEditable(false);
    }

    public void listarEjemplaresSedeInd() {
        ResultSet rsEjem = null;
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        modelo.addColumn("Codigo");
        modelo.addColumn("Libro");
        modelo.addColumn("ISBN");
        modelo.addColumn("Editorial");
        modelo.addColumn("Sede");
        modelo.addColumn("Estado");
        tblEjemplaresSede.setModel(modelo);
        tblEjemplaresSede.getTableHeader().setReorderingAllowed(false);

        try {
            rsEjem = objEjemplar.listarEjemplarSedeIndiv();
            while (rsEjem.next()) {
                String estado = "";

                    switch (rsEjem.getString("estado")) {
                        case "P":
                            estado = "Prestado";
                            break;
                        case "X":
                            estado = "Dañado";
                            break;
                        case "D":
                            estado = "Disponible";
                            break;
                        case "R":
                            estado = "Reservado";
                            break;
                        default:
                            throw new Exception("Error al obtener estado");
                    }
                modelo.addRow(new Object[]{
                    rsEjem.getString("codigo"),
                    rsEjem.getString("libro"),
                    rsEjem.getString("isbn"),
                    rsEjem.getString("editorial"),
                    rsEjem.getString("sede"),
                    estado
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al listar Ejemplares --->" + e.getMessage());
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEjemplaresSede = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        btnModificar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnDarBaja = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEditorial = new javax.swing.JTextField();
        cboSede = new javax.swing.JComboBox<>();
        txtISBN = new javax.swing.JTextField();
        btnFindCodigo = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNombreLibro = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        chkVigencia = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mantenimiento de Ejemplar");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(230, 182, 139));

        jSeparator1.setBackground(new java.awt.Color(113, 49, 18));
        jSeparator1.setForeground(new java.awt.Color(113, 49, 18));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        tblEjemplaresSede.setBackground(new java.awt.Color(245, 224, 206));
        tblEjemplaresSede.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblEjemplaresSede.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEjemplaresSedeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEjemplaresSede);

        jSeparator2.setBackground(new java.awt.Color(113, 49, 18));
        jSeparator2.setForeground(new java.awt.Color(113, 49, 18));

        jPanel2.setBackground(new java.awt.Color(230, 182, 139));

        btnModificar.setBackground(new java.awt.Color(113, 49, 18));
        btnModificar.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(245, 224, 206));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/editar_32px.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(113, 49, 18));
        btnSalir.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(245, 224, 206));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/salir_32px.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnDarBaja.setBackground(new java.awt.Color(113, 49, 18));
        btnDarBaja.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        btnDarBaja.setForeground(new java.awt.Color(245, 224, 206));
        btnDarBaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/darBaja_32px.png"))); // NOI18N
        btnDarBaja.setText("DAR BAJA");
        btnDarBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDarBajaActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(113, 49, 18));
        btnLimpiar.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(245, 224, 206));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/limpiar_32px.png"))); // NOI18N
        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.setMaximumSize(new java.awt.Dimension(107, 39));
        btnLimpiar.setMinimumSize(new java.awt.Dimension(107, 39));
        btnLimpiar.setPreferredSize(new java.awt.Dimension(107, 39));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnDarBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar)
                    .addComponent(btnDarBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(230, 182, 139));

        txtCodigo.setBackground(new java.awt.Color(245, 224, 206));
        txtCodigo.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtCodigo.setForeground(new java.awt.Color(113, 49, 18));
        txtCodigo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(113, 49, 18));
        jLabel5.setText("Editorial:");

        txtEditorial.setBackground(new java.awt.Color(245, 224, 206));
        txtEditorial.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtEditorial.setForeground(new java.awt.Color(113, 49, 18));
        txtEditorial.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        cboSede.setBackground(new java.awt.Color(245, 224, 206));
        cboSede.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
        cboSede.setForeground(new java.awt.Color(113, 49, 18));
        cboSede.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtISBN.setBackground(new java.awt.Color(245, 224, 206));
        txtISBN.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtISBN.setForeground(new java.awt.Color(113, 49, 18));
        txtISBN.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnFindCodigo.setBackground(new java.awt.Color(113, 49, 18));
        btnFindCodigo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/buscar_24px.png"))); // NOI18N
        btnFindCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindCodigoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(113, 49, 18));
        jLabel7.setText("ISBN:");

        jLabel6.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(113, 49, 18));
        jLabel6.setText("Estado:");

        jLabel2.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(113, 49, 18));
        jLabel2.setText("Libro:");

        txtNombreLibro.setEditable(false);
        txtNombreLibro.setBackground(new java.awt.Color(245, 224, 206));
        txtNombreLibro.setColumns(20);
        txtNombreLibro.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtNombreLibro.setForeground(new java.awt.Color(113, 49, 18));
        txtNombreLibro.setLineWrap(true);
        txtNombreLibro.setRows(5);
        txtNombreLibro.setWrapStyleWord(true);
        txtNombreLibro.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setViewportView(txtNombreLibro);

        jLabel3.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(113, 49, 18));
        jLabel3.setText("Sede:");

        jLabel4.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(113, 49, 18));
        jLabel4.setText("Código:");

        chkVigencia.setBackground(new java.awt.Color(230, 182, 139));
        chkVigencia.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
        chkVigencia.setForeground(new java.awt.Color(113, 49, 18));
        chkVigencia.setText("(Disponible)");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel4))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFindCodigo))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkVigencia)
                            .addComponent(cboSede, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(btnFindCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboSede, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(chkVigencia))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 754, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
        camposNoEdit();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        listarComboSede();
        listarEjemplaresSedeInd();
        camposNoEdit();
    }//GEN-LAST:event_formWindowOpened

    private void tblEjemplaresSedeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEjemplaresSedeMouseClicked
//        if (chkIndividual.isSelected()) {
//            txtCodigo.setText(String.valueOf(tblEjemplaresSede.getValueAt(tblEjemplaresSede.getSelectedRow(), 0)));
//        } else {
//            int columCount = tblEjemplaresSede.getColumnCount();
//
//            String libro = (String) (tblEjemplaresSede.getValueAt(tblEjemplaresSede.getSelectedRow(), 1));
//
//            String sede = null;
//            if (columCount > 2) {
//                sede = (String) (tblEjemplaresSede.getValueAt(tblEjemplaresSede.getSelectedRow(), 2));
//            }
//            
//            ResultSet rsCodigos = null;
//            try {
//                if(sede!=null){
//                    rsCodigos = objEjemplar.obtenerCodigoEjemplar(libro, sede);
//                }else{
//                    rsCodigos = objEjemplar.obtenerCodigoEjemplar(libro);
//                }
//                
//                StringBuilder codigosConcatenados = new StringBuilder();
//                while (rsCodigos.next()){
//                    if (codigosConcatenados.length() > 0) {
//                        codigosConcatenados.append(", ");
//                    }
//                    codigosConcatenados.append(rsCodigos.getInt("codigo"));
//                }
//                rsCodigos.close();
//                txtCodigo.setText(codigosConcatenados.toString());
//            } catch (Exception e) {
//                
//            }   
//        }
        txtCodigo.setText(String.valueOf(tblEjemplaresSede.getValueAt(tblEjemplaresSede.getSelectedRow(), 0)));
        btnFindCodigoActionPerformed(null);
    }//GEN-LAST:event_tblEjemplaresSedeMouseClicked

    private void btnFindCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindCodigoActionPerformed
        ResultSet rsEjemplar = null;
        try {
            if (txtCodigo.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un codigo para buscar");
            } else {
                rsEjemplar = objEjemplar.buscarPorCodigo(Integer.parseInt(txtCodigo.getText()));
                if (rsEjemplar.next()) {
                    txtISBN.setText(rsEjemplar.getString("isbn"));
                    txtNombreLibro.setText(rsEjemplar.getString("libro"));
                    txtEditorial.setText(rsEjemplar.getString("editorial"));
                    cboSede.setSelectedItem(rsEjemplar.getString("sede"));
                    if (rsEjemplar.getString("estado").equals("D")) {
                        chkVigencia.setSelected(true);
                    } else {
                        chkVigencia.setSelected(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Código ingresado no encontrado");
                    limpiarCampos();
                    camposNoEdit();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnFindCodigoActionPerformed

    private void btnDarBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarBajaActionPerformed
        int rp;
        try {
            if (txtCodigo.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Debe ingresar el codigo del ejemplar a dar de baja");
            } else {
                if (chkVigencia.isSelected()) {
                    rp = JOptionPane.showConfirmDialog(this, "Esta seguro de dar de baja el ejemplar:\n " + txtNombreLibro.getText()
                            + " de la editorial " + txtEditorial.getText(), "Dar de baja", JOptionPane.YES_NO_OPTION);
                    if (rp == 0) {
                        objEjemplar.darBajaEjemplarr(Integer.parseInt(txtCodigo.getText()));
                        limpiarCampos();
                        camposNoEdit();
                        listarEjemplaresSedeInd();
                    } else {
                        JOptionPane.showMessageDialog(this, "Dar de baja cancelado");
                    }
                }else{
                    JOptionPane.showMessageDialog(this, "El ejemplar ya esta dado de baja (No disponible)");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnDarBajaActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int rp;
        String modi = "X";
        try {
            if (txtCodigo.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Debe ingresar el codigo del ejemplar a modificar");
            }else{
                rp = JOptionPane.showConfirmDialog(this, "Esta seguro de modificar el ejemplar:\n " + txtNombreLibro.getText()
                            + " de la editorial " + txtEditorial.getText(), "Modificar", JOptionPane.YES_NO_OPTION);
                if (rp==0) {
                    if (chkVigencia.isSelected()) {
                        modi = "D";
                    }
                    objEjemplar.modificarEjemplar(
                            Integer.parseInt(txtCodigo.getText()),
                            objSede.obtenerSede(cboSede.getSelectedItem().toString()), 
                            modi);
                    limpiarCampos();
                    camposNoEdit();
                    listarEjemplaresSedeInd();
                }else{
                    JOptionPane.showMessageDialog(this, "Modificación cancelada");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDarBaja;
    private javax.swing.JButton btnFindCodigo;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cboSede;
    private javax.swing.JCheckBox chkVigencia;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tblEjemplaresSede;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEditorial;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextArea txtNombreLibro;
    // End of variables declaration//GEN-END:variables
}
