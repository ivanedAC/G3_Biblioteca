/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.clsPais;
import capaLogica.clsSede;
import capaLogica.clsTipoDocumento;
import capaLogica.clsTipoUsuario;
import capaLogica.clsUsuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author ACER
 */
public class jdManUsuario extends javax.swing.JDialog {

    clsUsuario objUsu = new clsUsuario();
    clsPais objPais = new clsPais();
    clsSede objSede = new clsSede();
    clsTipoUsuario objTUsuario = new clsTipoUsuario();
    clsTipoDocumento objTDoc = new clsTipoDocumento();

    /**
     * Creates new form jdMantUsuario
     */
    public jdManUsuario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void listarUsuarios() throws SQLException, Exception {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };;
        modelo.addColumn("Código");
        modelo.addColumn("País");
        modelo.addColumn("Doc");
        modelo.addColumn("N° Documento");
        modelo.addColumn("Nombre Completo");
        modelo.addColumn("Sexo");
        modelo.addColumn("Fecha Nac");
        modelo.addColumn("Dirección");
        modelo.addColumn("Celular");
        modelo.addColumn("Correo");
        modelo.addColumn("Cargo");
        modelo.addColumn("User");
        modelo.addColumn("Estado");
        modelo.addColumn("Sede");

        ResultSet rsUsu = objUsu.listarUsuarios();

        while (rsUsu.next()) {
            String sexo = "";
            if (rsUsu.getBoolean("sexo")) {
                sexo = "Masculino";
            } else {
                sexo = "Femenino";
            }
            Vector registro = new Vector();
            registro.add(0, rsUsu.getString("codigo"));
            registro.add(1, rsUsu.getString("pais"));
            registro.add(2, rsUsu.getString("tipo_doc"));
            registro.add(3, rsUsu.getString("numero_documento"));
            registro.add(4, rsUsu.getString("nombre_completo"));
            registro.add(5, sexo);
            registro.add(6, rsUsu.getString("f_nacimiento"));
            registro.add(7, rsUsu.getString("direccion"));
            registro.add(8, rsUsu.getString("telefono"));
            registro.add(9, rsUsu.getString("correo"));
            registro.add(10, rsUsu.getString("cargo"));
            registro.add(11, rsUsu.getString("usuario"));
            registro.add(12, rsUsu.getString("estado"));
            registro.add(13, rsUsu.getString("sede"));
            modelo.addRow(registro);
        }

        tblDatos.setModel(modelo);
        tblDatos.getColumnModel().getColumn(0).setPreferredWidth(28);
        tblDatos.getColumnModel().getColumn(1).setPreferredWidth(30);
        tblDatos.getColumnModel().getColumn(2).setPreferredWidth(10);
        tblDatos.getColumnModel().getColumn(5).setPreferredWidth(37);
        tblDatos.getColumnModel().getColumn(6).setPreferredWidth(40);
        tblDatos.getColumnModel().getColumn(8).setPreferredWidth(37);
        tblDatos.getColumnModel().getColumn(10).setPreferredWidth(21);
        tblDatos.getColumnModel().getColumn(11).setPreferredWidth(15);
        tblDatos.getColumnModel().getColumn(12).setPreferredWidth(10);
        tblDatos.getColumnModel().getColumn(13).setPreferredWidth(35);
        tblDatos.getTableHeader().setReorderingAllowed(false);
    }

    private void listarPaises() {
        ResultSet rsPais = null;
        DefaultComboBoxModel modeloPais = new DefaultComboBoxModel();
        cboxPais.setModel(modeloPais);
        try {
            rsPais = objPais.listarPais();
            while (rsPais.next()) {
                modeloPais.addElement(rsPais.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void listarSedes() {
        ResultSet rsSede = null;
        DefaultComboBoxModel modeloSede = new DefaultComboBoxModel();
        cboxSede.setModel(modeloSede);
        try {
            rsSede = objSede.listarSede();
            while (rsSede.next()) {
                modeloSede.addElement(rsSede.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void listarTipoDocumento() {
        ResultSet rsTDoc = null;
        DefaultComboBoxModel modeloSede = new DefaultComboBoxModel();
        cboxTipoDoc.setModel(modeloSede);
        try {
            rsTDoc = objTDoc.listarTipoDocumento();
            while (rsTDoc.next()) {
                modeloSede.addElement(rsTDoc.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void listarTipoUsuario() {
        ResultSet rsTuser = null;
        DefaultComboBoxModel modeloSede = new DefaultComboBoxModel();
        cboxTUser.setModel(modeloSede);
        try {
            rsTuser = objTUsuario.listarTipoUsuario();
            while (rsTuser.next()) {
                modeloSede.addElement(rsTuser.getString("nombre"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void limpiarControles() {
        txtCode.setText("");
        cboxPais.setSelectedItem(0);
        cboxTipoDoc.setSelectedItem(0);
        txtNroDoc.setText("");
        txtNom.setText("");
        txtApePat.setText("");
        txtApeMat.setText("");
        cboxSex.setSelectedItem(0);
        calendarFNac.setCalendar(null);
        txtDir.setText("");
        txtCel.setText("");
        txtCor.setText("");
        cboxTUser.setSelectedItem(0);
        txtUsu.setText("");
        txtPass.setText("");
        txtPass1.setText("");
        cboxSede.setSelectedItem(0);
    }

    private boolean esMayorDeEdad(Date fechaNacimiento) {
        Calendar fechaActual = Calendar.getInstance();

        Calendar fechaNacimientoCal = Calendar.getInstance();
        fechaNacimientoCal.setTime(fechaNacimiento);

        int añosDiferencia = fechaActual.get(Calendar.YEAR) - fechaNacimientoCal.get(Calendar.YEAR);

        if (fechaActual.get(Calendar.DAY_OF_YEAR) < fechaNacimientoCal.get(Calendar.DAY_OF_YEAR)) {
            añosDiferencia--;
        }

        // Retornar verdadero si la diferencia es mayor o igual a 18
        return añosDiferencia >= 18;
    }

    private boolean validarEmail(String email) {
        // Expresión regular para validar el formato de correo electrónico
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

        // Compilar la expresión regular
        Pattern pattern = Pattern.compile(emailRegex);

        // Comparar el correo con la expresión regular
        Matcher matcher = pattern.matcher(email);

        // Retornar true si coincide, false si no
        return matcher.matches();
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
        jLabel1 = new javax.swing.JLabel();
        cboxTipoDoc = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNroDoc = new javax.swing.JTextField();
        txtNom = new javax.swing.JTextField();
        txtApePat = new javax.swing.JTextField();
        txtApeMat = new javax.swing.JTextField();
        txtDir = new javax.swing.JTextField();
        calendarFNac = new com.toedter.calendar.JDateChooser();
        cboxSex = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        cboxPais = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtCel = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCor = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtUsu = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        cboxSede = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        cboxTUser = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        cboxEstado = new javax.swing.JComboBox<>();
        txtPass = new javax.swing.JPasswordField();
        jLabel18 = new javax.swing.JLabel();
        txtPass1 = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnDarBaja = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(230, 182, 139));

        jLabel1.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(113, 49, 18));
        jLabel1.setText("Tipo Documento:");

        cboxTipoDoc.setBackground(new java.awt.Color(245, 224, 206));
        cboxTipoDoc.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        cboxTipoDoc.setForeground(new java.awt.Color(113, 49, 18));
        cboxTipoDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboxTipoDoc.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cboxTipoDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxTipoDocActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(113, 49, 18));
        jLabel2.setText("Doc. Identidad:");

        jLabel3.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(113, 49, 18));
        jLabel3.setText("Nombres:");

        jLabel4.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(113, 49, 18));
        jLabel4.setText("Apellido Paterno:");

        jLabel5.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(113, 49, 18));
        jLabel5.setText("Apellido Materno:");

        jLabel6.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(113, 49, 18));
        jLabel6.setText("Sexo:");

        jLabel7.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(113, 49, 18));
        jLabel7.setText("Fecha Nacimiento:");

        jLabel8.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(113, 49, 18));
        jLabel8.setText("Teléfono:");

        txtNroDoc.setBackground(new java.awt.Color(245, 224, 206));
        txtNroDoc.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtNroDoc.setForeground(new java.awt.Color(113, 49, 18));
        txtNroDoc.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtNroDoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNroDocKeyTyped(evt);
            }
        });

        txtNom.setBackground(new java.awt.Color(245, 224, 206));
        txtNom.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtNom.setForeground(new java.awt.Color(113, 49, 18));
        txtNom.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtApePat.setBackground(new java.awt.Color(245, 224, 206));
        txtApePat.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtApePat.setForeground(new java.awt.Color(113, 49, 18));
        txtApePat.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtApeMat.setBackground(new java.awt.Color(245, 224, 206));
        txtApeMat.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtApeMat.setForeground(new java.awt.Color(113, 49, 18));
        txtApeMat.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtDir.setBackground(new java.awt.Color(245, 224, 206));
        txtDir.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtDir.setForeground(new java.awt.Color(113, 49, 18));
        txtDir.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        calendarFNac.setBackground(new java.awt.Color(245, 224, 206));
        calendarFNac.setForeground(new java.awt.Color(113, 49, 18));

        cboxSex.setBackground(new java.awt.Color(245, 224, 206));
        cboxSex.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        cboxSex.setForeground(new java.awt.Color(113, 49, 18));
        cboxSex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));
        cboxSex.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(113, 49, 18));
        jLabel9.setText("Código:");

        txtCode.setBackground(new java.awt.Color(245, 224, 206));
        txtCode.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtCode.setForeground(new java.awt.Color(113, 49, 18));
        txtCode.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodeKeyTyped(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(113, 49, 18));
        btnBuscar.setForeground(new java.awt.Color(245, 224, 206));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/buscar_24px.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(113, 49, 18));
        jLabel10.setText("País:");

        cboxPais.setBackground(new java.awt.Color(245, 224, 206));
        cboxPais.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        cboxPais.setForeground(new java.awt.Color(113, 49, 18));
        cboxPais.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboxPais.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel11.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(113, 49, 18));
        jLabel11.setText("Dirección:");

        txtCel.setBackground(new java.awt.Color(245, 224, 206));
        txtCel.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtCel.setForeground(new java.awt.Color(113, 49, 18));
        txtCel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(113, 49, 18));
        jLabel12.setText("Correo:");

        txtCor.setBackground(new java.awt.Color(245, 224, 206));
        txtCor.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtCor.setForeground(new java.awt.Color(113, 49, 18));
        txtCor.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCorKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(113, 49, 18));
        jLabel13.setText("Usuario:");

        jLabel14.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(113, 49, 18));
        jLabel14.setText("Contraseña:");

        txtUsu.setBackground(new java.awt.Color(245, 224, 206));
        txtUsu.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtUsu.setForeground(new java.awt.Color(113, 49, 18));
        txtUsu.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtUsu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsuKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(113, 49, 18));
        jLabel15.setText("Sede:");

        cboxSede.setBackground(new java.awt.Color(245, 224, 206));
        cboxSede.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        cboxSede.setForeground(new java.awt.Color(113, 49, 18));
        cboxSede.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboxSede.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jLabel16.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(113, 49, 18));
        jLabel16.setText("Tipo Usuario:");

        cboxTUser.setBackground(new java.awt.Color(245, 224, 206));
        cboxTUser.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        cboxTUser.setForeground(new java.awt.Color(113, 49, 18));
        cboxTUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboxTUser.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel17.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(113, 49, 18));
        jLabel17.setText("Estado:");

        cboxEstado.setBackground(new java.awt.Color(245, 224, 206));
        cboxEstado.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        cboxEstado.setForeground(new java.awt.Color(113, 49, 18));
        cboxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        cboxEstado.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtPass.setBackground(new java.awt.Color(245, 224, 206));
        txtPass.setForeground(new java.awt.Color(113, 49, 18));
        txtPass.setText("jPasswordField1");

        jLabel18.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(113, 49, 18));
        jLabel18.setText("Confirmar Contraseña:");

        txtPass1.setBackground(new java.awt.Color(245, 224, 206));
        txtPass1.setForeground(new java.awt.Color(113, 49, 18));
        txtPass1.setText("jPasswordField1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(238, 238, 238))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboxSex, 0, 703, Short.MAX_VALUE)
                            .addComponent(txtApeMat)
                            .addComponent(txtApePat)
                            .addComponent(txtNom)
                            .addComponent(cboxTipoDoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboxPais, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNroDoc, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(80, 80, 80))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel8)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel7)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboxSede, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboxTUser, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCor, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtDir, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCel)
                    .addComponent(calendarFNac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtPass, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(txtUsu))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPass1)
                            .addComponent(cboxEstado, 0, 182, Short.MAX_VALUE))))
                .addGap(20, 20, 20))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboxPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboxTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNroDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtApePat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtApeMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5))
                                        .addGap(13, 13, 13))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel14)
                                        .addGap(18, 18, 18)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboxSex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel6)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel9))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(calendarFNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(19, 19, 19)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11)))
                                    .addComponent(jLabel7))
                                .addGap(12, 12, 12)
                                .addComponent(txtCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboxTUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(cboxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(txtPass1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(cboxSede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBackground(new java.awt.Color(230, 182, 139));

        btnNuevo.setBackground(new java.awt.Color(113, 49, 18));
        btnNuevo.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(245, 224, 206));
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/nuevo_32px.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(113, 49, 18));
        btnModificar.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(245, 224, 206));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/editar_32px.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(113, 49, 18));
        btnEliminar.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(245, 224, 206));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/eliminar_32px.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnDarBaja.setBackground(new java.awt.Color(113, 49, 18));
        btnDarBaja.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        btnDarBaja.setForeground(new java.awt.Color(245, 224, 206));
        btnDarBaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/darBaja_32px.png"))); // NOI18N
        btnDarBaja.setText("Dar de Baja");
        btnDarBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDarBajaActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(113, 49, 18));
        btnLimpiar.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(245, 224, 206));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/limpiar_32px.png"))); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(113, 49, 18));
        btnSalir.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(245, 224, 206));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/salir_32px.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDarBaja, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnNuevo)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnDarBaja)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalir)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator2))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(230, 182, 139));

        tblDatos.setBackground(new java.awt.Color(245, 224, 206));
        tblDatos.setForeground(new java.awt.Color(113, 49, 18));
        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDatos);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            // TODO add your handling code here:
            listarPaises();
            listarSedes();
            listarTipoUsuario();
            listarTipoDocumento();
            listarUsuarios();
        } catch (Exception ex) {
            Logger.getLogger(jdManUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        try {
            ResultSet rs = objUsu.listarUsuarios();
            boolean acceso = true;
            if (btnNuevo.getText().equalsIgnoreCase("Nuevo")) {
                btnNuevo.setText("Guardar");
                limpiarControles();
                txtCode.setText(String.valueOf(objUsu.generarCodigoUsuario()));
                txtNroDoc.requestFocus();
            } else {
                if (!txtCode.getText().isBlank() && !txtNroDoc.getText().isBlank() && !txtNom.getText().isBlank()
                        && !txtApePat.getText().isBlank() && !txtApeMat.getText().isBlank() && !txtDir.getText().isBlank()
                        && !txtCel.getText().isBlank() && !txtCor.getText().isBlank() && !txtUsu.getText().isBlank()
                        && !txtPass.getText().isBlank() && !txtPass.getText().isBlank() && calendarFNac.getCalendar() != null) {
                    while (rs.next()) {
                        if (txtNroDoc.getText().equals(rs.getString("numero_documento"))) {
                            JOptionPane.showMessageDialog(null, "El número de documento ingresado ya se encuentra registrado, por favor cámbielo", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                            acceso = false;
                            break;
                        }
                        if (txtCode.getText().equals(rs.getString("codigo"))) {
                            JOptionPane.showMessageDialog(null, "El código ingresado ya se encuentra registrado, por favor cámbielo", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                            acceso = false;
                            break;
                        }
                        if (txtUsu.getText().equals(rs.getString("usuario"))) {
                            JOptionPane.showMessageDialog(null, "El nombre de usuario ingresado ya se encuentra registrado, por favor cámbielo", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                            acceso = false;
                            break;
                        }
                        if (!esMayorDeEdad(calendarFNac.getDate())) {
                            JOptionPane.showMessageDialog(null, "El usuario ingresado debe ser mayor de edad", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                            acceso = false;
                            break;
                        }
                        if (txtCel.getText().length() != 9) {
                            JOptionPane.showMessageDialog(null, "El número de celular debe tener exactamente 9 dígitos", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                            acceso = false;
                            break;
                        }
                        if (cboxTipoDoc.getSelectedItem().toString().equals("DNI") && txtNroDoc.getText().length() != 8) {
                            JOptionPane.showMessageDialog(null, "El DNI debe tener exactamente 8 dígitos", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                            acceso = false;
                            break;
                        }
                        if (cboxTipoDoc.getSelectedItem().toString().equals("RUC") && txtNroDoc.getText().length() != 11) {
                            JOptionPane.showMessageDialog(null, "El número de RUC debe tener exactamente 11 dígitos", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                            acceso = false;
                            break;
                        }
                        if (cboxTipoDoc.getSelectedItem().toString().equals("Carnet de Extranjería") && txtNroDoc.getText().length() > 12) {
                            JOptionPane.showMessageDialog(null, "El Carnet de Extranjería debe tener como máximo 12 dígitos", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                            acceso = false;
                            break;
                        }
                        if (!validarEmail(txtCor.getText())) {
                            JOptionPane.showMessageDialog(null, "El formato de correo electrónico ingresado no es válido", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                            acceso = false;
                            break;
                        }
                    }
                    if (objUsu.buscarCodigoPersona(txtNroDoc.getText()) == 0) {
                        if (acceso) {
                            if (txtPass.getText().equals(txtPass1.getText())) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                String fechita = sdf.format(calendarFNac.getCalendar().getTime());
                                objUsu.registrarUsuario(Integer.valueOf(txtCode.getText()), objPais.buscarCodigoPorNombre(cboxPais.getSelectedItem().toString()),
                                        objTDoc.obtenerTipoDocumento(cboxTipoDoc.getSelectedItem().toString()),
                                        txtNroDoc.getText(), txtNom.getText(), txtApePat.getText(), txtApeMat.getText(),
                                        (cboxSex.getSelectedItem().toString().equalsIgnoreCase("Masculino")),
                                        fechita, txtDir.getText(), txtCel.getText(), "", txtCor.getText(),
                                        objTUsuario.obtenerTipoUsuario(cboxTUser.getSelectedItem().toString()),
                                        txtUsu.getText(), txtPass.getText(), String.valueOf(cboxEstado.getSelectedItem().toString().charAt(0)),
                                        objSede.obtenerSede(cboxSede.getSelectedItem().toString()));
                                JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
                                btnNuevo.setText("Nuevo");
                                listarUsuarios();
                                limpiarControles();
                            } else {
                                JOptionPane.showMessageDialog(null, "Las contraseñas deben coincidir", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    } else if (acceso) {
                        int i = JOptionPane.showConfirmDialog(null, "La persona ingresada ya existe, ¿Desea registrarlo como usuario?", "Mensaje de Sistema", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        if (txtPass.getText().equals(txtPass1.getText())) {
                            if (i == 0) {
                                int j = JOptionPane.showConfirmDialog(null, "El usuario será registrado, ¿Desea actualizar sus datos por los ingresados (YES) o conservar los ya existentes (NO)?", "Mensaje de Sistema", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                boolean opc = (j == 0);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                String fechita = sdf.format(calendarFNac.getCalendar().getTime());
                                objUsu.registrarUsuarioExistente(Integer.valueOf(txtCode.getText()), objPais.buscarCodigoPorNombre(cboxPais.getSelectedItem().toString()),
                                        objTDoc.obtenerTipoDocumento(cboxTipoDoc.getSelectedItem().toString()),
                                        txtNroDoc.getText(), txtNom.getText(), txtApePat.getText(), txtApeMat.getText(),
                                        (cboxSex.getSelectedItem().toString().equalsIgnoreCase("Masculino")),
                                        fechita, txtDir.getText(), txtCel.getText(), "", txtCor.getText(),
                                        objTUsuario.obtenerTipoUsuario(cboxTUser.getSelectedItem().toString()),
                                        txtUsu.getText(), txtPass.getText(), String.valueOf(cboxEstado.getSelectedItem().toString().charAt(0)),
                                        objSede.obtenerSede(cboxSede.getSelectedItem().toString()), opc);
                                JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
                                btnNuevo.setText("Nuevo");
                                listarUsuarios();
                                limpiarControles();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Las contraseñas deben coincidir", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiarControles();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        try {
            ResultSet rsUsu = null;
            if (!txtCode.getText().isBlank()) {
                rsUsu = objUsu.buscarUsuario(Integer.valueOf(txtCode.getText()));
                if (rsUsu.next()) {
                    String[] partes = rsUsu.getString("nombre_completo").split(",");
                    String[] apellidos = partes[0].split(" ");
                    String apePat = apellidos[0], apeMat = apellidos[1], nombre = partes.length > 1 ? partes[1].trim() : "";
                    boolean sex = rsUsu.getBoolean("sexo");

                    String fechita = rsUsu.getString("f_nacimiento");
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date fechaNac = formato.parse(fechita);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fechaNac);

                    String estado;
                    if (rsUsu.getString("estado").equalsIgnoreCase("A")) {
                        estado = "Activo";
                    } else {
                        estado = "Inactivo";
                    }

                    txtCode.setText(rsUsu.getString("codigo"));
                    cboxPais.setSelectedItem(rsUsu.getString("pais"));
                    cboxTipoDoc.setSelectedItem(rsUsu.getString("tipo_doc"));
                    txtNroDoc.setText(rsUsu.getString("numero_documento"));
                    txtApePat.setText(apePat);
                    txtApeMat.setText(apeMat);
                    txtNom.setText(nombre);
                    if (sex) {
                        cboxSex.setSelectedItem("Masculino");
                    } else {
                        cboxSex.setSelectedItem("Femenino");
                    }
                    calendarFNac.setCalendar(calendar);
                    txtDir.setText(rsUsu.getString("direccion"));
                    txtCel.setText(rsUsu.getString("telefono"));
                    txtCor.setText(rsUsu.getString("correo"));
                    cboxTUser.setSelectedItem(rsUsu.getString("cargo"));
                    txtUsu.setText(rsUsu.getString("usuario"));
                    cboxEstado.setSelectedItem(estado);
                    cboxSede.setSelectedItem(rsUsu.getString("sede"));
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Mensaje de Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatosMouseClicked
        // TODO add your handling code here:
        txtCode.setText(String.valueOf(tblDatos.getValueAt(tblDatos.getSelectedRow(), 0)));
        btnBuscarActionPerformed(null);
    }//GEN-LAST:event_tblDatosMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        try {
            if (txtCode.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un código a eliminar!");
            } else {
                ResultSet rs = objUsu.buscarUsuario(Integer.valueOf(txtCode.getText()));
                if (rs.next()) {
                    int i = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar a este Usuario?",
                            "Mensaje", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (i == 0) {
                        objUsu.eliminarUsuario(Integer.valueOf(txtCode.getText()));
                        JOptionPane.showMessageDialog(null, "Usuario eliminado");
                        limpiarControles();
                        listarUsuarios();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El código ingresado no existe", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar usuario: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        try {
            ResultSet rsUsu = objUsu.listarUsuarios();
            boolean acceso = true;
            if (txtCode.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un código a modificar!");
            } else {
                if (!txtNroDoc.getText().isBlank() && !txtNom.getText().isBlank()
                        && !txtApePat.getText().isBlank() && !txtApeMat.getText().isBlank() && !txtDir.getText().isBlank()
                        && !txtCel.getText().isBlank() && !txtCor.getText().isBlank() && !txtUsu.getText().isBlank()
                        && calendarFNac.getCalendar() != null) {
                    ResultSet rs = objUsu.buscarUsuario(Integer.valueOf(txtCode.getText()));
                    if (rs.next()) {
                        while (rsUsu.next()) {
                            if (rsUsu.getInt("codigo") == rs.getInt("codigo")) {
                                continue;
                            }
                            if (txtNroDoc.getText().equals(rsUsu.getString("numero_documento"))) {
                                JOptionPane.showMessageDialog(null, "El número de documento ingresado ya se encuentra registrado, por favor cámbielo", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                                acceso = false;
                                break;
                            }
                            if (txtUsu.getText().equals(rsUsu.getString("usuario"))) {
                                JOptionPane.showMessageDialog(null, "El nombre de usuario ingresado ya se encuentra registrado, por favor cámbielo", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                                acceso = false;
                                break;
                            }
                            if (!esMayorDeEdad(calendarFNac.getDate())) {
                                JOptionPane.showMessageDialog(null, "El usuario ingresado debe ser mayor de edad", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                                acceso = false;
                                break;
                            }
                            if (txtCel.getText().length() != 9) {
                                JOptionPane.showMessageDialog(null, "El número de celular debe tener exactamente 9 dígitos", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                                acceso = false;
                                break;
                            }
                            if (cboxTipoDoc.getSelectedItem().toString().equals("DNI") && txtNroDoc.getText().length() != 8) {
                                JOptionPane.showMessageDialog(null, "El DNI debe tener exactamente 8 dígitos", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                                acceso = false;
                                break;
                            }
                            if (cboxTipoDoc.getSelectedItem().toString().equals("RUC") && txtNroDoc.getText().length() != 11) {
                                JOptionPane.showMessageDialog(null, "El número de RUC debe tener exactamente 11 dígitos", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                                acceso = false;
                                break;
                            }
                            if (cboxTipoDoc.getSelectedItem().toString().equals("Carnet de Extranjería") && txtNroDoc.getText().length() > 12) {
                                JOptionPane.showMessageDialog(null, "El Carnet de Extranjería debe tener como máximo 12 dígitos", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                                acceso = false;
                                break;
                            }
                            if (!validarEmail(txtCor.getText())) {
                                JOptionPane.showMessageDialog(null, "El formato de correo electrónico ingresado no es válido", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                                acceso = false;
                                break;
                            }
                        }
                        if (acceso) {
                            int i = JOptionPane.showConfirmDialog(null, "¿Está seguro modificar a este Usuario?",
                                    "Mensaje", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (i == 0) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                String fechita = sdf.format(calendarFNac.getCalendar().getTime());
                                objUsu.modificarUsuario(Integer.valueOf(txtCode.getText()), objPais.buscarCodigoPorNombre(cboxPais.getSelectedItem().toString()),
                                        objTDoc.obtenerTipoDocumento(cboxTipoDoc.getSelectedItem().toString()),
                                        txtNroDoc.getText(), txtNom.getText(), txtApePat.getText(), txtApeMat.getText(),
                                        (cboxSex.getSelectedItem().toString().equalsIgnoreCase("Masculino")),
                                        fechita, txtDir.getText(), txtCel.getText(), "", txtCor.getText(),
                                        objTUsuario.obtenerTipoUsuario(cboxTUser.getSelectedItem().toString()),
                                        txtUsu.getText(), String.valueOf(cboxEstado.getSelectedItem().toString().charAt(0)),
                                        objSede.obtenerSede(cboxSede.getSelectedItem().toString()));
                                JOptionPane.showMessageDialog(null, "Usuario modificado correctamente");
                                limpiarControles();
                                listarUsuarios();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El código ingresado no existe", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar usuario: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnDarBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarBajaActionPerformed
        // TODO add your handling code here:
        try {
            if (txtCode.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un código a dar de baja!");
            } else {
                ResultSet rs = objUsu.buscarUsuario(Integer.valueOf(txtCode.getText()));
                if (rs.next()) {
                    int i = JOptionPane.showConfirmDialog(null, "¿Está seguro de dar de baja a este Usuario?",
                            "Mensaje", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (i == 0) {
                        objUsu.darBajaUsuario(Integer.valueOf(txtCode.getText()));
                        JOptionPane.showMessageDialog(null, "Usuario dado de baja");
                        limpiarControles();
                        listarUsuarios();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El código ingresado no existe", "Mensaje de sistema", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al dar de baja a usuario: " + e.getMessage());
        }
    }//GEN-LAST:event_btnDarBajaActionPerformed

    private void txtCelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelKeyTyped
        // TODO add your handling code here:
        Character objTecla = evt.getKeyChar();
        if (!Character.isDigit(objTecla)) {
            evt.consume();
        }
        if (txtCel.getText().length() > 8) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCelKeyTyped

    private void txtCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodeKeyTyped
        // TODO add your handling code here:
        Character objTecla = evt.getKeyChar();
        if (!Character.isDigit(objTecla)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodeKeyTyped

    private void txtNroDocKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNroDocKeyTyped
        // TODO add your handling code here:
        Character objTecla = evt.getKeyChar();
        if (!Character.isDigit(objTecla)) {
            evt.consume();
        }
        if (cboxTipoDoc.getSelectedItem().toString().equals("DNI") && txtNroDoc.getText().length() > 7) {
            evt.consume();
        }

        if (cboxTipoDoc.getSelectedItem().toString().equals("RUC") && txtNroDoc.getText().length() > 10) {
            evt.consume();
        }

        if (cboxTipoDoc.getSelectedItem().toString().equals("Carnet de Extranjería") && txtNroDoc.getText().length() > 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNroDocKeyTyped

    private void txtCorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorKeyTyped
        // TODO add your handling code here:
        Character objTecla = evt.getKeyChar();
        if ((int) objTecla == 32) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCorKeyTyped

    private void cboxTipoDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxTipoDocActionPerformed
        // TODO add your handling code here:
        txtNroDoc.setText("");
    }//GEN-LAST:event_cboxTipoDocActionPerformed

    private void txtUsuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuKeyTyped
        // TODO add your handling code here:
        Character objTecla = evt.getKeyChar();
        if ((int) objTecla == 32) {
            evt.consume();
        }
    }//GEN-LAST:event_txtUsuKeyTyped

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnDarBaja;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private com.toedter.calendar.JDateChooser calendarFNac;
    private javax.swing.JComboBox<String> cboxEstado;
    private javax.swing.JComboBox<String> cboxPais;
    private javax.swing.JComboBox<String> cboxSede;
    private javax.swing.JComboBox<String> cboxSex;
    private javax.swing.JComboBox<String> cboxTUser;
    private javax.swing.JComboBox<String> cboxTipoDoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtApeMat;
    private javax.swing.JTextField txtApePat;
    private javax.swing.JTextField txtCel;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtCor;
    private javax.swing.JTextField txtDir;
    private javax.swing.JTextField txtNom;
    private javax.swing.JTextField txtNroDoc;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JPasswordField txtPass1;
    private javax.swing.JTextField txtUsu;
    // End of variables declaration//GEN-END:variables
}
