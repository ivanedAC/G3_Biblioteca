/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.*;
import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.text.JTextComponent;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class jdTranPrestamo extends javax.swing.JDialog {

    clsPrestamo objPrestamo = new clsPrestamo();
    clsCliente objCliente = new clsCliente();
    clsEjemplar objEjem = new clsEjemplar();
    clsTipoDocumento objTDoc = new clsTipoDocumento();

    /**
     * Creates new form jdTranPrestamo
     */
    public jdTranPrestamo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void llenarTablaInicial() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("ISBN");
        modelo.addColumn("Editorial");
        modelo.addColumn("Sede");
        modelo.addColumn("Estado");

        tblDetalles.setModel(modelo);
        tblDetalles.getTableHeader().setReorderingAllowed(false);
    }

    private void mostrarDatosCliente(Integer cod) throws Exception {
        ResultSet rsCli = objCliente.buscarClientePorCodigo(cod);
        if (rsCli.next()) {
            if (rsCli.getString("estado").equals("A")) {
                String nom = rsCli.getString("nombres");
                String apeP = rsCli.getString("ape_paterno");
                String apeM = rsCli.getString("ape_materno");

                lblCodCli.setText(rsCli.getString("cod_cliente"));
                if (rsCli.getString("razon_social") == null) {
                    lblTipoPerCli.setText("P. Natural");
                    lblNomCli.setText(nom + " " + apeP + " " + apeM);
                } else {
                    lblTipoPerCli.setText("P. Jurídica");
                    lblNomCli.setText(nom);
                }

                lblTipoDocCli.setText(objTDoc.nombreTipoDocumento(rsCli.getInt("cod_tipo_doc")));
                lblNroDocCli.setText(rsCli.getString("numero_documento"));
            } else{
                JOptionPane.showMessageDialog(null, "El estado del cliente no le permite tramitar préstamos");
            }
        }
    }

    private void agregarEjemplar(String isbn) throws Exception {
        try {
            ResultSet rsEjems = objEjem.obtenerEjemplares(isbn);
            ResultSet rsEjem = null;
            DefaultTableModel modelo = (DefaultTableModel) tblDetalles.getModel();
            boolean repetido = false;

            while (rsEjems.next()) {
                if (rsEjems.getString("sede").equals(clsUsuarioSTATIC.sede)) {
                    rsEjem = objEjem.buscarPorCodigo(rsEjems.getInt("codigo"));
                    break;
                }
            }

            for (int i = 0; i < tblDetalles.getRowCount(); i++) {
                if (tblDetalles.getValueAt(i, 2).toString().equals(isbn)) {
                    repetido = true;
                }
            }

            if (repetido) {
                JOptionPane.showMessageDialog(null, "No se puede llevar dos ejemplares del mismo libro", "Mensaje de Sistema", JOptionPane.WARNING_MESSAGE);
            } else {
                if (rsEjem == null) {
                    JOptionPane.showMessageDialog(null, "El libro seleccionado no tiene ejemplares disponibles en esta sede");
                } else if (rsEjem.next()) {

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

                    modelo.addRow(new Object[]{rsEjem.getString("codigo"), rsEjem.getString("libro"),
                        rsEjem.getString("isbn"), rsEjem.getString("editorial"), rsEjem.getString("sede"), estado});
                }
            }
            tblDetalles.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void eliminarEjemplar(int cod) {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblDetalles.getModel();
            for (int i = 0; i < tblDetalles.getRowCount(); i++) {
                if (Integer.parseInt(String.valueOf(tblDetalles.getValueAt(i, 0))) == cod) {
                    modelo.removeRow(i);
                }
            }
            tblDetalles.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void mostrarFechaLim() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 5); // Sumar 5 días a la fecha actual
        Date datePlusFiveDays = calendar.getTime();

        // Establecer la fecha en el JDateChooser
        calendarFLim.setDate(datePlusFiveDays);

        // Deshabilitar la edición manual del campo de texto
        JTextComponent dateTextField = (JTextComponent) calendarFLim.getDateEditor().getUiComponent();
        dateTextField.setEditable(false);
        dateTextField.setBackground(Color.WHITE); // Fondo blanco para que se vea igual
        dateTextField.setForeground(Color.BLACK);

        for (Component comp : calendarFLim.getComponents()) {
            if (comp instanceof JButton) {
                comp.setEnabled(false); // Deshabilitar el botón para evitar abrir el calendario
            }
        }
    }

    private void limpiarTodo() {
        lblCodCli.setText("");
        lblNomCli.setText("");
        lblTipoPerCli.setText("");
        lblTipoDocCli.setText("");
        lblNroDocCli.setText("");
        lblCodEjem.setText("");
        lblEditorialEjem.setText("");
        lblISBNEjem.setText("");
        lblNomEjem.setText("");
        spnHora.setValue(7);
        spnMin.setValue(0);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        calendarFLim = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtCodPre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        spnHora = new javax.swing.JSpinner();
        spnMin = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblNomCli = new javax.swing.JLabel();
        lblNroDocCli = new javax.swing.JLabel();
        btnNuevoCli = new javax.swing.JButton();
        btnElegirCli = new javax.swing.JButton();
        lblCodCli = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTipoPerCli = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblTipoDocCli = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalles = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblNomEjem = new javax.swing.JLabel();
        btnAgregarEjem = new javax.swing.JButton();
        btnEliminarEjem = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        lblISBNEjem = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblEditorialEjem = new javax.swing.JLabel();
        lblCodEjem = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnAnular = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("PRÉSTAMO");

        jLabel6.setText("Código:");

        jLabel7.setText("Fecha Límite:");

        txtCodPre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodPreKeyTyped(evt);
            }
        });

        jLabel8.setText("Hora Límite:");

        spnHora.setModel(new javax.swing.SpinnerNumberModel(7, 7, 18, 1));
        JFormattedTextField txtField = ((JSpinner.DefaultEditor) spnHora.getEditor()).getTextField();
        txtField.setEditable(false);
        spnHora.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnHoraStateChanged(evt);
            }
        });

        spnMin.setModel(new javax.swing.SpinnerNumberModel(0, 0, 30, 30));
        JFormattedTextField txtField2 = ((JSpinner.DefaultEditor) spnMin.getEditor()).getTextField();
        txtField2.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodPre, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addGap(96, 96, 96)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(calendarFLim, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(spnHora, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnMin, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(calendarFLim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(spnHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(spnMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6)
                        .addComponent(txtCodPre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("CLIENTE");

        jLabel3.setText("Nombre:");

        jLabel4.setText("Código:");

        jLabel5.setText("Nro Doc:");

        lblNomCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblNroDocCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnNuevoCli.setText("Nuevo");
        btnNuevoCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCliActionPerformed(evt);
            }
        });

        btnElegirCli.setText("Elegir Cliente");
        btnElegirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElegirCliActionPerformed(evt);
            }
        });

        lblCodCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setText("Tipo Persona:");

        lblTipoPerCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setText("Tipo Doc:");

        lblTipoDocCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel10))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblTipoDocCli, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblNroDocCli, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(lblCodCli, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(lblTipoPerCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblNomCli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnElegirCli)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNuevoCli)
                        .addGap(35, 35, 35))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(53, 53, 53))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnElegirCli)
                            .addComponent(btnNuevoCli)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblCodCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblTipoPerCli, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNomCli, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(lblNroDocCli, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(lblTipoDocCli, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setText("EJEMPLARES");

        tblDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblDetalles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetallesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDetalles);

        jLabel12.setText("Código:");

        jLabel13.setText("Nombre:");

        lblNomEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnAgregarEjem.setText("Agregar");
        btnAgregarEjem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEjemActionPerformed(evt);
            }
        });

        btnEliminarEjem.setText("Eliminar");
        btnEliminarEjem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEjemActionPerformed(evt);
            }
        });

        jLabel15.setText("ISBN:");

        lblISBNEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel16.setText("Editorial:");

        lblEditorialEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblCodEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel13))
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblCodEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblISBNEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEditorialEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblNomEjem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarEjem)
                    .addComponent(btnEliminarEjem))
                .addGap(41, 41, 41))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblCodEjem, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                                .addComponent(jLabel15)
                                .addComponent(lblEditorialEjem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblISBNEjem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel12))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNomEjem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAgregarEjem)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarEjem)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnAnular.setText("Anular");
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(btnAnular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(49, 49, 49))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAnular)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            // TODO add your handling code here:
            txtCodPre.setEditable(false);
            txtCodPre.setText(String.valueOf(objPrestamo.generarCodPrestamo()));
            mostrarFechaLim();
            llenarTablaInicial();
            String isbn = jdMenuLibros.ISBN;
            if (!isbn.equals("")) {
                agregarEjemplar(isbn);
                System.out.println(isbn);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_formWindowOpened

    private void spnHoraStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnHoraStateChanged
        // TODO add your handling code here:
        int hour = (int) spnHora.getValue();
        if (hour == 18) {
            spnMin.setValue(0); // Se asegura de que el valor de minutos sea 0
            spnMin.setEnabled(false); // Deshabilita el spinner de minutos
        } else {
            spnMin.setEnabled(true); // Habilita el spinner de minutos para otras horas
        }
    }//GEN-LAST:event_spnHoraStateChanged

    private void btnNuevoCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCliActionPerformed
        // TODO add your handling code here:
        jdManCliente objJd = new jdManCliente(null, true);
        objJd.setLocationRelativeTo(null);
        objJd.setVisible(true);
    }//GEN-LAST:event_btnNuevoCliActionPerformed

    private void btnElegirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElegirCliActionPerformed
        try {
            // TODO add your handling code here:
            jdBuscarCliente objJd = new jdBuscarCliente(null, true);
            objJd.setLocationRelativeTo(null);
            objJd.setVisible(true);
            if (objJd.codCli != -1) {
                mostrarDatosCliente(objJd.codCli);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnElegirCliActionPerformed

    private void btnAgregarEjemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEjemActionPerformed
        // TODO add your handling code here:
        jdAgregarEjemplar objJd = new jdAgregarEjemplar(null, true);
        objJd.setLocationRelativeTo(null);
        objJd.setVisible(true);
        String isbn = objJd.isbn;
        try {
            if (!isbn.equals("")) {
                if (tblDetalles.getRowCount() == 3) {
                    JOptionPane.showMessageDialog(null, "Solo se puede llevar 3 ejemplares en un préstamo");
                } else {
                    agregarEjemplar(isbn);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }//GEN-LAST:event_btnAgregarEjemActionPerformed

    private void btnEliminarEjemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEjemActionPerformed
        // TODO add your handling code here:
        if (tblDetalles.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un libro a eliminar del préstamo!");
        } else {
            if (JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este libro del préstamo?", "Mensaje de Sistema", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                lblCodEjem.setText("");
                lblNomEjem.setText("");
                lblEditorialEjem.setText("");
                lblISBNEjem.setText("");
                eliminarEjemplar(Integer.parseInt(tblDetalles.getValueAt(tblDetalles.getSelectedRow(), 0).toString()));
            }
        }


    }//GEN-LAST:event_btnEliminarEjemActionPerformed

    private void tblDetallesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetallesMouseClicked
        try {
            // TODO add your handling code here:
            ResultSet rsEjem = objEjem.buscarPorCodigo(Integer.parseInt(tblDetalles.getValueAt(tblDetalles.getSelectedRow(), 0).toString()));
            if (rsEjem.next()) {
                lblCodEjem.setText(rsEjem.getString("codigo"));
                lblNomEjem.setText(rsEjem.getString("libro"));
                lblISBNEjem.setText(rsEjem.getString("isbn"));
                lblEditorialEjem.setText(rsEjem.getString("editorial"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_tblDetallesMouseClicked

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            if (txtCodPre.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "El código ingresado no es válido");
            } else {
                if (lblCodCli.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente");
                } else {
                    if (tblDetalles.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un ejemplar a prestar");
                    } else {
                        ResultSet rsPre = objPrestamo.buscarPrestamo(Integer.valueOf(txtCodPre.getText()));
                        ResultSet rsPresCli = objPrestamo.buscarPrestamos(Integer.valueOf(lblCodCli.getText()));
                        boolean permitir = true;

                        if (rsPre.next()) {
                            JOptionPane.showMessageDialog(null, "El código de préstamo ingresado ya existe");
                        } else {

                            while (rsPresCli.next()) {
                                if (rsPresCli.getString("estado").equals("P")) {
                                    permitir = false;
                                    break;
                                }
                            }

                            if (permitir) {
                                if (JOptionPane.showConfirmDialog(null, "¿Está seguro de registrar el préstamo?", "Mensaje de sistema", JOptionPane.OK_OPTION) == 0) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    String fechita = sdf.format(calendarFLim.getCalendar().getTime());
                                    objPrestamo.registrarPrestamo(Integer.valueOf(txtCodPre.getText()),
                                            Integer.valueOf(lblCodCli.getText()), fechita, spnHora.getValue() + ":" + spnMin.getValue(), tblDetalles);
                                    JOptionPane.showMessageDialog(null, "Préstamo registrado exitosamente");
                                    limpiarTodo();
                                    txtCodPre.setText(String.valueOf(objPrestamo.generarCodPrestamo()));
                                    mostrarFechaLim();
                                    llenarTablaInicial();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Este cliente no puede realizar préstamos porque aún tiene alguno vigente, debe tramitar su devolución/anulación primero", "Mensaje de Sistema", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                }

            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        try {
            // TODO add your handling code here:
            jdBuscarClienteConPrestamos objJd = new jdBuscarClienteConPrestamos(null, true);
            objJd.setLocationRelativeTo(null);
            objJd.setVisible(true);

            ResultSet rsPre = objPrestamo.buscarPrestamoVigentes(objJd.codCli);
            if (rsPre.next()) {
                if (JOptionPane.showConfirmDialog(null, "¿Está seguro de anular el préstamo con código: " + rsPre.getString("codigo") + "?", "Mensaje de Sistema", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                    if (rsPre.getString("estado").equals("P")) {
                        objPrestamo.anularPrestamo(rsPre.getInt("codigo"));
                        JOptionPane.showMessageDialog(null, "Préstamo anulado correctamente");
                        limpiarTodo();
                        txtCodPre.setText(String.valueOf(objPrestamo.generarCodPrestamo()));
                        mostrarFechaLim();
                        llenarTablaInicial();
                    } else {
                        JOptionPane.showMessageDialog(null, "El préstamo que intenta anular no se encuentra vigente", "Mensaje de Sistema", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "El cliente ingresado no tiene préstamos vigentes");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnAnularActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(null, "¿Está seguro de salir?, Perderá todos los datos que haya ingresado.", "Mensaje de Sistema", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtCodPreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodPreKeyTyped
        // TODO add your handling code here:
        Character objTecla = evt.getKeyChar();
        if (!Character.isDigit(objTecla)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodPreKeyTyped

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            String isbn = jdMenuLibros.ISBN;
            if (!isbn.equals("")) {
                agregarEjemplar(isbn);
            }
        } catch (Exception ex) {
            Logger.getLogger(jdTranReserva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarEjem;
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnElegirCli;
    private javax.swing.JButton btnEliminarEjem;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevoCli;
    private javax.swing.JButton btnSalir;
    private com.toedter.calendar.JDateChooser calendarFLim;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodCli;
    private javax.swing.JLabel lblCodEjem;
    private javax.swing.JLabel lblEditorialEjem;
    private javax.swing.JLabel lblISBNEjem;
    private javax.swing.JLabel lblNomCli;
    private javax.swing.JLabel lblNomEjem;
    private javax.swing.JLabel lblNroDocCli;
    private javax.swing.JLabel lblTipoDocCli;
    private javax.swing.JLabel lblTipoPerCli;
    private javax.swing.JSpinner spnHora;
    private javax.swing.JSpinner spnMin;
    private javax.swing.JTable tblDetalles;
    private javax.swing.JTextField txtCodPre;
    // End of variables declaration//GEN-END:variables
}
