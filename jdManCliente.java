/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.clsCliente;
import capaLogica.clsPais;
import capaLogica.clsTipoDocumento;
import java.awt.Color;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

    public void clearfields() {
        txtApeMa.setText("");
        txtApePaRS.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtDocIdent.setText("");
        txtFecha.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        cbxPais.setSelectedIndex(0);
        cbxSexo.setSelectedIndex(0);
        cbxtTipoDoc.setSelectedIndex(0);
    }

    public void personaJuridica() {
        lblFecha.setVisible(false);
        lblSexo.setVisible(false);
        lblapeMa.setVisible(false);
        lblApePaRS.setText("RAZON SOCIAL");
        txtApeMa.setVisible(false);
        txtFecha.setVisible(false);
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
                Object datos[][] = new Object[1][11];
                datos[0][0] = rsClientesN.getString("codigo");
                nombrepais = objPais.buscarPaisPorCodigo(rsClientesN.getInt("cod_pais"));
                datos[0][1] = nombrepais;
                nombreTipoDoc = objDocumento.nombreTipoDocumento(rsClientesN.getInt("cod_tipo_doc"));
                datos[0][2] = nombreTipoDoc;
                datos[0][3] = rsClientesN.getString("numero_documento");
                datos[0][4] = rsClientesN.getString("nombres");
                datos[0][5] = rsClientesN.getString("ape_paterno") + " " + rsClientesN.getString("ape_materno");
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

                modelo.addRow(datos[0]);
            }
            tblPersonaNatural.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error al listar en tabla" + e.getMessage());
        }
    }

    public void listarClientesJuridicos() {
        ResultSet rsClientesJ = null;
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Pais");
        modelo.addColumn("Tipo doc");
        modelo.addColumn("N° documento");
        modelo.addColumn("Nombres");
        modelo.addColumn("Razon Social");
        modelo.addColumn("Direccion");
        modelo.addColumn("Telefono");
        modelo.addColumn("Correo");
        String nombrepais = "";
        String nombreTipoDoc = "";
        try {
            rsClientesJ = objCliente.listarClientesJ();
            while (rsClientesJ.next()) {
                Object datos[][] = new Object[1][9];
                datos[0][0] = rsClientesJ.getString("codigo");
                nombrepais = objPais.buscarPaisPorCodigo(rsClientesJ.getInt("cod_pais"));
                datos[0][1] = nombrepais;
                nombreTipoDoc = objDocumento.nombreTipoDocumento(rsClientesJ.getInt("cod_tipo_doc"));
                datos[0][2] = nombreTipoDoc;
                datos[0][3] = rsClientesJ.getString("numero_documento");
                datos[0][4] = rsClientesJ.getString("nombres");
                datos[0][5] = rsClientesJ.getString("razon_social");
                datos[0][6] = rsClientesJ.getString("direccion");
                datos[0][7] = rsClientesJ.getString("telefono");
                datos[0][8] = rsClientesJ.getString("correo");

                modelo.addRow(datos[0]);
            }
            tblPersonaJuridicas.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error al listar en tabla" + e.getMessage());
        }
    }

    public void formulariotamano() {
        Dimension fixedSize = new Dimension(220, 29);
        txtApePaRS.setPreferredSize(fixedSize);
        txtApePaRS.setMinimumSize(fixedSize);
        txtApePaRS.setMaximumSize(fixedSize);
        txtNombre.setPreferredSize(fixedSize);
        txtNombre.setMinimumSize(fixedSize);
        txtNombre.setMaximumSize(fixedSize);
        txtDireccion.setPreferredSize(fixedSize);
        txtDireccion.setMinimumSize(fixedSize);
        txtDireccion.setMaximumSize(fixedSize);
        txtTelefono.setPreferredSize(fixedSize);
        txtTelefono.setMinimumSize(fixedSize);
        txtTelefono.setMaximumSize(fixedSize);
        txtCorreo.setPreferredSize(fixedSize);
        txtCorreo.setMinimumSize(fixedSize);
        txtCorreo.setMaximumSize(fixedSize);
        cbxPais.setPreferredSize(fixedSize);
        cbxPais.setMinimumSize(fixedSize);
        cbxPais.setMaximumSize(fixedSize);
        cbxtTipoDoc.setPreferredSize(fixedSize);
        cbxtTipoDoc.setMinimumSize(fixedSize);
        cbxtTipoDoc.setMaximumSize(fixedSize);
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

    public String currentdate() {
        int dia, mes, año;
        String fechaR = "";
        Calendar cal = new GregorianCalendar();
        dia = cal.get(Calendar.DAY_OF_MONTH);
        mes = cal.get(Calendar.MONTH) + 1;
        año = cal.get(Calendar.YEAR);
        return fechaR = año + "-" + mes + "-" + dia;
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
        btnLimpiar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPersonaNatural = new javax.swing.JTable();
        panelFormulario = new javax.swing.JPanel();
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
        lblDireccion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
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
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(113, 49, 18));
        jButton5.setForeground(new java.awt.Color(245, 224, 206));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/editar_32px.png"))); // NOI18N
        jButton5.setText("MODIFICAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(113, 49, 18));
        jButton7.setForeground(new java.awt.Color(245, 224, 206));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar_32px.png"))); // NOI18N
        jButton7.setText("ELIMINAR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(113, 49, 18));
        btnLimpiar.setForeground(new java.awt.Color(245, 224, 206));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/limpiar_32px.png"))); // NOI18N
        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
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

        btnGuardar.setBackground(new java.awt.Color(113, 49, 18));
        btnGuardar.setForeground(new java.awt.Color(245, 224, 206));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/guardar_32px.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setPreferredSize(new java.awt.Dimension(115, 41));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
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
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
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
        tblPersonaNatural.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPersonaNaturalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPersonaNatural);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );

        panelFormulario.setBackground(new java.awt.Color(230, 182, 139));
        panelFormulario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/buscar_24px.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        lblPais.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        lblPais.setText("PAIS");

        cbxtTipoDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxtTipoDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxtTipoDocActionPerformed(evt);
            }
        });

        lblDireccion.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        lblDireccion.setText("DIRECCION");

        txtDireccion.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N

        lblCorreo.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        lblCorreo.setText("CORREO");

        txtCorreo.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N

        jCheckBox1.setBackground(new java.awt.Color(230, 182, 139));
        jCheckBox1.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jCheckBox1.setText("Vigencia");

        javax.swing.GroupLayout panelFormularioLayout = new javax.swing.GroupLayout(panelFormulario);
        panelFormulario.setLayout(panelFormularioLayout);
        panelFormularioLayout.setHorizontalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblapeMa)
                    .addComponent(lblTipoDoc)
                    .addComponent(lblApePaRS)
                    .addComponent(lblSexo)
                    .addComponent(lblFecha)
                    .addComponent(lblTelefono)
                    .addComponent(lblDoc)
                    .addComponent(lblNombre)
                    .addComponent(lblPais)
                    .addComponent(lblDireccion)
                    .addComponent(lblCorreo))
                .addGap(42, 42, 42)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox1)
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                            .addComponent(cbxtTipoDoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxPais, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtApePaRS)
                            .addComponent(txtApeMa)
                            .addComponent(cbxSexo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTelefono)
                            .addComponent(txtDireccion)
                            .addComponent(txtCorreo)
                            .addComponent(txtDocIdent)
                            .addComponent(txtNombre))
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelFormularioLayout.setVerticalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoDoc)
                    .addComponent(cbxtTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblDoc)
                        .addComponent(txtDocIdent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPais)
                    .addComponent(cbxPais, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApePaRS)
                    .addComponent(txtApePaRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtApeMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblapeMa, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSexo)
                    .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFecha)
                    .addComponent(txtFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTelefono)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDireccion)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCorreo)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox1)
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
        tblPersonaJuridicas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPersonaJuridicasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPersonaJuridicas);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
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
                .addComponent(panelFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                    .addComponent(panelFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        try {

            boolean sexo;
            int codPais = objPais.buscarCodigoPorNombre(cbxPais.getSelectedItem().toString());
            String fechaR = currentdate();

            if (cbxtTipoDoc.getSelectedIndex() == 1) {
                objCliente.insertarClienteJuridico(codPais, cbxtTipoDoc.getSelectedIndex() + 1, txtDocIdent.getText(), txtNombre.getText(), txtApePaRS.getText(), txtDireccion.getText(), txtTelefono.getText(), fechaR, txtCorreo.getText(), "A");
                JOptionPane.showMessageDialog(this, "Se ha insertado un nuevo cliente");
            } else {
                if (cbxSexo.getSelectedItem().toString().equals("Masculino")) {
                    sexo = true;
                } else {
                    sexo = false;
                }
                objCliente.insertarClienteNatural(codPais, cbxtTipoDoc.getSelectedIndex() + 1, txtDocIdent.getText(), txtNombre.getText(), txtApePaRS.getText(), txtApeMa.getText(), sexo, txtFecha.getText(), txtDireccion.getText(), txtTelefono.getText(), fechaR, txtCorreo.getText(), "A");
                JOptionPane.showMessageDialog(this, "Se ha insertado un nuevo cliente");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "error-->" + ex.getMessage());
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void cbxtTipoDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxtTipoDocActionPerformed
        if (cbxtTipoDoc.getSelectedIndex() == 1) {
            personaJuridica();
        } else {
            personaNatural();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxtTipoDocActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        clearfields();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            ResultSet rs = objCliente.obtenerClientePorDoc(txtDocIdent.getText());
            while (rs.next()) {
                cbxtTipoDoc.setSelectedIndex(rs.getInt("cod_tipo_doc") - 1);
                if (rs.getInt("cod_tipo_doc") == 2) {
                    cbxPais.setSelectedItem(objPais.buscarPaisPorCodigo(rs.getInt("cod_pais")));
                    txtNombre.setText(rs.getString("nombres"));
                    txtApePaRS.setText(rs.getString("razon_social"));
                    txtDireccion.setText(rs.getString("direccion"));
                    txtTelefono.setText(rs.getString("telefono"));
                    txtCorreo.setText(rs.getString("correo"));
                    formulariotamano();
                } else {
                    cbxPais.setSelectedItem(objPais.buscarPaisPorCodigo(rs.getInt("cod_pais")));
                    txtNombre.setText(rs.getString("nombres"));
                    txtApePaRS.setText(rs.getString("ape_paterno"));
                    if (rs.getBoolean("sexo")) {
                        cbxSexo.setSelectedIndex(0);
                    } else {
                        cbxSexo.setSelectedIndex(1);
                    }
                    txtFecha.setText(rs.getString("f_nacimiento"));
                    txtApeMa.setText(rs.getString("ape_materno"));
                    txtDireccion.setText(rs.getString("direccion"));
                    txtTelefono.setText(rs.getString("telefono"));
                    txtCorreo.setText(rs.getString("correo"));
                }
            }
            // TODO add your handling code here:
        } catch (Exception ex) {
            Logger.getLogger(jdManCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblPersonaNaturalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPersonaNaturalMouseClicked
        txtDocIdent.setText(String.valueOf(tblPersonaNatural.getValueAt(tblPersonaNatural.getSelectedRow(), 3)));
        btnBuscarActionPerformed(null);
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPersonaNaturalMouseClicked

    private void tblPersonaJuridicasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPersonaJuridicasMouseClicked
        txtDocIdent.setText(String.valueOf(tblPersonaJuridicas.getValueAt(tblPersonaJuridicas.getSelectedRow(), 3)));
        btnBuscarActionPerformed(null);
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPersonaJuridicasMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (cbxtTipoDoc.getSelectedIndex() == 1) {
            try {
                objCliente.modificarClienteJuridico(objPais.buscarCodigoPorNombre(cbxPais.getSelectedItem().toString()), (int) cbxtTipoDoc.getSelectedIndex() + 1, txtDocIdent.getText(), txtNombre.getText(), txtApePaRS.getText(), txtDireccion.getText(), txtTelefono.getText(), currentdate().toString(), txtCorreo.getText(), "A");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        } else {
            try {
                objCliente.modificarClienteNatural(objPais.buscarCodigoPorNombre(cbxPais.getSelectedItem().toString()), (int) cbxtTipoDoc.getSelectedIndex() + 1, txtDocIdent.getText(), txtNombre.getText(), txtApePaRS.getText(), txtApeMa.getText(), txtFecha.getText(), txtDireccion.getText(), txtTelefono.getText(), currentdate().toString(), txtCorreo.getText(), "A");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            objCliente.darBajaCliente(txtDocIdent.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            objCliente.eliminarCliente(txtDocIdent.getText());
             JOptionPane.showMessageDialog(this, "Cliente eliminado");
            // TODO add your handling code here:
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cbxPais;
    private javax.swing.JComboBox<String> cbxSexo;
    private javax.swing.JComboBox<String> cbxtTipoDoc;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblApePaRS;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblDoc;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTipoDoc;
    private javax.swing.JLabel lblapeMa;
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JTable tblPersonaJuridicas;
    private javax.swing.JTable tblPersonaNatural;
    private javax.swing.JTextField txtApeMa;
    private javax.swing.JTextField txtApePaRS;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDocIdent;
    private javax.swing.JFormattedTextField txtFecha;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
