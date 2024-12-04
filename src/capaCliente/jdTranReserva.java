/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.clsCliente;
import capaLogica.clsEjemplar;
import capaLogica.clsPrestamo;
import capaLogica.clsReserva;
import capaLogica.clsSede;
import capaLogica.clsTipoDocumento;
import capaLogica.clsUsuarioSTATIC;
import java.awt.Color;
import java.awt.Component;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author ander
 */
public class jdTranReserva extends javax.swing.JDialog {

    clsPrestamo objPrestamo = new clsPrestamo();
    clsCliente objCliente = new clsCliente();
    clsEjemplar objEjem = new clsEjemplar();
    clsTipoDocumento objTDoc = new clsTipoDocumento();
    clsReserva objReserva = new clsReserva();
    clsSede objSede = new clsSede();

    /**
     * Creates new form jdTranReserva
     */
    public jdTranReserva(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Menú - Transacción Reserva");

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

    private void limpiarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblDetalles.getModel();
        modelo.setRowCount(0);
    }

    private void mostrarDatosCliente(Integer cod) throws Exception {
        ResultSet rsCli = objCliente.buscarClientePorCodigo(cod);
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
        }
    }

    private void agregarEjemplar(String isbn) throws Exception {
        try {
            ResultSet rsEjems = objEjem.obtenerEjemplaresPrestados(isbn);
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
                JOptionPane.showMessageDialog(null, "No se puede reservar dos veces el mismo libro", "Mensaje de Sistema", JOptionPane.WARNING_MESSAGE);
            } else {
                if (rsEjem == null) {
                    JOptionPane.showMessageDialog(null, "El libro seleccionado no tiene ejemplares disponibles en esta sede");
                } else if (rsEjem.next()) {
                    modelo.addRow(new Object[]{rsEjem.getString("codigo"), rsEjem.getString("libro"),
                        rsEjem.getString("isbn"), rsEjem.getString("editorial"), rsEjem.getString("sede"), rsEjem.getString("estado")});
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

    private String fechaStimada(String isbn) throws Exception {
        Date fechaAprox = objReserva.obtenerFechaAproximada(isbn);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = sdf.format(fechaAprox);

        return fechaFormateada;
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
        lblFechaEstimada.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
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
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        calendarFLim = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtCodRes = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
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
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        lblFechaEstimada = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Reservas");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(243, 226, 210));

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nombre:");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Código:");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Nro Doc:");

        lblNomCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblNroDocCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnNuevoCli.setBackground(new java.awt.Color(113, 49, 18));
        btnNuevoCli.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnNuevoCli.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add_cliente.png"))); // NOI18N
        btnNuevoCli.setText("Nuevo");
        btnNuevoCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCliActionPerformed(evt);
            }
        });

        btnElegirCli.setBackground(new java.awt.Color(113, 49, 18));
        btnElegirCli.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnElegirCli.setForeground(new java.awt.Color(255, 255, 255));
        btnElegirCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/choose_cliente_32.png"))); // NOI18N
        btnElegirCli.setText("Elegir");
        btnElegirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElegirCliActionPerformed(evt);
            }
        });

        lblCodCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Tipo Persona:");

        lblTipoPerCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Tipo Doc:");

        lblTipoDocCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel6.setBackground(new java.awt.Color(113, 49, 18));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CLIENTE");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
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

        jLabel14.setText("jLabel14");
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(lblTipoDocCli, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(lblNroDocCli, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(lblCodCli, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel9)
                            .addGap(18, 18, 18)
                            .addComponent(lblTipoPerCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(2, 2, 2)
                            .addComponent(jLabel3)
                            .addGap(18, 18, 18)
                            .addComponent(lblNomCli, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(101, 101, 101)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnElegirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(81, 81, 81))
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblNroDocCli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnElegirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnNuevoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCodCli, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTipoPerCli, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblNomCli, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(lblTipoDocCli, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(22, 22, 22))
        );

        jPanel1.setBackground(new java.awt.Color(243, 226, 210));

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Código:");

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Fecha Reserva:");

        txtCodRes.setBackground(new java.awt.Color(255, 255, 255));
        txtCodRes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodResKeyTyped(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(66, 39, 5));

        jLabel2.setBackground(new java.awt.Color(66, 39, 5));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("REGISTRAR RESERVA");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodRes, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(calendarFLim, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(calendarFLim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6)
                        .addComponent(txtCodRes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(243, 226, 210));

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

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Código:");

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Nombre:");

        lblNomEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnAgregarEjem.setBackground(new java.awt.Color(113, 49, 18));
        btnAgregarEjem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add_book.png"))); // NOI18N
        btnAgregarEjem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEjemActionPerformed(evt);
            }
        });

        btnEliminarEjem.setBackground(new java.awt.Color(113, 49, 18));
        btnEliminarEjem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/remove_book.png"))); // NOI18N
        btnEliminarEjem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEjemActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("ISBN:");

        lblISBNEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel16.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Editorial:");

        lblEditorialEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblCodEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel7.setBackground(new java.awt.Color(113, 49, 18));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("EJEMPLARES");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(13, 13, 13)
                        .addComponent(lblNomEjem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCodEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblISBNEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblEditorialEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37)
                .addComponent(btnAgregarEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnEliminarEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(lblCodEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblISBNEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEditorialEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNomEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnEliminarEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(243, 226, 210));

        jLabel17.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Fecha estimada para recoger libro:");

        lblFechaEstimada.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 0, 51));
        jLabel18.setText("NOTA: La reserva no asegura el libro para el cliente, recordar que se le avisara cuando recoger el libro");

        jPanel8.setBackground(new java.awt.Color(66, 39, 5));

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(113, 49, 18));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/save_transa.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));
        btnSalir.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(113, 49, 18));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/leave_transa.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFechaEstimada, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(545, 545, 545))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFechaEstimada, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            mostrarDatosCliente(objJd.codCli);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnElegirCliActionPerformed

    private void txtCodResKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodResKeyTyped
        // TODO add your handling code here:
        Character objTecla = evt.getKeyChar();
        if (!Character.isDigit(objTecla)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodResKeyTyped

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

    private void btnAgregarEjemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEjemActionPerformed
        // TODO add your handling code here:
        jdAgregarEjemplarReserva objJd = new jdAgregarEjemplarReserva(null, true);
        objJd.setLocationRelativeTo(null);
        objJd.setVisible(true);
        String isbn = objJd.isbn;

        try {
            if (!isbn.equals("")) {
                lblFechaEstimada.setText(fechaStimada(isbn));
                if (tblDetalles.getRowCount() == 1) {
                    JOptionPane.showMessageDialog(null, "Solo se puede reservar 1 libro por cliente");
                } else {
                    agregarEjemplar(isbn);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error " + ex.getMessage());
        }
    }//GEN-LAST:event_btnAgregarEjemActionPerformed

    private void btnEliminarEjemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEjemActionPerformed
        // TODO add your handling code here:
        if (tblDetalles.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un libro a eliminar de la reserva!");
        } else {
            if (JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este libro de la reserva?", "Mensaje de Sistema", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                lblCodEjem.setText("");
                lblNomEjem.setText("");
                lblEditorialEjem.setText("");
                lblISBNEjem.setText("");
                eliminarEjemplar(Integer.parseInt(tblDetalles.getValueAt(tblDetalles.getSelectedRow(), 0).toString()));
            }
        }

    }//GEN-LAST:event_btnEliminarEjemActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            if (txtCodRes.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "El código ingresado no es válido");
            } else {
                if (lblCodCli.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente");
                } else {
                    if (tblDetalles.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un ejemplar para reservar");
                    } else {
                        ResultSet rsPre = objReserva.buscarReserva(Integer.valueOf(txtCodRes.getText()));
                        ResultSet rsPresCli = objPrestamo.buscarPrestamos(Integer.valueOf(lblCodCli.getText()));
                        boolean permitir = true;

                        if (rsPre.next()) {
                            JOptionPane.showMessageDialog(null, "El código de reserva ingresado ya existe");
                        } else {

                            while (rsPresCli.next()) {
                                if (rsPresCli.getString("estado").equals("P")) {
                                    permitir = false;
                                    break;
                                }
                            }

                            if (permitir) {
                                if (objReserva.verificarExistenciaReserva(Integer.valueOf(lblCodCli.getText()))) {
                                    JOptionPane.showMessageDialog(this, "El cliente ya tiene una reserva registrada, no puede registrar otra", "Mensaje del sistema", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    if (JOptionPane.showConfirmDialog(null, "¿Está seguro de registrar la reserva?", "Mensaje de sistema", JOptionPane.YES_NO_OPTION) == 0) {

                                        objReserva.registrarReserva(
                                                Integer.parseInt(txtCodRes.getText()),
                                                Integer.parseInt(lblCodCli.getText()),
                                                tblDetalles,
                                                objSede.obtenerSede(clsUsuarioSTATIC.sede));

                                        JOptionPane.showMessageDialog(null, "Reserva registrado exitosamente");
                                        limpiarTodo();
                                        txtCodRes.setText(String.valueOf(objReserva.generarCodReserva()));
                                        mostrarFecha();
                                        llenarTablaInicial();
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Este cliente no puede realizar un reserva porque aún tiene un prestamo vigente, debe tramitar su devolución/anulación primero", "Mensaje de Sistema", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(null, "¿Está seguro de salir?, Perderá todos los datos que haya ingresado.", "Mensaje de Sistema", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            // TODO add your handling code here:
            txtCodRes.setText(objReserva.generarCodReserva().toString());
            txtCodRes.setEditable(false);
            llenarTablaInicial();
            mostrarFecha();
            limpiarTabla();
            String isbn = jdMenuLibros.ISBN;
            if (!isbn.equals("") && jdMenuLibros.bandera) {
                agregarEjemplar(isbn);
                fechaStimada(isbn);
            }

        } catch (Exception ex) {
            Logger.getLogger(jdTranReserva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        jdMenuLibros.bandera = false;
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarEjem;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodCli;
    private javax.swing.JLabel lblCodEjem;
    private javax.swing.JLabel lblEditorialEjem;
    private javax.swing.JLabel lblFechaEstimada;
    private javax.swing.JLabel lblISBNEjem;
    private javax.swing.JLabel lblNomCli;
    private javax.swing.JLabel lblNomEjem;
    private javax.swing.JLabel lblNroDocCli;
    private javax.swing.JLabel lblTipoDocCli;
    private javax.swing.JLabel lblTipoPerCli;
    private javax.swing.JTable tblDetalles;
    private javax.swing.JTextField txtCodRes;
    // End of variables declaration//GEN-END:variables
}
