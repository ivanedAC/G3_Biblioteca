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
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;
import java.sql.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class jdTranDevolucion extends javax.swing.JDialog {

    clsPrestamo objPrestamo = new clsPrestamo();
    clsCliente objCliente = new clsCliente();
    clsEjemplar objEjem = new clsEjemplar();
    clsTipoDocumento objTDoc = new clsTipoDocumento();
    clsDevolucion objDev = new clsDevolucion();
    clsSancion objSan = new clsSancion();
    clsReserva objReserva = new clsReserva();
    clsSede objSede = new clsSede();
    Integer codPrestamo = -1;

    /**
     * Creates new form jdTranPrestamo
     */
    public jdTranDevolucion(java.awt.Frame parent, boolean modal) {
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

        DefaultTableModel modelo1 = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo1.addColumn("Código");
        modelo1.addColumn("Nombre");
        modelo1.addColumn("ISBN");
        modelo1.addColumn("Editorial");
        modelo1.addColumn("Sede");
        modelo1.addColumn("Estado");
        modelo1.addColumn("Sanción");

        tblEjemplaresP.setModel(modelo);
        tblEjemplaresD.setModel(modelo1);
        tblEjemplaresD.getTableHeader().setReorderingAllowed(false);
        tblEjemplaresP.getTableHeader().setReorderingAllowed(false);
    }

    private void listarSanciones() throws Exception {
        ResultSet rsSan = null;
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        cboxSanciones.setModel(modelo);
        modelo.addElement("Ninguna");
        try {
            rsSan = objSan.listarSancionesSin1();
            while (rsSan.next()) {
                modelo.addElement("Código-" + rsSan.getString("codigo"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void mostrarDatosCliente(Integer cod) throws Exception {
        ResultSet rsCli = objCliente.buscarClientePorCodigo(cod);
        ResultSet rsPresV = objPrestamo.buscarPrestamoVigentes(cod);
        if (rsPresV.next()) {
            if (rsCli.next()) {
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
                codPrestamo = rsPresV.getInt("codigo");

            }
        } else {
            JOptionPane.showMessageDialog(null, "El cliente seleccionado no tiene préstamos vigentes");
        }

    }

    private void llenarTablaP() throws Exception {
        ResultSet rsEjemV = objDev.listarDetallesPendientes(codPrestamo);
        DefaultTableModel modelo = (DefaultTableModel) tblEjemplaresP.getModel();

        while (rsEjemV.next()) {
            String estado = "";
            switch (rsEjemV.getString("estado")) {
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

            modelo.addRow(new Object[]{rsEjemV.getString("codigo"), rsEjemV.getString("libro"),
                rsEjemV.getString("isbn"), rsEjemV.getString("editorial"), rsEjemV.getString("sede"), estado});
        }

    }

    private void eliminarEjemplar(int cod) {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblEjemplaresP.getModel();
            for (int i = 0; i < tblEjemplaresP.getRowCount(); i++) {
                if (Integer.parseInt(String.valueOf(tblEjemplaresP.getValueAt(i, 0))) == cod) {
                    modelo.removeRow(i);
                }
            }
            tblEjemplaresP.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    private void mostrarFecha() {
        Calendar calendar = Calendar.getInstance();
        Date datePlusFiveDays = calendar.getTime();

        calendarFLim.setDate(datePlusFiveDays);

        JTextComponent dateTextField = (JTextComponent) calendarFLim.getDateEditor().getUiComponent();
        dateTextField.setEditable(false);
        dateTextField.setBackground(Color.WHITE);
        dateTextField.setForeground(Color.BLACK);

        for (Component comp : calendarFLim.getComponents()) {
            if (comp instanceof JButton) {
                comp.setEnabled(false);
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
    }

    private void limpiarEjemplar() {
        lblCodEjem.setText("");
        lblEditorialEjem.setText("");
        lblISBNEjem.setText("");
        lblNomEjem.setText("");
        cboxSanciones.setSelectedIndex(0);
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
        jLabel6 = new javax.swing.JLabel();
        calendarFLim = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtCodDev = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnElegirCli = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblNomCli = new javax.swing.JLabel();
        lblNroDocCli = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblTipoDocCli = new javax.swing.JLabel();
        lblCodCli = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTipoPerCli = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEjemplaresP = new javax.swing.JTable();
        btnDevolverEjem = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblEjemplaresD = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        lblNomEjem = new javax.swing.JLabel();
        cboxSanciones = new javax.swing.JComboBox<>();
        lblISBNEjem = new javax.swing.JLabel();
        lblCodEjem = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnConsultarSanciones = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        lblEditorialEjem = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnFinalizar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(243, 226, 210));

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Código:");

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Fecha de Devolución:");

        txtCodDev.setBackground(new java.awt.Color(255, 255, 255));
        txtCodDev.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodDevKeyTyped(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(66, 39, 5));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("DEVOLUCIÓN:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(txtCodDev, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(calendarFLim, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(227, 227, 227))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(calendarFLim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtCodDev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(243, 226, 210));

        btnElegirCli.setBackground(new java.awt.Color(113, 49, 18));
        btnElegirCli.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnElegirCli.setForeground(new java.awt.Color(255, 255, 255));
        btnElegirCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/choose_cliente_32.png"))); // NOI18N
        btnElegirCli.setText("Elegir");
        btnElegirCli.setToolTipText("");
        btnElegirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElegirCliActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(113, 49, 18));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CLIENTE");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(243, 226, 210));

        lblNomCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblNroDocCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nombre:");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Nro Doc:");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Código:");

        lblTipoDocCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblCodCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Tipo Persona:");

        lblTipoPerCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Tipo Doc:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel10))
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(lblTipoDocCli, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblNroDocCli, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addComponent(lblCodCli, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(lblTipoPerCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblNomCli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblCodCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9))
                    .addComponent(lblTipoPerCli, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNomCli, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(lblNroDocCli, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(lblTipoDocCli, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel18.setText("jLabel14");
        jLabel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnElegirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnElegirCli)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(243, 226, 210));

        tblEjemplaresP.setBackground(new java.awt.Color(255, 255, 255));
        tblEjemplaresP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblEjemplaresP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEjemplaresPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEjemplaresP);

        btnDevolverEjem.setBackground(new java.awt.Color(113, 49, 18));
        btnDevolverEjem.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnDevolverEjem.setForeground(new java.awt.Color(255, 255, 255));
        btnDevolverEjem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/leave_16.png"))); // NOI18N
        btnDevolverEjem.setText("Devolver");
        btnDevolverEjem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDevolverEjemActionPerformed(evt);
            }
        });

        tblEjemplaresD.setBackground(new java.awt.Color(255, 255, 255));
        tblEjemplaresD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tblEjemplaresD);

        jPanel8.setBackground(new java.awt.Color(113, 49, 18));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("EJEMPLARES PENDIENTES:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(113, 49, 18));

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("EJEMPLARES A DEVOLVER:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(243, 226, 210));

        lblNomEjem.setBackground(new java.awt.Color(255, 255, 255));
        lblNomEjem.setForeground(new java.awt.Color(0, 0, 0));
        lblNomEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cboxSanciones.setBackground(new java.awt.Color(255, 255, 255));
        cboxSanciones.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cboxSanciones.setForeground(new java.awt.Color(0, 0, 0));
        cboxSanciones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblISBNEjem.setBackground(new java.awt.Color(255, 255, 255));
        lblISBNEjem.setForeground(new java.awt.Color(0, 0, 0));
        lblISBNEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblCodEjem.setBackground(new java.awt.Color(255, 255, 255));
        lblCodEjem.setForeground(new java.awt.Color(0, 0, 0));
        lblCodEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Código:");

        jLabel17.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Sanción:");

        jLabel16.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Editorial:");

        btnConsultarSanciones.setBackground(new java.awt.Color(113, 49, 18));
        btnConsultarSanciones.setForeground(new java.awt.Color(243, 226, 210));
        btnConsultarSanciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/sancion.png"))); // NOI18N
        btnConsultarSanciones.setText("Consultar Sanciones");
        btnConsultarSanciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarSancionesActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Nombre:");

        lblEditorialEjem.setBackground(new java.awt.Color(255, 255, 255));
        lblEditorialEjem.setForeground(new java.awt.Color(0, 0, 0));
        lblEditorialEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("ISBN:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel13))
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(lblCodEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblISBNEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEditorialEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblNomEjem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(cboxSanciones, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnConsultarSanciones)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCodEjem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(jLabel15))
                    .addComponent(lblEditorialEjem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblISBNEjem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNomEjem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboxSanciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConsultarSanciones))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(32, 32, 32)
                                .addComponent(btnDevolverEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDevolverEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(66, 39, 5));

        btnFinalizar.setBackground(new java.awt.Color(255, 255, 255));
        btnFinalizar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnFinalizar.setForeground(new java.awt.Color(66, 39, 5));
        btnFinalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/finish_devolucion.png"))); // NOI18N
        btnFinalizar.setText("Finalizar");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));
        btnSalir.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(66, 39, 5));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/leave_transa.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
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
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            // TODO add your handling code here:
            txtCodDev.setText(String.valueOf(objDev.generarCodDevolucion()));
            mostrarFecha();
            llenarTablaInicial();
            listarSanciones();
            txtCodDev.setEditable(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_formWindowOpened

    private void btnElegirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElegirCliActionPerformed
        try {
            // TODO add your handling code here:
            jdBuscarClienteConPrestamos objJd = new jdBuscarClienteConPrestamos(null, true);
            objJd.setLocationRelativeTo(null);
            objJd.setVisible(true);
            if (objJd.codCli != -1) {
                mostrarDatosCliente(objJd.codCli);
                llenarTablaInicial();
                llenarTablaP();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnElegirCliActionPerformed

    private void btnDevolverEjemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDevolverEjemActionPerformed
        // TODO add your handling code here:
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblEjemplaresD.getModel();

            if (tblEjemplaresP.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Ya no hay más ejemplares por devolver");
            } else {
                if (lblCodEjem.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un ejemplar a devolver");
                } else {
                    int cod = Integer.parseInt(lblCodEjem.getText());
                    if (JOptionPane.showConfirmDialog(null, "¿Está seguro de devolver el ejemplar " + lblNomEjem.getText() + "?", "Mensaje de Sistema", JOptionPane.OK_OPTION) == 0) {
                        ResultSet rsEjem = objEjem.buscarPorCodigo(cod);
                        if (rsEjem.next()) {
                            if (rsEjem.getString("estado").equals("P")) {
                                String sancion = cboxSanciones.getSelectedItem().toString();

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

                                ResultSet rsPre = objPrestamo.buscarPrestamo(codPrestamo);

                                boolean san1 = false;

                                if (rsPre.next()) {
                                    san1 = objDev.validarFLimite(rsPre.getString("f_limite"), rsPre.getString("h_limite"));
                                }

                                if (san1 && cboxSanciones.getSelectedIndex() == 0) {
                                    JOptionPane.showMessageDialog(null, "El sistema le asignará automáticamente una sanción por tardanza en la devolución");
                                    sancion = "Codigo-1";
                                }

                                modelo.addRow(new Object[]{rsEjem.getString("codigo"), rsEjem.getString("libro"),
                                    rsEjem.getString("isbn"), rsEjem.getString("editorial"), rsEjem.getString("sede"), estado, sancion});
                                eliminarEjemplar(cod);
                                limpiarEjemplar();
                            } else {
                                JOptionPane.showMessageDialog(null, "El estado del ejemplar debe ser (P) prestado");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_btnDevolverEjemActionPerformed

    private void tblEjemplaresPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEjemplaresPMouseClicked
        try {
            // TODO add your handling code here:
            ResultSet rsEjem = objEjem.buscarPorCodigo(Integer.parseInt(tblEjemplaresP.getValueAt(tblEjemplaresP.getSelectedRow(), 0).toString()));
            if (rsEjem.next()) {
                lblCodEjem.setText(rsEjem.getString("codigo"));
                lblNomEjem.setText(rsEjem.getString("libro"));
                lblISBNEjem.setText(rsEjem.getString("isbn"));
                lblEditorialEjem.setText(rsEjem.getString("editorial"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_tblEjemplaresPMouseClicked

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
//        try {
//            if (txtCodDev.getText().isBlank()) {
//                JOptionPane.showMessageDialog(null, "El código ingresado no es válido");
//            } else {
//                if (lblCodCli.getText().isBlank()) {
//                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente");
//                } else {
//                    if (tblEjemplaresD.getRowCount() == 0) {
//                        JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un ejemplar a devolver");
//                    } else {
//                        if (JOptionPane.showConfirmDialog(null, "¿Está seguro de finalizar la devolución?", "Mensaje de sistema", JOptionPane.OK_OPTION) == 0) {
//                            objDev.registrarDevolucion(Integer.valueOf(txtCodDev.getText()), codPrestamo, tblEjemplaresD);
//                            JOptionPane.showMessageDialog(null, "Devolución registrada exitosamente");
//                            for (int i = 0; i < tblEjemplaresD.getRowCount(); i++) {
//                                ResultSet rsEjemplarReser = objReserva.verificarReservaLibro(
//                                    tblEjemplaresD.getValueAt(i, 3).toString(),
//                                    objSede.obtenerSede(clsUsuarioSTATIC.sede));
//                                if (rsEjemplarReser.next()) {                                
//                                    JOptionPane.showMessageDialog(this, "El ejemplar con isbn: "+rsEjemplarReser.getString("isbn")+" está reservado","Mensaje Sistema",JOptionPane.INFORMATION_MESSAGE);
//                                    objReserva.insertarDetalleReserva(
//                                            rsEjemplarReser.getInt("cod_reserva"),
//                                            Integer.parseInt(txtCodDev.getText()),
//                                            rsEjemplarReser.getInt("cod_cliente"),
//                                            Integer.parseInt((String) tblEjemplaresD.getValueAt(i, 0)),
//                                            tblEjemplaresD.getValueAt(i, 3).toString());
//                                    System.out.println(rsEjemplarReser.getInt("cod_cliente"));
//                                }
//                                
//                            }
//                            
//                            limpiarTodo();
//                            txtCodDev.setText(String.valueOf(objDev.generarCodDevolucion()));
//                            mostrarFecha();
//                            llenarTablaInicial();
//                            
//                        }
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        }

        try {
            if (txtCodDev.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "El código ingresado no es válido");
            } else {
                if (lblCodCli.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente");
                } else {
                    if (tblEjemplaresD.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un ejemplar a devolver");
                    } else {
                        if (JOptionPane.showConfirmDialog(null, "¿Está seguro de finalizar la devolución?", "Mensaje de sistema", JOptionPane.OK_OPTION) == 0) {
                            objDev.registrarDevolucion(Integer.valueOf(txtCodDev.getText()), codPrestamo, tblEjemplaresD);
                            JOptionPane.showMessageDialog(null, "Devolución registrada exitosamente");

                            for (int i = 0; i < tblEjemplaresD.getRowCount(); i++) {
                                String isbn = tblEjemplaresD.getValueAt(i, 2).toString();
                                int codEjemplar = Integer.parseInt((String) tblEjemplaresD.getValueAt(i, 0));

                                ResultSet rsEjemplarReser = objReserva.verificarReservaLibro(isbn, objSede.obtenerSede(clsUsuarioSTATIC.sede));
                                if (rsEjemplarReser != null && rsEjemplarReser.next()) {
                                    JOptionPane.showMessageDialog(this, "El ejemplar con ISBN: " + isbn + " se encuentra en una reserva pendiente\n"
                                            + "Datos del cliente que realizó la reserva:\n"
                                            + "Nombre: " + rsEjemplarReser.getString("nombres"), "Mensaje Sistema", JOptionPane.INFORMATION_MESSAGE);

                                    objReserva.insertarDetalleReserva(
                                            rsEjemplarReser.getInt("cod_reserva"),
                                            Integer.parseInt(txtCodDev.getText()),
                                            rsEjemplarReser.getInt("cod_cliente"),
                                            codEjemplar,
                                            isbn
                                    );
                                }
                            }

                            limpiarTodo();
                            txtCodDev.setText(String.valueOf(objDev.generarCodDevolucion()));
                            mostrarFecha();
                            llenarTablaInicial();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Mensaje de error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(null, "¿Está seguro de salir?, Perderá todos los datos que haya ingresado.", "Mensaje de Sistema", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtCodDevKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodDevKeyTyped
        // TODO add your handling code here:
        Character objTecla = evt.getKeyChar();
        if (!Character.isDigit(objTecla)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodDevKeyTyped

    private void btnConsultarSancionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarSancionesActionPerformed
        // TODO add your handling code here:
        jdConsultarSanciones objJd = new jdConsultarSanciones(null, true);
        objJd.setLocationRelativeTo(null);
        objJd.setVisible(true);
    }//GEN-LAST:event_btnConsultarSancionesActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultarSanciones;
    private javax.swing.JButton btnDevolverEjem;
    private javax.swing.JButton btnElegirCli;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnSalir;
    private com.toedter.calendar.JDateChooser calendarFLim;
    private javax.swing.JComboBox<String> cboxSanciones;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCodCli;
    private javax.swing.JLabel lblCodEjem;
    private javax.swing.JLabel lblEditorialEjem;
    private javax.swing.JLabel lblISBNEjem;
    private javax.swing.JLabel lblNomCli;
    private javax.swing.JLabel lblNomEjem;
    private javax.swing.JLabel lblNroDocCli;
    private javax.swing.JLabel lblTipoDocCli;
    private javax.swing.JLabel lblTipoPerCli;
    private javax.swing.JTable tblEjemplaresD;
    private javax.swing.JTable tblEjemplaresP;
    private javax.swing.JTextField txtCodDev;
    // End of variables declaration//GEN-END:variables
}
