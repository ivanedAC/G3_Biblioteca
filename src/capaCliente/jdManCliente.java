/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.clsCliente;
import capaLogica.clsPais;
import capaLogica.clsTipoDocumento;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Chinc
 */
public class jdManCliente extends javax.swing.JDialog {

    /**
     * Creates new form jdManCliente
     */
    clsPais objPais = new clsPais();
    clsTipoDocumento objDocumento = new clsTipoDocumento();
    clsCliente objCliente = new clsCliente();

    public jdManCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        listarPaises();
        listartipoDocumento();
        listarClientesNaturales();
        listarClientesJuridicos();
    }

    public void personaJuridica() {
        lblTelefono.setVisible(false);
        lblFecha.setVisible(false);
        lblSexo.setVisible(false);
        lblapeMa.setVisible(false);
        lblApePaRS.setText("RAZON SOCIAL");
        txtApeMa.setVisible(false);
        txtFecha.setVisible(false);
        txtTelefono.setVisible(false);
        cbxSexo.setVisible(false);
    }

    public void personaNatural() {
        lblTelefono.setVisible(true);
        lblFecha.setVisible(true);
        lblSexo.setVisible(true);
        lblapeMa.setVisible(true);
        lblApePaRS.setText("APELLIDO PATERNO");
        txtApeMa.setVisible(true);
        txtFecha.setVisible(true);
        txtTelefono.setVisible(true);
        cbxSexo.setVisible(true);
    }

    public void listarClientesNaturales() {
        ResultSet rsClientesN = null;
        String sexo = "";
        DefaultTableModel modelo = new DefaultTableModel();
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

        String nombrepais = "";
        String nombreTipoDoc = "";
        try {
            rsClientesN = objCliente.listarClientesN();
            while (rsClientesN.next()) {
                Object datos[][] = new Object[1][6];
                datos[0][0] = rsClientesN.getString("codigo");
                nombrepais = objPais.buscarPaisPorCodigo(rsClientesN.getInt("cod_pais"));
                datos[0][1] = nombrepais;
                nombreTipoDoc = objDocumento.nombreTipoDocumento(rsClientesN.getInt("cod_tipo_doc"));
                datos[0][2] = nombreTipoDoc;
                datos[0][3] = rsClientesN.getString("numero_documento");
                datos[0][4] = rsClientesN.getString("nombres");
                datos[0][5] = rsClientesN.getString("ape_paterno")+" "+rsClientesN.getString("ape_materno");

                modelo.addRow(datos[0]);
            }
            tblPersonaNatural.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error al listar en tabla" + e.getMessage());
        }
    }

    public void listarClientesJuridicos() {
        ResultSet rsClientesN = null;
        String sexo = "";
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Pais");
        modelo.addColumn("Nombre");
        String nombrepais = "";
        try {
            rsClientesN = objCliente.listarClientesJ();
            while (rsClientesN.next()) {
                Object datos[][] = new Object[1][3];
                datos[0][0] = rsClientesN.getString("codigo");
                nombrepais = objPais.buscarPaisPorCodigo(rsClientesN.getInt("cod_pais"));
                datos[0][1] = nombrepais;
                modelo.addRow(datos[0]);
            }
            tblPersonaJuridicas.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error al listar en tabla" + e.getMessage());
        }
    }

    private void listarPaises() {
        ResultSet rsPais = null;
        DefaultComboBoxModel modeloPais = new DefaultComboBoxModel();
        cbxPais.setModel(modeloPais);
        try {
            rsPais = objPais.listarPais();
            while (rsPais.next()) {
                modeloPais.addElement(rsPais.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void listartipoDocumento() {
        ResultSet rsDocumentos = null;
        DefaultComboBoxModel modeloDoc = new DefaultComboBoxModel();
        cbxtTipoDoc.setModel(modeloDoc);
        try {
            rsDocumentos = objDocumento.listarTipoDocumento();
            while (rsDocumentos.next()) {
                modeloDoc.addElement(rsDocumentos.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPersonaNatural = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        lblTipoDoc = new javax.swing.JLabel();
        cbxPais = new javax.swing.JComboBox<>();
        lblDoc = new javax.swing.JLabel();
        txtDocIdent = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblApePaRS = new javax.swing.JLabel();
        txtApePaRS = new javax.swing.JTextField();
        lblapeMa = new javax.swing.JLabel();
        txtApeMa = new javax.swing.JTextField();
        lblSexo = new javax.swing.JLabel();
        cbxSexo = new javax.swing.JComboBox<>();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JFormattedTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        lblPais = new javax.swing.JLabel();
        cbxtTipoDoc = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPersonaJuridicas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 215, 171));
        setMaximumSize(new java.awt.Dimension(32767, 32767));
        setPreferredSize(new java.awt.Dimension(1700, 780));

        jPanel1.setBackground(new java.awt.Color(230, 182, 139));
        jPanel1.setPreferredSize(new java.awt.Dimension(1700, 780));

        jPanel2.setBackground(new java.awt.Color(230, 182, 139));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton6.setBackground(new java.awt.Color(113, 49, 18));
        jButton6.setForeground(new java.awt.Color(245, 224, 206));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/darBaja_32px.png"))); // NOI18N
        jButton6.setText("DAR DE BAJA");

        jButton5.setBackground(new java.awt.Color(113, 49, 18));
        jButton5.setForeground(new java.awt.Color(245, 224, 206));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/editar_32px.png"))); // NOI18N
        jButton5.setText("MODIFICAR");

        jButton7.setBackground(new java.awt.Color(113, 49, 18));
        jButton7.setForeground(new java.awt.Color(245, 224, 206));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar_32px.png"))); // NOI18N
        jButton7.setText("ELIMINAR");

        jButton4.setBackground(new java.awt.Color(113, 49, 18));
        jButton4.setForeground(new java.awt.Color(245, 224, 206));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/limpiar_32px.png"))); // NOI18N
        jButton4.setText("LIMPIAR");

        jButton8.setBackground(new java.awt.Color(113, 49, 18));
        jButton8.setForeground(new java.awt.Color(245, 224, 206));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/salir_32px.png"))); // NOI18N
        jButton8.setText("SALIR");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(113, 49, 18));
        jButton3.setForeground(new java.awt.Color(245, 224, 206));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/guardar_32px.png"))); // NOI18N
        jButton3.setText("GUARDAR");
        jButton3.setPreferredSize(new java.awt.Dimension(115, 41));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(230, 182, 139));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblPersonaNatural.setBackground(new java.awt.Color(230, 182, 139));
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
        tblPersonaNatural.setGridColor(new java.awt.Color(0, 0, 0));
        tblPersonaNatural.setSelectionForeground(new java.awt.Color(230, 182, 139));
        jScrollPane1.setViewportView(tblPersonaNatural);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                .addGap(60, 60, 60))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(230, 182, 139));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTipoDoc.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        lblTipoDoc.setText("TIPO DOCUMENTO");

        cbxPais.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblDoc.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        lblDoc.setText("DOC. IDENTIDAD");

        txtDocIdent.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N

        lblNombre.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        lblNombre.setText("NOMBRE");

        txtNombre.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N

        lblApePaRS.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        lblApePaRS.setText("APELLIDO PATERNO");

        txtApePaRS.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N

        lblapeMa.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        lblapeMa.setText("APELLIDO MATERNO");

        txtApeMa.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N

        lblSexo.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        lblSexo.setText("SEXO");

        cbxSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino", " " }));

        lblFecha.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        lblFecha.setText("FECHA NACIMIENTO");

        txtFecha.setText("jFormattedTextField1");

        lblTelefono.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        lblTelefono.setText("TELEFONO");

        txtTelefono.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/autor (1).png"))); // NOI18N

        lblPais.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        lblPais.setText("PAIS");

        cbxtTipoDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxtTipoDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxtTipoDocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblapeMa)
                    .addComponent(lblTipoDoc)
                    .addComponent(lblApePaRS)
                    .addComponent(lblSexo)
                    .addComponent(lblFecha)
                    .addComponent(lblTelefono)
                    .addComponent(lblDoc)
                    .addComponent(lblNombre)
                    .addComponent(lblPais))
                .addGap(42, 42, 42)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtFecha)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDocIdent)
                            .addComponent(cbxPais, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtApePaRS)
                        .addComponent(cbxtTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtApeMa)
                        .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(55, 55, 55)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoDoc)
                    .addComponent(cbxtTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDoc)
                    .addComponent(txtDocIdent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPais)
                    .addComponent(cbxPais, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApePaRS)
                    .addComponent(txtApePaRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtApeMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblapeMa, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSexo)
                    .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFecha)
                    .addComponent(txtFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTelefono)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(230, 182, 139));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblPersonaJuridicas.setBackground(new java.awt.Color(230, 182, 139));
        tblPersonaJuridicas.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPersonaJuridicas.setGridColor(new java.awt.Color(0, 0, 0));
        tblPersonaJuridicas.setSelectionForeground(new java.awt.Color(230, 182, 139));
        jScrollPane2.setViewportView(tblPersonaJuridicas);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addGap(60, 60, 60))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            objCliente.insertarClienteJuridico(139, 1, "12876543", "prueba", "pryueba", "acapulco", "971133939", "2024-08-12", "luis@fdsflj", "A");
            // TODO add your handling code here:
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void cbxtTipoDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxtTipoDocActionPerformed
        if (cbxtTipoDoc.getSelectedIndex() == 1) {
            personaJuridica();
        } else {
            personaNatural();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxtTipoDocActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JComboBox<String> cbxPais;
    private javax.swing.JComboBox<String> cbxSexo;
    private javax.swing.JComboBox<String> cbxtTipoDoc;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblApePaRS;
    private javax.swing.JLabel lblDoc;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTipoDoc;
    private javax.swing.JLabel lblapeMa;
    private javax.swing.JTable tblPersonaJuridicas;
    private javax.swing.JTable tblPersonaNatural;
    private javax.swing.JTextField txtApeMa;
    private javax.swing.JTextField txtApePaRS;
    private javax.swing.JTextField txtDocIdent;
    private javax.swing.JFormattedTextField txtFecha;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
