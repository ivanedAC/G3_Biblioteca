/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.clsEditorial;
import capaLogica.clsEjemplar;
import capaLogica.clsSede;
import capaLogica.clsTipoLibro;
import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ander
 */
public class jdMenuLibros extends javax.swing.JDialog {

    clsEditorial objEditorial = new clsEditorial();
    clsSede objSede = new clsSede();
    clsTipoLibro objTipoLibro = new clsTipoLibro();
    clsEjemplar objEjemplar = new clsEjemplar();
    
    public static String ISBN = "";
    /**
     * Creates new form jdMenuLibros
     */
    public jdMenuLibros(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        listarEjemplares();
    }

    private ImageIcon prestamoIcon = new ImageIcon(getClass().getResource("/recursos/prestamo_icon.png"));
    private ImageIcon reservaIcon = new ImageIcon(getClass().getResource("/recursos/reserva_icon.png"));

    private void comboEditorial() {
        DefaultComboBoxModel combito = new DefaultComboBoxModel();
        combito.addElement("--Editorial--");
        try {
            ResultSet rs = objEditorial.listarEditorial();
            while (rs.next()) {
                combito.addElement(rs.getString("nombre"));
            }
            cboEditorial.setModel(combito);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void comboSede() {
        DefaultComboBoxModel combito = new DefaultComboBoxModel();
        try {
            ResultSet rs = objSede.listarSede();
            while (rs.next()) {
                combito.addElement(rs.getString("nombre"));
            }
            cboSede.setModel(combito);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void comboTipoLibro() {
        DefaultComboBoxModel combito = new DefaultComboBoxModel();
        combito.addElement("--Tipo--");
        try {
            ResultSet rs = objTipoLibro.listarTipoLibro();
            while (rs.next()) {
                combito.addElement(rs.getString("nombre"));
            }
            cboTipo.setModel(combito);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

//    private void listarEjemplares(){
//        ResultSet rs = null;
//        DefaultTableModel modelito = new DefaultTableModel();
//        modelito.addColumn("ISBN");
//        modelito.addColumn("Titulo");
//        modelito.addColumn("Autor");
//        modelito.addColumn("Edicioón");
//        modelito.addColumn("Editorial");
//        modelito.addColumn("Disponible");
//        modelito.addColumn("Prestado");
//        modelito.addColumn(" ");
//        modelito.addColumn(" ");
//        
//        try {
//            if (!cboEditorial.getSelectedItem().equals("--Editorial--")) {
//                if (!cboTipo.getSelectedItem().equals("--Tipo--")) {
//                    rs = objEjemplar.buscarEjemplares(
//                            txtISBN.getText(),
//                            txtTitulo.getText(),
//                            txtAutor.getText(),
//                            objEditorial.obtenerCodigoEditorial(cboEditorial.getSelectedItem().toString()), 
//                            objTipoLibro.obtenerCodigoTipoLibro(cboTipo.getSelectedItem().toString()),
//                            objSede.obtenerSede(cboSede.getSelectedItem().toString()));
//                }else{
//                    rs = objEjemplar.buscarEjemplaresEditorial(
//                            txtISBN.getText(),
//                            txtTitulo.getText(),
//                            txtAutor.getText(),
//                            objEditorial.obtenerCodigoEditorial(cboEditorial.getSelectedItem().toString()), 
//                            objSede.obtenerSede(cboSede.getSelectedItem().toString()));
//                }
//            }else{
//                if (!cboTipo.getSelectedItem().equals("--Tipo--")){
//                    rs = objEjemplar.buscarEjemplaresTipoLibro(
//                            txtISBN.getText(),
//                            txtTitulo.getText(),
//                            txtAutor.getText(),
//                            objTipoLibro.obtenerCodigoTipoLibro(cboTipo.getSelectedItem().toString()),
//                            objSede.obtenerSede(cboSede.getSelectedItem().toString()));
//                }else{
//                    rs = objEjemplar.buscarEjemplaresTexto(
//                            txtISBN.getText(),
//                            txtTitulo.getText(),
//                            txtAutor.getText(),
//                            objSede.obtenerSede(cboSede.getSelectedItem().toString()));
//                }
//            }
//            
//            while (rs.next()) {                
//                modelito.addRow(new Object[]{
//                    rs.getString("isbn"),
//                    rs.getString("libro"),
//                    rs.getString("autor"),
//                    rs.getString("edicion"),
//                    rs.getString("editorial"),
//                    rs.getInt("disponible"),
//                    rs.getInt("prestados")
//                });
//            }
//            
//            tblEjemplares.setModel(modelito);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Error en la busqueda de libros --> "+e.getMessage());
//        }
//    }
    private void listarEjemplares() {
        ResultSet rs = null;
        DefaultTableModel modelito = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que todas las celdas sean no editables
            }
        };

        modelito.addColumn("ISBN");
        modelito.addColumn("Titulo");
        modelito.addColumn("Autor");
        modelito.addColumn("Edición");
        modelito.addColumn("Editorial");
        modelito.addColumn("Disponible");
        modelito.addColumn("Prestado");
        modelito.addColumn("Reservas");
        modelito.addColumn("Prestamo");
        modelito.addColumn("Reserva");

        try {

            if (!cboEditorial.getSelectedItem().equals("--Editorial--")) {
                if (!cboTipo.getSelectedItem().equals("--Tipo--")) {
                    rs = objEjemplar.buscarEjemplares(
                            txtISBN.getText(),
                            txtTitulo.getText(),
                            txtAutor.getText(),
                            objEditorial.obtenerCodigoEditorial(cboEditorial.getSelectedItem().toString()),
                            objTipoLibro.obtenerCodigoTipoLibro(cboTipo.getSelectedItem().toString()),
                            objSede.obtenerSede(cboSede.getSelectedItem().toString()));
                } else {
                    rs = objEjemplar.buscarEjemplaresEditorial(
                            txtISBN.getText(),
                            txtTitulo.getText(),
                            txtAutor.getText(),
                            objEditorial.obtenerCodigoEditorial(cboEditorial.getSelectedItem().toString()),
                            objSede.obtenerSede(cboSede.getSelectedItem().toString()));
                }
            } else {
                if (!cboTipo.getSelectedItem().equals("--Tipo--")) {
                    rs = objEjemplar.buscarEjemplaresTipoLibro(
                            txtISBN.getText(),
                            txtTitulo.getText(),
                            txtAutor.getText(),
                            objTipoLibro.obtenerCodigoTipoLibro(cboTipo.getSelectedItem().toString()),
                            objSede.obtenerSede(cboSede.getSelectedItem().toString()));
                } else {
                    rs = objEjemplar.buscarEjemplaresTexto(
                            txtISBN.getText(),
                            txtTitulo.getText(),
                            txtAutor.getText(),
                            objSede.obtenerSede(cboSede.getSelectedItem().toString()));
                }
            }

            while (rs.next()) {
                modelito.addRow(new Object[]{
                    rs.getString("isbn"),
                    rs.getString("libro"),
                    rs.getString("autor"),
                    rs.getString("edicion"),
                    rs.getString("editorial"),
                    rs.getInt("disponible"),
                    rs.getInt("prestados"),
                    rs.getInt("reservas"),
                    "prestamo", // Indicador para el ícono de préstamo
                    "reserva" // Indicador para el ícono de reserva
                });
            }

            tblEjemplares.setModel(modelito);

            // Asigna el renderer a las columnas correspondientes
            tblEjemplares.getColumnModel().getColumn(8).setCellRenderer(new IconCellRenderer(prestamoIcon));
            tblEjemplares.getColumnModel().getColumn(9).setCellRenderer(new IconCellRenderer(reservaIcon));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en la búsqueda de libros --> " + e.getMessage());
        }
    }

    // Clase para el renderer de celdas
    class IconCellRenderer extends DefaultTableCellRenderer {

        private final ImageIcon icon;

        public IconCellRenderer(ImageIcon icon) {
            this.icon = icon;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
            JLabel label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER); // Centra el ícono
            label.setIcon(icon);
            if (isSelected) {
                label.setBackground(table.getSelectionBackground());
                label.setOpaque(true);
            }
            return label;
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtISBN = new javax.swing.JTextField();
        txtTitulo = new javax.swing.JTextField();
        txtAutor = new javax.swing.JTextField();
        cboEditorial = new javax.swing.JComboBox<>();
        cboSede = new javax.swing.JComboBox<>();
        cboTipo = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEjemplares = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("ISBN:");

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Titulo:");

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Autor:");

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Editorial:");

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Sede:");

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Tipo:");

        txtISBN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtISBNKeyReleased(evt);
            }
        });

        txtTitulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTituloKeyReleased(evt);
            }
        });

        txtAutor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAutorKeyReleased(evt);
            }
        });

        cboEditorial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboEditorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEditorialActionPerformed(evt);
            }
        });

        cboSede.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSedeActionPerformed(evt);
            }
        });

        cboTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtISBN)
                    .addComponent(txtAutor)
                    .addComponent(cboSede, 0, 330, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(370, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(cboEditorial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cboSede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setForeground(new java.awt.Color(204, 204, 204));

        tblEjemplares.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblEjemplares.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEjemplaresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEjemplares);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1206, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        comboEditorial();
        comboSede();
        comboTipoLibro();
        listarEjemplares();
    }//GEN-LAST:event_formWindowOpened

    private void cboEditorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEditorialActionPerformed
        // TODO add your handling code here:
        listarEjemplares();
    }//GEN-LAST:event_cboEditorialActionPerformed

    private void cboSedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSedeActionPerformed
        // TODO add your handling code here:
        listarEjemplares();
    }//GEN-LAST:event_cboSedeActionPerformed

    private void cboTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoActionPerformed
        // TODO add your handling code here:
        listarEjemplares();
    }//GEN-LAST:event_cboTipoActionPerformed

    private void txtISBNKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtISBNKeyReleased
        // TODO add your handling code here:
        listarEjemplares();
    }//GEN-LAST:event_txtISBNKeyReleased

    private void txtTituloKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTituloKeyReleased
        // TODO add your handling code here:
        listarEjemplares();
    }//GEN-LAST:event_txtTituloKeyReleased

    private void txtAutorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAutorKeyReleased
        // TODO add your handling code here:
        listarEjemplares();
    }//GEN-LAST:event_txtAutorKeyReleased

    private void tblEjemplaresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEjemplaresMouseClicked
        // TODO add your handling code here:
        ISBN = String.valueOf(tblEjemplares.getValueAt(tblEjemplares.getSelectedRow(), 0));
        int dispo = (int) tblEjemplares.getValueAt(tblEjemplares.getSelectedRow(), 5);
        int prest = (int) tblEjemplares.getValueAt(tblEjemplares.getSelectedRow(), 6);
        int reser = (int) tblEjemplares.getValueAt(tblEjemplares.getSelectedRow(), 7); 
        
        if (dispo == 0) {
            if (JOptionPane.showConfirmDialog(this, "No hay ejemplares disponibles \n ¿Desea realizar una reserva?",
                    "Sin ejemplares", JOptionPane.YES_NO_OPTION)==0) {
                if (reser > prest) {
                    JOptionPane.showMessageDialog(this, "Límite de reservas", "No quedan mas ejemplares para reservas", JOptionPane.OK_OPTION);
                }else{
                    jdTranReserva objReserva = new jdTranReserva(null, true);
                    objReserva.setLocationRelativeTo(null);
                    objReserva.setVisible(true);
                    
                }
            }
        } else {
            if (JOptionPane.showConfirmDialog(this, "Hay ejemplares disponibles \n ¿Desea realizar un prestamo?",
                    "Ejemplares disponibles", JOptionPane.YES_NO_OPTION)==0) {
                jdTranPrestamo objPrestamo = new jdTranPrestamo(null, true);
                objPrestamo.setLocationRelativeTo(null);
                objPrestamo.setVisible(true);
                
            }
        }
        System.out.println(ISBN);
    }//GEN-LAST:event_tblEjemplaresMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        comboEditorial();
        comboSede();
        comboTipoLibro();
        listarEjemplares();
    }//GEN-LAST:event_formWindowActivated

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboEditorial;
    private javax.swing.JComboBox<String> cboSede;
    private javax.swing.JComboBox<String> cboTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEjemplares;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
