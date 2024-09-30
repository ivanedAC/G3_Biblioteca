/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package capaCliente;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author ander
 */
public class FrmMenuPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form MenuPrincipal
     */
    public FrmMenuPrincipal() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        FondoPanel fondo = new FondoPanel();  // Crear el fondo
        pnlFondo.setLayout(new BorderLayout());  // Mantén el layout que necesites

        pnlFondo.add(fondo, BorderLayout.CENTER);  // Agregar el panel de fondo dentro del panel diseñado
        pnlFondo.revalidate();  // Refresca el panel
        pnlFondo.repaint();     // Fuerza la actualización del fondo
    }

    public class FondoPanel extends JPanel {

        private Image imagen;

        public FondoPanel() {
            // Ruta de la imagen (puedes ajustarla según tu directorio)
            imagen = new ImageIcon(getClass().getResource("/recursos/fondo_biblioteca.jpeg")).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dibujar la imagen en el panel, ajustada al tamaño del panel
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
    public void sinBotones(){
        btnLibro.setVisible(false);
        btnAutor.setVisible(false);
        btnEjemplar.setVisible(false);
        btnCliente.setVisible(false);
    }

    public void botonesMantenimiento(){
        btnLibro.setVisible(true);
        btnAutor.setVisible(true);
        btnEjemplar.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnAutor = new javax.swing.JButton();
        btnLibro = new javax.swing.JButton();
        btnEjemplar = new javax.swing.JButton();
        btnCliente = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pnlFondo = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnuSalir = new javax.swing.JMenuItem();
        mnuMantenimientos = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        mnuReportes = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jToolBar1.setBackground(new java.awt.Color(230, 182, 139));
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);
        jToolBar1.setEnabled(false);

        btnAutor.setBackground(new java.awt.Color(230, 182, 139));
        btnAutor.setForeground(new java.awt.Color(113, 49, 18));
        btnAutor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/autor (1).png"))); // NOI18N
        btnAutor.setText("AUTOR");
        btnAutor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(113, 49, 18), 2, true));
        btnAutor.setHideActionText(true);
        btnAutor.setMaximumSize(new java.awt.Dimension(190, 50));
        btnAutor.setMinimumSize(new java.awt.Dimension(190, 50));
        jToolBar1.add(btnAutor);

        btnLibro.setBackground(new java.awt.Color(230, 182, 139));
        btnLibro.setForeground(new java.awt.Color(113, 49, 18));
        btnLibro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/libro(1).png"))); // NOI18N
        btnLibro.setText("LIBRO");
        btnLibro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(113, 49, 18), 2, true));
        btnLibro.setFocusCycleRoot(true);
        btnLibro.setFocusable(false);
        btnLibro.setHideActionText(true);
        btnLibro.setMaximumSize(new java.awt.Dimension(190, 50));
        btnLibro.setMinimumSize(new java.awt.Dimension(190, 50));
        btnLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLibroActionPerformed(evt);
            }
        });
        jToolBar1.add(btnLibro);

        btnEjemplar.setBackground(new java.awt.Color(230, 182, 139));
        btnEjemplar.setForeground(new java.awt.Color(113, 49, 18));
        btnEjemplar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/ejemplar (1).png"))); // NOI18N
        btnEjemplar.setText("EJEMPLAR");
        btnEjemplar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(113, 49, 18), 2, true));
        btnEjemplar.setFocusable(false);
        btnEjemplar.setHideActionText(true);
        btnEjemplar.setMaximumSize(new java.awt.Dimension(190, 50));
        btnEjemplar.setMinimumSize(new java.awt.Dimension(190, 50));
        btnEjemplar.setPreferredSize(new java.awt.Dimension(130, 50));
        btnEjemplar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjemplarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEjemplar);

        btnCliente.setBackground(new java.awt.Color(230, 182, 139));
        btnCliente.setForeground(new java.awt.Color(113, 49, 18));
        btnCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/ejemplar (1).png"))); // NOI18N
        btnCliente.setText("CLIENTE");
        btnCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(113, 49, 18), 2, true));
        btnCliente.setFocusable(false);
        btnCliente.setHideActionText(true);
        btnCliente.setMaximumSize(new java.awt.Dimension(190, 50));
        btnCliente.setMinimumSize(new java.awt.Dimension(190, 50));
        btnCliente.setPreferredSize(new java.awt.Dimension(130, 50));
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCliente);

        jPanel1.setBackground(new java.awt.Color(113, 49, 18));

        jLabel1.setFont(new java.awt.Font("Courier New", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(230, 182, 139));
        jLabel1.setText("Biblioteca Ricardo Palma");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 729, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(355, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(245, 224, 206));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 71, Short.MAX_VALUE)
        );

        pnlFondo.setBackground(new java.awt.Color(153, 255, 255));

        javax.swing.GroupLayout pnlFondoLayout = new javax.swing.GroupLayout(pnlFondo);
        pnlFondo.setLayout(pnlFondoLayout);
        pnlFondoLayout.setHorizontalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 946, Short.MAX_VALUE)
        );
        pnlFondoLayout.setVerticalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
        );

        jMenuBar1.setBackground(new java.awt.Color(245, 224, 206));

        jMenu1.setText("Login");

        mnuSalir.setText("Salir");
        mnuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalirActionPerformed(evt);
            }
        });
        jMenu1.add(mnuSalir);

        jMenuBar1.add(jMenu1);

        mnuMantenimientos.setText("Mantenimiento");
        mnuMantenimientos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuMantenimientosMouseClicked(evt);
            }
        });

        jMenuItem4.setText("Usuario");
        mnuMantenimientos.add(jMenuItem4);

        jMenuItem1.setText("Autor");
        mnuMantenimientos.add(jMenuItem1);

        jMenuItem3.setText("Libro");
        mnuMantenimientos.add(jMenuItem3);

        jMenuBar1.add(mnuMantenimientos);

        mnuReportes.setText("Reportes");
        mnuReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnuReportesMouseClicked(evt);
            }
        });
        jMenuBar1.add(mnuReportes);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEjemplarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjemplarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEjemplarActionPerformed

    private void mnuMantenimientosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuMantenimientosMouseClicked
        botonesMantenimiento();
    }//GEN-LAST:event_mnuMantenimientosMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        sinBotones();
        jdIniciarSesion frmLogin = new jdIniciarSesion(this, true);
        frmLogin.setLocationRelativeTo(null);
        frmLogin.setVisible(true);
    }//GEN-LAST:event_formWindowOpened

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClienteActionPerformed

    private void mnuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalirActionPerformed
        //this.dispose();
        System.exit(0);
    }//GEN-LAST:event_mnuSalirActionPerformed

    private void mnuReportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuReportesMouseClicked
        sinBotones();
    }//GEN-LAST:event_mnuReportesMouseClicked

    private void btnLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLibroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLibroActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAutor;
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnEjemplar;
    private javax.swing.JButton btnLibro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenu mnuMantenimientos;
    private javax.swing.JMenu mnuReportes;
    private javax.swing.JMenuItem mnuSalir;
    private javax.swing.JPanel pnlFondo;
    // End of variables declaration//GEN-END:variables

}
