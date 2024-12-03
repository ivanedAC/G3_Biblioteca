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
import java.text.SimpleDateFormat;
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

        setTitle("Mantenimiento Cliente");
        listarPaises();
        listartipoDocumento();
        listarClientesNaturales();
        btngpPersona.add(rbtnPJ);
        btngpPersona.add(rbtnPN);
        rbtnPN.setSelected(true);
//        formulariotamano();
    }

    public void clearfields() {
        txtApeMa.setText("");
        txtApePaRS.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtDocIdent.setText("");
        calendarFNac.setDate(null);
        txtNombre.setText("");
        txtTelefono.setText("");
        cbxPais.setSelectedIndex(0);
        cbxSexo.setSelectedIndex(0);
        cbxtTipoDoc.setSelectedIndex(0);
        chkVigencia.setSelected(true);
    }

    public boolean camposLlenos() {
        boolean vacio = false;
        if (txtApeMa.getText().isBlank() || txtApePaRS.getText().isBlank()) {
            System.out.println("vacio");
        } else {
            System.out.println("lleno");
        }
        return vacio;
    }

    public void personaJuridica() {
        lblFecha.setVisible(false);
        lblSexo.setVisible(false);
        lblapeMa.setVisible(false);
        lblApePaRS.setText("RAZON SOCIAL");
        txtApeMa.setVisible(false);
        calendarFNac.setVisible(false);
        cbxSexo.setVisible(false);
    }

    public void personaNatural() {
        lblTelefono.setVisible(true);
        lblFecha.setVisible(true);
        lblSexo.setVisible(true);
        lblapeMa.setVisible(true);
        lblApePaRS.setText("Apellido paterno");
        txtApeMa.setVisible(true);
        calendarFNac.setVisible(true);
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
            rsClientesN = objCliente.listarClientesNatural();
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
            rsClientesJ = objCliente.listarClientesJuridicos();

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

//    public void formulariotamano() {
//        Dimension fixedSize = new Dimension(220, 29);
//        txtApePaRS.setPreferredSize(fixedSize);
//        txtApePaRS.setMinimumSize(fixedSize);
//        txtApePaRS.setMaximumSize(fixedSize);
//        txtNombre.setPreferredSize(fixedSize);
//        txtNombre.setMinimumSize(fixedSize);
//        txtNombre.setMaximumSize(fixedSize);
//        txtDireccion.setPreferredSize(fixedSize);
//        txtDireccion.setMinimumSize(fixedSize);
//        txtDireccion.setMaximumSize(fixedSize);
//        txtTelefono.setPreferredSize(fixedSize);
//        txtTelefono.setMinimumSize(fixedSize);
//        txtTelefono.setMaximumSize(fixedSize);
//        txtCorreo.setPreferredSize(fixedSize);
//        txtCorreo.setMinimumSize(fixedSize);
//        txtCorreo.setMaximumSize(fixedSize);
//        cbxPais.setPreferredSize(fixedSize);
//        cbxPais.setMinimumSize(fixedSize);
//        cbxPais.setMaximumSize(fixedSize);
//        cbxtTipoDoc.setPreferredSize(fixedSize);
//        cbxtTipoDoc.setMinimumSize(fixedSize);
//        cbxtTipoDoc.setMaximumSize(fixedSize);
//    }

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

    public void listarSeguntipo() {
        if (cbxtTipoDoc.getSelectedIndex() == 1) {
            listarClientesJuridicos();
        } else {
            listarClientesNaturales();
        }
    }

    public boolean comprobarCamposLlenos() {
        boolean campos = false;
        if (cbxtTipoDoc.getSelectedIndex() == 1) {
            if (txtApePaRS.getText().isBlank() || txtCorreo.getText().isBlank() || txtDireccion.getText().isBlank() || txtDocIdent.getText().isBlank()
                    || txtNombre.getText().isBlank() || txtTelefono.getText().isBlank()) {
                campos = false;
            } else {
                campos = true;
            }
        } else {
            if (txtApePaRS.getText().isBlank() || txtCorreo.getText().isBlank() || txtDireccion.getText().isBlank() || txtDocIdent.getText().isBlank()
                    || txtNombre.getText().isBlank() || txtTelefono.getText().isBlank() || txtApeMa.getText().isBlank() || calendarFNac.getDate().equals("")) {
                campos = false;
            } else {
                campos = true;
            }
        }
        return campos;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngpPersona = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        panelFormulario = new javax.swing.JPanel();
        lblTipoDoc = new javax.swing.JLabel();
        lblDoc = new javax.swing.JLabel();
        txtDocIdent = new javax.swing.JTextField();
        lblSexo = new javax.swing.JLabel();
        cbxSexo = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        cbxtTipoDoc = new javax.swing.JComboBox<>();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblApePaRS = new javax.swing.JLabel();
        txtApePaRS = new javax.swing.JTextField();
        lblapeMa = new javax.swing.JLabel();
        txtApeMa = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        lblFecha = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        chkVigencia = new javax.swing.JCheckBox();
        lblDireccion = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lblPais = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        calendarFNac = new com.toedter.calendar.JDateChooser();
        lblTelefono = new javax.swing.JLabel();
        cbxPais = new javax.swing.JComboBox<>();
        txtTelefono = new javax.swing.JTextField();
        lblCorreo1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnDarBaja = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPersonaNatural = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        rbtnPN = new javax.swing.JRadioButton();
        rbtnPJ = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mantenimiento de cliente");
        setBackground(new java.awt.Color(255, 215, 171));
        setMaximumSize(new java.awt.Dimension(32767, 32767));

        jPanel1.setBackground(new java.awt.Color(243, 226, 210));
        jPanel1.setPreferredSize(new java.awt.Dimension(1332, 725));

        panelFormulario.setBackground(new java.awt.Color(243, 226, 210));

        lblTipoDoc.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblTipoDoc.setText("Tipo documento:");

        lblDoc.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblDoc.setText("Doc. identidad:");

        txtDocIdent.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtDocIdent.setBorder(null);

        lblSexo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblSexo.setText("Sexo:");

        cbxSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino", " " }));
        cbxSexo.setBorder(null);

        btnBuscar.setBackground(new java.awt.Color(113, 49, 18));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/buscar_18px.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        cbxtTipoDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxtTipoDoc.setBorder(null);
        cbxtTipoDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxtTipoDocActionPerformed(evt);
            }
        });

        lblNombre.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblNombre.setText("Nombre:");

        txtNombre.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtNombre.setBorder(null);

        lblApePaRS.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblApePaRS.setText("Apellido paterno:");

        txtApePaRS.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtApePaRS.setBorder(null);

        lblapeMa.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblapeMa.setText("Apellido materno:");

        txtApeMa.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtApeMa.setBorder(null);

        javax.swing.GroupLayout panelFormularioLayout = new javax.swing.GroupLayout(panelFormulario);
        panelFormulario.setLayout(panelFormularioLayout);
        panelFormularioLayout.setHorizontalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblapeMa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblApePaRS, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtApePaRS)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormularioLayout.createSequentialGroup()
                        .addComponent(txtDocIdent, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbxtTipoDoc, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtApeMa)
                    .addComponent(cbxSexo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelFormularioLayout.setVerticalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxtTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipoDoc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDocIdent, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApePaRS)
                    .addComponent(txtApePaRS, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblapeMa)
                    .addComponent(txtApeMa, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSexo))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(243, 226, 210));

        lblFecha.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblFecha.setText("Nacimiento:");

        lblCorreo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblCorreo.setText("Correo:");

        chkVigencia.setBackground(new java.awt.Color(243, 226, 210));
        chkVigencia.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        chkVigencia.setText("Vigencia");

        lblDireccion.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblDireccion.setText("Direccion:");

        txtCorreo.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtCorreo.setBorder(null);
        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });

        lblPais.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblPais.setText("Pais:");

        txtDireccion.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtDireccion.setBorder(null);

        calendarFNac.setBackground(new java.awt.Color(255, 255, 255));

        lblTelefono.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblTelefono.setText("Telefono:");

        cbxPais.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxPais.setBorder(null);

        txtTelefono.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtTelefono.setBorder(null);

        lblCorreo1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblCorreo1.setText("Estado:");

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblPais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbxPais, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(calendarFNac, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                            .addComponent(lblCorreo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCorreo)
                            .addComponent(txtDireccion)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(chkVigencia, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFecha)
                            .addComponent(calendarFNac, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPais)
                            .addComponent(cbxPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTelefono)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDireccion)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCorreo)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCorreo1)
                            .addComponent(chkVigencia))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(panelFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(243, 226, 210));

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

        btnDarBaja.setBackground(new java.awt.Color(113, 49, 18));
        btnDarBaja.setForeground(new java.awt.Color(245, 224, 206));
        btnDarBaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/darBaja_32px.png"))); // NOI18N
        btnDarBaja.setText("DAR DE BAJA");
        btnDarBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDarBajaActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(113, 49, 18));
        btnEliminar.setForeground(new java.awt.Color(245, 224, 206));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar_32px.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
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

        btnLimpiar.setBackground(new java.awt.Color(113, 49, 18));
        btnLimpiar.setForeground(new java.awt.Color(245, 224, 206));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/limpiar_32px.png"))); // NOI18N
        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(113, 49, 18));
        btnModificar.setForeground(new java.awt.Color(245, 224, 206));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/editar_32px.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.setPreferredSize(new java.awt.Dimension(115, 41));
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDarBaja, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDarBaja)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimpiar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(btnSalir)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(243, 226, 210));

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 993, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(113, 49, 18));
        jPanel4.setForeground(new java.awt.Color(113, 49, 18));

        rbtnPN.setBackground(new java.awt.Color(113, 49, 18));
        rbtnPN.setForeground(new java.awt.Color(255, 255, 255));
        rbtnPN.setText("Persona Natural");
        rbtnPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnPNActionPerformed(evt);
            }
        });

        rbtnPJ.setBackground(new java.awt.Color(113, 49, 18));
        rbtnPJ.setForeground(new java.awt.Color(255, 255, 255));
        rbtnPJ.setText("Persona Jurídica");
        rbtnPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnPJActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtnPN, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rbtnPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rbtnPN)
                .addComponent(rbtnPJ))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 837, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxtTipoDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxtTipoDocActionPerformed
        if (cbxtTipoDoc.getSelectedIndex() == 1) {
            personaJuridica();
        } else {
            personaNatural();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxtTipoDocActionPerformed

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
//                        formulariotamano();
                    } else {
                        cbxPais.setSelectedItem(objPais.buscarPaisPorCodigo(rs.getInt("cod_pais")));
                        txtNombre.setText(rs.getString("nombres"));
                        txtApePaRS.setText(rs.getString("ape_paterno"));
                        if (rs.getBoolean("sexo")) {
                            cbxSexo.setSelectedIndex(0);
                        } else {
                            cbxSexo.setSelectedIndex(1);
                        }
                        String fechita = rs.getString("f_nacimiento");
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date fechaNac = formato.parse(fechita);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(fechaNac);
                        calendarFNac.setCalendar(calendar);
                        txtApeMa.setText(rs.getString("ape_materno"));
                        txtDireccion.setText(rs.getString("direccion"));
                        txtTelefono.setText(rs.getString("telefono"));
                        txtCorreo.setText(rs.getString("correo"));
                        chkVigencia.setSelected(estado);

                    }
                } else {
                    rs = objCliente.obtenerPersonaPorDoc(txtDocIdent.getText());
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "Este usuario aun no esta registrado como cliente");

                        cbxtTipoDoc.setSelectedIndex(rs.getInt("cod_tipo_doc") - 1);

                        if (rs.getInt("cod_tipo_doc") == 2) {
                            cbxPais.setSelectedItem(objPais.buscarPaisPorCodigo(rs.getInt("cod_pais")));
                            txtNombre.setText(rs.getString("nombres"));
                            txtApePaRS.setText(rs.getString("razon_social"));
                            txtDireccion.setText(rs.getString("direccion"));
                            txtTelefono.setText(rs.getString("telefono"));
                            txtCorreo.setText(rs.getString("correo"));
                            chkVigencia.setSelected(true);
//                            formulariotamano();
                        } else {
                            cbxPais.setSelectedItem(objPais.buscarPaisPorCodigo(rs.getInt("cod_pais")));
                            txtNombre.setText(rs.getString("nombres"));
                            txtApePaRS.setText(rs.getString("ape_paterno"));
                            if (rs.getBoolean("sexo")) {
                                cbxSexo.setSelectedIndex(0);
                            } else {
                                cbxSexo.setSelectedIndex(1);
                            }
                            String fechita = rs.getString("f_nacimiento");
                            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                            java.util.Date fechaNac = formato.parse(fechita);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(fechaNac);
                            calendarFNac.setCalendar(calendar);
                            txtApeMa.setText(rs.getString("ape_materno"));
                            txtDireccion.setText(rs.getString("direccion"));
                            txtTelefono.setText(rs.getString("telefono"));
                            txtCorreo.setText(rs.getString("correo"));
                            chkVigencia.setSelected(true);
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "No existen registros de este documento");

                    }
                }

            } else {
                JOptionPane.showMessageDialog(this, "No hay un Numero de documento para buscar cliente");
            }

            // TODO add your handling code here:
        } catch (Exception ex) {
            Logger.getLogger(jdManCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblPersonaNaturalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPersonaNaturalMouseClicked
        clearfields();
        txtDocIdent.setText(String.valueOf(tblPersonaNatural.getValueAt(tblPersonaNatural.getSelectedRow(), 3)));
        btnBuscarActionPerformed(null);
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPersonaNaturalMouseClicked

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {
            if (!txtDocIdent.getText().isBlank()) {
                if (!(objCliente.verificarPrestamoCliente(objCliente.buscarCodigoCliente(txtDocIdent.getText())))) {
                    if (!(objCliente.verificarReservaCliente(objCliente.buscarCodigoCliente(txtDocIdent.getText())))) {

                        int opc = JOptionPane.showConfirmDialog(this, "¿Desea eliminar al cliente " + txtNombre.getText() + "?", "Confirmacion", JOptionPane.YES_NO_OPTION);
                        if (opc == JOptionPane.YES_OPTION) {
                            objCliente.eliminarCliente(txtDocIdent.getText());
                            JOptionPane.showMessageDialog(this, "Cliente eliminado");
                            listarClientesJuridicos();
                            listarClientesNaturales();
                            clearfields();
                        } else {
                            JOptionPane.showMessageDialog(this, "Se cancelo la eliminacion");
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Este cliente se encuentra en una reserva", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Este cliente se encuentra en un prestamo", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese el documento del cliente a eliminar");
            }

            // TODO add your handling code here:
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnDarBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarBajaActionPerformed
        try {
            if (txtDocIdent.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Ingrese el documento del cliente a dar Baja");
            } else {
                int opc = JOptionPane.showConfirmDialog(this, "Desea dar de baja al cliente " + txtNombre.getText() + "?", "Confirmacion", JOptionPane.YES_NO_OPTION);
                if (opc == JOptionPane.YES_OPTION) {
                    objCliente.darBajaCliente(txtDocIdent.getText());
                    JOptionPane.showMessageDialog(this, "Cliente dado de baja");
                    listarClientesJuridicos();
                    listarClientesNaturales();
                    clearfields();
                } else {
                    JOptionPane.showMessageDialog(this, "Se ha cancelado");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnDarBajaActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        clearfields();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        try {
            if (comprobarCamposLlenos()) {
                boolean sexo;
                int codPais = objPais.buscarCodigoPorNombre(cbxPais.getSelectedItem().toString());
                String fechaR = currentdate();
                String estado = "";
                ResultSet rs = null;
                rs = objCliente.obtenerPersonaPorDoc(txtDocIdent.getText());
                if (chkVigencia.isSelected()) {
                    estado = "A";
                } else {
                    estado = "I";
                }
                if (rs.next()) {
                    objCliente.insertarClienteExistente(txtDocIdent.getText(), estado);
                    JOptionPane.showMessageDialog(this, "Cliente registrado");

                    listarSeguntipo();
                } else {
                    if (cbxtTipoDoc.getSelectedIndex() == 1) {
                        objCliente.insertarClienteJuridico(codPais, cbxtTipoDoc.getSelectedIndex() + 1, txtDocIdent.getText(), txtNombre.getText(), txtApePaRS.getText(), txtDireccion.getText(), txtTelefono.getText(), fechaR, txtCorreo.getText(), "A");
                        JOptionPane.showMessageDialog(this, "Se ha insertado un nuevo cliente");
                        clearfields();
                        listarClientesJuridicos();
                    } else {
                        if (cbxSexo.getSelectedItem().toString().equals("Masculino")) {
                            sexo = true;
                        } else {
                            sexo = false;
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String fechita = sdf.format(calendarFNac.getCalendar().getTime());
                        objCliente.insertarClienteNatural(codPais, cbxtTipoDoc.getSelectedIndex() + 1, txtDocIdent.getText(), txtNombre.getText(), txtApePaRS.getText(), txtApeMa.getText(), sexo, fechita, txtDireccion.getText(), txtTelefono.getText(), fechaR, txtCorreo.getText(), estado);
                        JOptionPane.showMessageDialog(this, "Se ha insertado un nuevo cliente");
                        clearfields();
                        listarClientesNaturales();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Rellene todos lo campos");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "error-->" + ex.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void rbtnPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnPNActionPerformed
        // TODO add your handling code here:
        if(rbtnPN.isSelected()){
            listarClientesNaturales();
        }
    }//GEN-LAST:event_rbtnPNActionPerformed

    private void rbtnPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnPJActionPerformed
        // TODO add your handling code here:
        if(rbtnPJ.isSelected()){
            listarClientesJuridicos();
        }
    }//GEN-LAST:event_rbtnPJActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        try {
            if (comprobarCamposLlenos()) {
                boolean sexo;
                int codPais = objPais.buscarCodigoPorNombre(cbxPais.getSelectedItem().toString());
                String fechaR = currentdate();
                String estado = chkVigencia.isSelected() ? "A" : "I";
                ResultSet rs = objCliente.obtenerPersonaPorDoc(txtDocIdent.getText());

                if (rs.next()) { // Cliente ya existe
                    if (cbxtTipoDoc.getSelectedIndex() == 1) { // Cliente Jurídico
                        objCliente.modificarClienteJuridico(
                                codPais,
                                cbxtTipoDoc.getSelectedIndex() + 1,
                                txtDocIdent.getText(),
                                txtNombre.getText(),
                                txtApePaRS.getText(),
                                txtDireccion.getText(),
                                txtTelefono.getText(),
                                fechaR,
                                txtCorreo.getText(),
                                estado
                        );
                        JOptionPane.showMessageDialog(this, "Cliente jurídico modificado correctamente.");
                        listarClientesJuridicos();
                    } else { // Cliente Natural
                        sexo = cbxSexo.getSelectedItem().toString().equals("Masculino");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String fechita = sdf.format(calendarFNac.getCalendar().getTime());

                        objCliente.modificarClienteNatural(
                                codPais,
                                cbxtTipoDoc.getSelectedIndex() + 1,
                                txtDocIdent.getText(),
                                txtNombre.getText(),
                                txtApePaRS.getText(),
                                txtApeMa.getText(),
                                fechita,
                                txtDireccion.getText(),
                                txtTelefono.getText(),
                                fechaR,
                                txtCorreo.getText(),
                                estado,
                                sexo
                        );
                        JOptionPane.showMessageDialog(this, "Cliente natural modificado correctamente.");
                        listarClientesNaturales();
                    }
                } else { // Cliente no existe, insertar nuevo
                    if (cbxtTipoDoc.getSelectedIndex() == 1) { // Cliente Jurídico
                        objCliente.insertarClienteJuridico(
                                codPais,
                                cbxtTipoDoc.getSelectedIndex() + 1,
                                txtDocIdent.getText(),
                                txtNombre.getText(),
                                txtApePaRS.getText(),
                                txtDireccion.getText(),
                                txtTelefono.getText(),
                                fechaR,
                                txtCorreo.getText(),
                                "A"
                        );
                        JOptionPane.showMessageDialog(this, "Se ha insertado un nuevo cliente jurídico.");
                        clearfields();
                        listarClientesJuridicos();
                    } else { // Cliente Natural
                        sexo = cbxSexo.getSelectedItem().toString().equals("Masculino");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String fechita = sdf.format(calendarFNac.getCalendar().getTime());

                        objCliente.insertarClienteNatural(
                                codPais,
                                cbxtTipoDoc.getSelectedIndex() + 1,
                                txtDocIdent.getText(),
                                txtNombre.getText(),
                                txtApePaRS.getText(),
                                txtApeMa.getText(),
                                sexo,
                                fechita,
                                txtDireccion.getText(),
                                txtTelefono.getText(),
                                fechaR,
                                txtCorreo.getText(),
                                estado
                        );
                        JOptionPane.showMessageDialog(this, "Se ha insertado un nuevo cliente natural.");
                        clearfields();
                        listarClientesNaturales();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Rellene todos los campos.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnDarBaja;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup btngpPersona;
    private com.toedter.calendar.JDateChooser calendarFNac;
    private javax.swing.JComboBox<String> cbxPais;
    private javax.swing.JComboBox<String> cbxSexo;
    private javax.swing.JComboBox<String> cbxtTipoDoc;
    private javax.swing.JCheckBox chkVigencia;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblApePaRS;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblCorreo1;
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
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
