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
 * @author laboratorio_computo
 */
public class jdTranReservaCompleta extends javax.swing.JDialog {

    clsPrestamo objPrestamo = new clsPrestamo();
    clsCliente objCliente = new clsCliente();
    clsEjemplar objEjem = new clsEjemplar();
    clsTipoDocumento objTDoc = new clsTipoDocumento();
    clsReserva objReserva = new clsReserva();

    /**
     * Creates new form jdTranReservaCompleta
     */
    public jdTranReservaCompleta(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Menú - Transacción Reserva Completa");

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

    private void agregarEjemplarParaClienteReserva(String isbn) throws Exception {
        try {
            ResultSet rsReserva = objReserva.obtenerEjemplarReservado(jdBuscarClienteConReservas.codCli);
            boolean tieneReserva = false;
            int codigoEjemplarReserv = 0;

            while (rsReserva.next()) {
                if (rsReserva.getString("isbn").equals(isbn)) {
                    tieneReserva = true;
                    codigoEjemplarReserv = rsReserva.getInt("cod_ejemplar");
                    break;
                }
            }

            if (!tieneReserva) {
                JOptionPane.showMessageDialog(null, "El cliente no tiene una reserva para este libro.", "Mensaje de Sistema", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ResultSet rsEjems = objEjem.obtenerEjemplares_V2(isbn);
            DefaultTableModel modelo = (DefaultTableModel) tblDetalles.getModel();
            boolean reemplazado = false;

            for (int i = 0; i < tblDetalles.getRowCount(); i++) {
                String isbnTabla = tblDetalles.getValueAt(i, 2).toString();
                int codigoTabla = Integer.parseInt(tblDetalles.getValueAt(i, 0).toString());

                if (isbnTabla.equals(isbn)) {
                    if (codigoTabla != codigoEjemplarReserv) {
                        modelo.removeRow(i);
                    } else {
                        reemplazado = true;
                    }
                    break;
                }
            }

            if (!reemplazado) {
                while (rsEjems.next()) {
                    if (rsEjems.getInt("codigo") == codigoEjemplarReserv) {
                        String estado = "";

                        switch (rsEjems.getString("estado")) {
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
                            rsEjems.getString("codigo"),
                            rsEjems.getString("libro"),
                            rsEjems.getString("isbn"),
                            rsEjems.getString("editorial"),
                            rsEjems.getString("sede"),
                            estado
                        });

                        JOptionPane.showMessageDialog(null, "Ejemplar reservado agregado correctamente.",
                                "Mensaje de Sistema", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                }
            }

            tblDetalles.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEjemplar(int cod, int codCli) {
        int codEjem = 0;
        try {
            ResultSet rs = objReserva.obtenerEjemplarReservado(codCli);
            if (rs.next()) {
                codEjem = rs.getInt("cod_ejemplar");
            }

            DefaultTableModel modelo = (DefaultTableModel) tblDetalles.getModel();
            for (int i = 0; i < tblDetalles.getRowCount(); i++) {
                if (Integer.parseInt(String.valueOf(tblDetalles.getValueAt(i, 0))) == cod) {
                    if (cod != codEjem) {
                        modelo.removeRow(i);
                    } else {
                        JOptionPane.showMessageDialog(this, "No se puede eliminar el ejemplar reservado",
                                "Mensaje de sistema", JOptionPane.INFORMATION_MESSAGE);
                    }
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnAnular = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblNomCli = new javax.swing.JLabel();
        lblNroDocCli = new javax.swing.JLabel();
        btnElegirCli = new javax.swing.JButton();
        lblCodCli = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTipoPerCli = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblTipoDocCli = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        calendarFLim = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtCodPre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        spnHora = new javax.swing.JSpinner();
        spnMin = new javax.swing.JSpinner();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDetalles = new javax.swing.JTable();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        lblNomEjem = new javax.swing.JLabel();
        btnAgregarEjem4 = new javax.swing.JButton();
        btnEliminarEjem4 = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        lblISBNEjem = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        lblEditorialEjem = new javax.swing.JLabel();
        lblCodEjem = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Préstamo de Reservas");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(66, 39, 5));

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

        btnAnular.setBackground(new java.awt.Color(255, 255, 255));
        btnAnular.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAnular.setForeground(new java.awt.Color(113, 49, 18));
        btnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cancel_transa.png"))); // NOI18N
        btnAnular.setText("Anular");
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel2.setBackground(new java.awt.Color(243, 226, 210));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nombre:");

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Código:");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Nro Doc:");

        lblNomCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblNroDocCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnElegirCli.setBackground(new java.awt.Color(113, 49, 18));
        btnElegirCli.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
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

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Tipo Doc:");

        lblTipoDocCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel4.setBackground(new java.awt.Color(113, 49, 18));

        jLabel1.setBackground(new java.awt.Color(66, 39, 5));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CLIENTE");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
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
                .addGap(15, 15, 15)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel10)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblCodCli, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(lblTipoPerCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblNomCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTipoDocCli, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(lblNroDocCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(39, 39, 39)
                .addComponent(btnElegirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblCodCli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTipoPerCli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(lblNomCli, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(btnElegirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNroDocCli, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTipoDocCli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel5))
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel1.setBackground(new java.awt.Color(243, 226, 210));

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Código:");

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Fecha Límite:");

        txtCodPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPreActionPerformed(evt);
            }
        });
        txtCodPre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodPreKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
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

        jPanel3.setBackground(new java.awt.Color(66, 39, 5));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("PRÉSTAMO DE RESERVAS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCodPre)
                .addGap(72, 72, 72)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(calendarFLim, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(spnHora, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnMin, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(calendarFLim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(spnHora, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnMin, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6)
                        .addComponent(txtCodPre, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(243, 226, 210));

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
        jScrollPane5.setViewportView(tblDetalles);

        jLabel32.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Código:");

        jLabel33.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Nombre:");

        lblNomEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnAgregarEjem4.setBackground(new java.awt.Color(113, 49, 18));
        btnAgregarEjem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/add_book.png"))); // NOI18N
        btnAgregarEjem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEjem4ActionPerformed(evt);
            }
        });

        btnEliminarEjem4.setBackground(new java.awt.Color(113, 49, 18));
        btnEliminarEjem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/remove_book.png"))); // NOI18N
        btnEliminarEjem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEjem4ActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("ISBN:");

        lblISBNEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel35.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("Editorial:");

        lblEditorialEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblCodEjem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel5.setBackground(new java.awt.Color(113, 49, 18));

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("EJEMPLARES");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel31)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(lblCodEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblISBNEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEditorialEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblNomEjem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26)
                        .addComponent(btnAgregarEjem4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarEjem4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 29, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addComponent(lblCodEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblEditorialEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addGap(6, 6, 6))
                            .addComponent(lblISBNEjem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNomEjem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAgregarEjem4, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                            .addComponent(btnEliminarEjem4, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            int codReserva = 0;
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

                            ResultSet rsReserva = objReserva.obtenerEjemplarReservado(Integer.parseInt(lblCodCli.getText()));
                            if (rsReserva.next()) {
                                codReserva = rsReserva.getInt("codigo");
                            } else {
                                codReserva = 0;
                            }

                            if (permitir) {
                                if (JOptionPane.showConfirmDialog(null, "¿Está seguro de registrar el préstamo?", "Mensaje de sistema", JOptionPane.OK_OPTION) == 0) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    String fechita = sdf.format(calendarFLim.getCalendar().getTime());
                                    objPrestamo.registrarPrestamo_V2(Integer.valueOf(txtCodPre.getText()),
                                            Integer.valueOf(lblCodCli.getText()), fechita, spnHora.getValue() + ":" + spnMin.getValue(), tblDetalles, codReserva);
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

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(null, "¿Está seguro de salir?, Perderá todos los datos que haya ingresado.", "Mensaje de Sistema", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        try {
            // TODO add your handling code here:
            ResultSet rsPre = objPrestamo.buscarPrestamo(Integer.valueOf(txtCodPre.getText()));
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
                JOptionPane.showMessageDialog(null, "El código ingresado no existe");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnAnularActionPerformed

    private void btnElegirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElegirCliActionPerformed
        String isbnReser = "";
        try {
            jdBuscarClienteConReservas objJd = new jdBuscarClienteConReservas(null, true);
            objJd.setLocationRelativeTo(null);
            objJd.setVisible(true);

            if (objJd.codCli != -1) {
                mostrarDatosCliente(objJd.codCli);
                ResultSet rs = objReserva.obtenerEjemplarReservado(objJd.codCli);

                if (rs.next()) {
                    isbnReser = rs.getString("isbn");
                    agregarEjemplarParaClienteReserva(isbnReser);
                } else {
                    JOptionPane.showMessageDialog(this, "Aún no esta disponible el libro reservado por el cliente", "Mensaje", JOptionPane.WARNING_MESSAGE);
                    limpiarTodo();
                    limpiarTabla();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnElegirCliActionPerformed

    private void txtCodPreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodPreKeyTyped
        // TODO add your handling code here:
        Character objTecla = evt.getKeyChar();
        if (!Character.isDigit(objTecla)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodPreKeyTyped

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

    private void btnAgregarEjem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEjem4ActionPerformed
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
    }//GEN-LAST:event_btnAgregarEjem4ActionPerformed

    private void btnEliminarEjem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEjem4ActionPerformed
        // TODO add your handling code here:
        if (tblDetalles.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un libro a eliminar del préstamo!");
        } else {
            if (JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este libro del préstamo?", "Mensaje de Sistema", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                lblCodEjem.setText("");
                lblNomEjem.setText("");
                lblEditorialEjem.setText("");
                lblISBNEjem.setText("");
                eliminarEjemplar(Integer.parseInt(tblDetalles.getValueAt(tblDetalles.getSelectedRow(), 0).toString()), Integer.parseInt(lblCodCli.getText()));
            }
        }

    }//GEN-LAST:event_btnEliminarEjem4ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        try {
            txtCodPre.setEditable(false);
            txtCodPre.setText(String.valueOf(objPrestamo.generarCodPrestamo()));
            mostrarFechaLim();
            llenarTablaInicial();
            limpiarTabla();
        } catch (Exception ex) {
            Logger.getLogger(jdTranReservaCompleta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void txtCodPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodPreActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarEjem4;
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnElegirCli;
    private javax.swing.JButton btnEliminarEjem4;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private com.toedter.calendar.JDateChooser calendarFLim;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane5;
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
