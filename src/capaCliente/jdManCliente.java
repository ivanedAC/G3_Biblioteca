/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.clsCliente;
import capaLogica.clsPais;
import capaLogica.clsTipoDocumento;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

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

        rbtnPN.setSelected(true);
        formulariotamano();
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
        chkVigencia.setSelected(true);
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
        modelo.addColumn("Estado");

        String nombrepais = "";
        String nombreTipoDoc = "";
        try {
            rsClientesN = objCliente.listarClientesN();
            while (rsClientesN.next()) {
                Object datos[][] = new Object[1][12];
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
                datos[0][11] = rsClientesN.getString("estado");

                modelo.addRow(datos[0]);
            }
            tblPersonaNatural.setModel(modelo);
            adjustColumnSizes(tblPersonaNatural);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error al listar en tabla" + e.getMessage());
        }
    }

    public void listarClientesJuridicos() {
        ResultSet rsClientesJ = null;
        DefaultTableModel modelo = new DefaultTableModel();

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

        String nombrepais;
        String nombreTipoDoc;

        try {
            rsClientesJ = objCliente.listarClientesJ();

            // Recorrer el ResultSet y llenar el modelo
            while (rsClientesJ.next()) {
                // Crear un array para la fila y llenarlo con datos
                Object[] datos = new Object[10];
                datos[0] = rsClientesJ.getString("codigo");
                nombrepais = objPais.buscarPaisPorCodigo(rsClientesJ.getInt("cod_pais"));
                datos[1] = nombrepais;
                nombreTipoDoc = objDocumento.nombreTipoDocumento(rsClientesJ.getInt("cod_tipo_doc"));
                datos[2] = nombreTipoDoc;
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        rbtnPN = new javax.swing.JRadioButton();
        rbtnPJ = new javax.swing.JRadioButton();
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
        chkVigencia = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 215, 171));
        setMaximumSize(new java.awt.Dimension(32767, 32767));
        setPreferredSize(new java.awt.Dimension(1332, 725));

        jPanel1.setBackground(new java.awt.Color(230, 182, 139));
        jPanel1.setPreferredSize(new java.awt.Dimension(1332, 725));

        jPanel3.setBackground(new java.awt.Color(230, 182, 139));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        buttonGroup1.add(rbtnPN);
        rbtnPN.setText("Persona Natural");
        rbtnPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnPNActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnPJ);
        rbtnPJ.setText("Persona Juridica");
        rbtnPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnPJActionPerformed(evt);
            }
        });

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
        tblPersonaNatural.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
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
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(rbtnPN)
                        .addGap(37, 37, 37)
                        .addComponent(rbtnPJ)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnPN)
                    .addComponent(rbtnPJ))
                .addGap(43, 43, 43)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelFormulario.setBackground(new java.awt.Color(230, 182, 139));
        panelFormulario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTipoDoc.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        lblTipoDoc.setText("TIPO DOCUMENTO");

        cbxPais.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblDoc.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        lblDoc.setText("DOC. IDENTIDAD");

        txtDocIdent.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N

        lblNombre.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        lblNombre.setText("NOMBRE");

        txtNombre.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N

        lblApePaRS.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        lblApePaRS.setText("APELLIDO PATERNO");

        txtApePaRS.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N

        lblapeMa.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        lblapeMa.setText("APELLIDO MATERNO");

        txtApeMa.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N

        lblSexo.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        lblSexo.setText("SEXO");

        cbxSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino", " " }));

        lblFecha.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        lblFecha.setText("FECHA NACIMIENTO");

        try {
            txtFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });

        lblTelefono.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        lblTelefono.setText("TELEFONO");

        txtTelefono.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/buscar_24px.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        lblPais.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        lblPais.setText("PAIS");

        cbxtTipoDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxtTipoDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxtTipoDocActionPerformed(evt);
            }
        });

        lblDireccion.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        lblDireccion.setText("DIRECCION");

        txtDireccion.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N

        lblCorreo.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        lblCorreo.setText("CORREO");

        txtCorreo.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N

        chkVigencia.setBackground(new java.awt.Color(230, 182, 139));
        chkVigencia.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        chkVigencia.setText("Vigencia");

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
                    .addComponent(chkVigencia)
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtFecha)
                            .addComponent(cbxtTipoDoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxPais, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtApePaRS)
                            .addComponent(cbxSexo, 0, 220, Short.MAX_VALUE)
                            .addComponent(txtTelefono)
                            .addComponent(txtDireccion)
                            .addComponent(txtCorreo)
                            .addComponent(txtDocIdent)
                            .addComponent(txtNombre)
                            .addComponent(txtApeMa))
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
                .addComponent(chkVigencia)
                .addContainerGap(145, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(230, 182, 139));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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

        jButton5.setBackground(new java.awt.Color(113, 49, 18));
        jButton5.setForeground(new java.awt.Color(245, 224, 206));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/editar_32px.png"))); // NOI18N
        jButton5.setText("MODIFICAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
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

        jButton6.setBackground(new java.awt.Color(113, 49, 18));
        jButton6.setForeground(new java.awt.Color(245, 224, 206));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/darBaja_32px.png"))); // NOI18N
        jButton6.setText("DAR DE BAJA");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
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

        btnSalir.setBackground(new java.awt.Color(113, 49, 18));
        btnSalir.setForeground(new java.awt.Color(245, 224, 206));
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
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap(41, Short.MAX_VALUE)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(83, 83, 83)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        try {

            boolean sexo;
            int codPais = objPais.buscarCodigoPorNombre(cbxPais.getSelectedItem().toString());
            String fechaR = currentdate();
            String estado = "";

            if (cbxtTipoDoc.getSelectedIndex() == 1) {
                objCliente.insertarClienteJuridico(codPais, cbxtTipoDoc.getSelectedIndex() + 1, txtDocIdent.getText(), txtNombre.getText(), txtApePaRS.getText(), txtDireccion.getText(), txtTelefono.getText(), fechaR, txtCorreo.getText(), "A");
                JOptionPane.showMessageDialog(this, "Se ha insertado un nuevo cliente");
                clearfields();
            } else {
                if (cbxSexo.getSelectedItem().toString().equals("Masculino")) {
                    sexo = true;
                } else {
                    sexo = false;
                }
                if (chkVigencia.isSelected()) {
                    estado = "A";
                } else {
                    estado = "I";
                }
                objCliente.insertarClienteNatural(codPais, cbxtTipoDoc.getSelectedIndex() + 1, txtDocIdent.getText(), txtNombre.getText(), txtApePaRS.getText(), txtApeMa.getText(), sexo, txtFecha.getText(), txtDireccion.getText(), txtTelefono.getText(), fechaR, txtCorreo.getText(), estado);
                JOptionPane.showMessageDialog(this, "Se ha insertado un nuevo cliente");
                clearfields();
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
            if (!txtDocIdent.getText().equals("")) {
                ResultSet rs = objCliente.obtenerClientePorDoc(txtDocIdent.getText());
                boolean estado = false;

                if (rs.next()) {
                    cbxtTipoDoc.setSelectedIndex(rs.getInt("cod_tipo_doc") - 1);
                    if (rs.getString("estado").equals("A")) {
                        estado = true;
                    } else if (rs.getString("estado").equals("I")) {
                        estado = false;
                    }
                    if (rs.getInt("cod_tipo_doc") == 2) {
                        cbxPais.setSelectedItem(objPais.buscarPaisPorCodigo(rs.getInt("cod_pais")));
                        txtNombre.setText(rs.getString("nombres"));
                        txtApePaRS.setText(rs.getString("razon_social"));
                        txtDireccion.setText(rs.getString("direccion"));
                        txtTelefono.setText(rs.getString("telefono"));
                        txtCorreo.setText(rs.getString("correo"));
                        chkVigencia.setSelected(estado);
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
                        chkVigencia.setSelected(estado);

                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No existe este cliente en nuestros registros");
                }

            } else {
                JOptionPane.showMessageDialog(this, "No hay un Numero de documento para buscar cliente");
            }

            // TODO add your handling code here:
        } catch (Exception ex) {
            Logger.getLogger(jdManCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String estado = "";
        if (chkVigencia.isSelected()) {
            estado = "A";
        } else {
            estado = "I";
        }

        if (cbxtTipoDoc.getSelectedIndex() == 1) {
            try {
                objCliente.modificarClienteJuridico(objPais.buscarCodigoPorNombre(cbxPais.getSelectedItem().toString()), (int) cbxtTipoDoc.getSelectedIndex() + 1, txtDocIdent.getText(), txtNombre.getText(), txtApePaRS.getText(), txtDireccion.getText(), txtTelefono.getText(), currentdate().toString(), txtCorreo.getText(), estado);
                JOptionPane.showMessageDialog(this, "Se ha modificado el cliente");
                listarClientesJuridicos();
                clearfields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        } else {
            try {
                objCliente.modificarClienteNatural(objPais.buscarCodigoPorNombre(cbxPais.getSelectedItem().toString()), (int) cbxtTipoDoc.getSelectedIndex() + 1, txtDocIdent.getText(), txtNombre.getText(), txtApePaRS.getText(), txtApeMa.getText(), txtFecha.getText(), txtDireccion.getText(), txtTelefono.getText(), currentdate().toString(), txtCorreo.getText(), estado);
                JOptionPane.showMessageDialog(this, "Se ha modificado el cliente");
                listarClientesNaturales();
                clearfields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            objCliente.darBajaCliente(txtDocIdent.getText());
            JOptionPane.showMessageDialog(this, "Cliente dado de baja");
            listarClientesJuridicos();
            listarClientesNaturales();
            clearfields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            objCliente.eliminarCliente(txtDocIdent.getText());
            JOptionPane.showMessageDialog(this, "Cliente eliminado");
            listarClientesJuridicos();
            listarClientesNaturales();
            clearfields();
            // TODO add your handling code here:
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void rbtnPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnPNActionPerformed
        if (rbtnPN.isSelected()) {
            listarClientesNaturales();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnPNActionPerformed

    private void rbtnPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnPJActionPerformed
        if (rbtnPJ.isSelected()) {
            listarClientesJuridicos();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnPJActionPerformed

    private void tblPersonaNaturalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPersonaNaturalMouseClicked
        clearfields();
        txtDocIdent.setText(String.valueOf(tblPersonaNatural.getValueAt(tblPersonaNatural.getSelectedRow(), 3)));
        btnBuscarActionPerformed(null);
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPersonaNaturalMouseClicked

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxPais;
    private javax.swing.JComboBox<String> cbxSexo;
    private javax.swing.JComboBox<String> cbxtTipoDoc;
    private javax.swing.JCheckBox chkVigencia;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JRadioButton rbtnPJ;
    private javax.swing.JRadioButton rbtnPN;
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
