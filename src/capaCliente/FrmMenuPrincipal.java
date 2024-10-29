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

    jdIniciarSesion frmLogin = new jdIniciarSesion(this, true);

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

    public void accesoRe() {
        //MnuItems : Acceso
        MnuCliente.setVisible(true);
        //Btn : Acceso
        btnCliente.setVisible(true);
        //MnuItems : No acceso
        MnuEjemplar.setVisible(false);
        MnuUsuario.setVisible(false);
        MnuAutor.setVisible(false);
        MnuContraseña.setVisible(false);
        MnuLibro.setVisible(false);
        MnuCategoria.setVisible(false);
        MnuProveedor.setVisible(false);
        //Btn : No Acceso
        btnLibro.setVisible(false);
        btnEjemplar.setVisible(false);
        btnEditorial.setVisible(false);
        btnAutor.setVisible(false);
    }

    public void accesoSuper() {
        //MnuItems : Acceso
        MnuLibro.setVisible(true);
        MnuEjemplar.setVisible(true);
        MnuProveedor.setVisible(true);
        MnuCategoria.setVisible(true);
        //Btn : Acceso
        btnLibro.setVisible(true);
        btnEjemplar.setVisible(true);
        btnCategoria.setVisible(true);
        btnProveedor.setVisible(true);
        //MnuItems : No Acceso
        MnuCliente.setVisible(false);
        MnuUsuario.setVisible(false);
        MnuAutor.setVisible(false);
        MnuContraseña.setVisible(false);
        //Btn : No Acceso
        btnEditorial.setVisible(false);
        btnAutor.setVisible(false);
        btnCliente.setVisible(false);

    }

    public void sinBotones() {
        btnEditorial.setVisible(false);
        btnLibro.setVisible(false);
        btnAutor.setVisible(false);
        btnEjemplar.setVisible(false);
        btnCliente.setVisible(false);
        btnProveedor.setVisible(false);
        btnCategoria.setVisible(false);
    }

    public void botonesMantenimiento() {
        btnEditorial.setVisible(true);
        btnLibro.setVisible(true);
        btnAutor.setVisible(true);
        btnEjemplar.setVisible(true);
        btnCliente.setVisible(true);
        btnProveedor.setVisible(true);
        btnCategoria.setVisible(true);

    }

    public void botonesReportes() {
        btnCliente.setVisible(true);
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
        btnEditorial = new javax.swing.JButton();
        btnCategoria = new javax.swing.JButton();
        btnProveedor = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblUser = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblCargo = new javax.swing.JLabel();
        pnlFondo = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnuSalir = new javax.swing.JMenuItem();
        mnuMantenimientos = new javax.swing.JMenu();
        MnuUsuario = new javax.swing.JMenuItem();
        MnuContraseña = new javax.swing.JMenuItem();
        MnuAutor = new javax.swing.JMenuItem();
        MnuLibro = new javax.swing.JMenuItem();
        MnuCliente = new javax.swing.JMenuItem();
        MnuCategoria = new javax.swing.JMenuItem();
        MnuProveedor = new javax.swing.JMenuItem();
        MnuEjemplar = new javax.swing.JMenuItem();
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
        btnAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutorActionPerformed(evt);
            }
        });
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
        btnCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/cliente(1).png"))); // NOI18N
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

        btnEditorial.setBackground(new java.awt.Color(230, 182, 139));
        btnEditorial.setForeground(new java.awt.Color(113, 49, 18));
        btnEditorial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/editorial(1).png"))); // NOI18N
        btnEditorial.setText("Editorial");
        btnEditorial.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(113, 49, 18), 2, true));
        btnEditorial.setFocusable(false);
        btnEditorial.setHideActionText(true);
        btnEditorial.setMaximumSize(new java.awt.Dimension(190, 50));
        btnEditorial.setMinimumSize(new java.awt.Dimension(190, 50));
        btnEditorial.setPreferredSize(new java.awt.Dimension(130, 50));
        btnEditorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditorialActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEditorial);

        btnCategoria.setBackground(new java.awt.Color(230, 182, 139));
        btnCategoria.setForeground(new java.awt.Color(113, 49, 18));
        btnCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/categoria(1).png"))); // NOI18N
        btnCategoria.setText("CATEGORIA");
        btnCategoria.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(113, 49, 18), 2, true));
        btnCategoria.setFocusable(false);
        btnCategoria.setHideActionText(true);
        btnCategoria.setMaximumSize(new java.awt.Dimension(190, 50));
        btnCategoria.setMinimumSize(new java.awt.Dimension(190, 50));
        btnCategoria.setPreferredSize(new java.awt.Dimension(130, 50));
        btnCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriaActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCategoria);

        btnProveedor.setBackground(new java.awt.Color(230, 182, 139));
        btnProveedor.setForeground(new java.awt.Color(113, 49, 18));
        btnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/proveedor(1).png"))); // NOI18N
        btnProveedor.setText("Proveedor");
        btnProveedor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(113, 49, 18), 2, true));
        btnProveedor.setFocusable(false);
        btnProveedor.setHideActionText(true);
        btnProveedor.setMaximumSize(new java.awt.Dimension(190, 50));
        btnProveedor.setMinimumSize(new java.awt.Dimension(190, 50));
        btnProveedor.setPreferredSize(new java.awt.Dimension(130, 50));
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });
        jToolBar1.add(btnProveedor);

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

        lblUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("Usuario:");

        jLabel4.setText("Cargo:");

        lblCargo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(jLabel3)
                .addGap(34, 34, 34)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(lblCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(19, 19, 19)))))
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
            .addGap(0, 531, Short.MAX_VALUE)
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

        MnuUsuario.setText("Usuario");
        MnuUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnuUsuarioActionPerformed(evt);
            }
        });
        mnuMantenimientos.add(MnuUsuario);

        MnuContraseña.setText("Contraseña");
        MnuContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnuContraseñaActionPerformed(evt);
            }
        });
        mnuMantenimientos.add(MnuContraseña);

        MnuAutor.setText("Autor");
        MnuAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnuAutorActionPerformed(evt);
            }
        });
        mnuMantenimientos.add(MnuAutor);

        MnuLibro.setText("Libro");
        MnuLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnuLibroActionPerformed(evt);
            }
        });
        mnuMantenimientos.add(MnuLibro);

        MnuCliente.setText("Cliente");
        MnuCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnuClienteActionPerformed(evt);
            }
        });
        mnuMantenimientos.add(MnuCliente);

        MnuCategoria.setText("Categoria");
        MnuCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnuCategoriaActionPerformed(evt);
            }
        });
        mnuMantenimientos.add(MnuCategoria);

        MnuProveedor.setText("Proveedor");
        MnuProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnuProveedorActionPerformed(evt);
            }
        });
        mnuMantenimientos.add(MnuProveedor);

        MnuEjemplar.setText("Ejemplar");
        MnuEjemplar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnuEjemplarActionPerformed(evt);
            }
        });
        mnuMantenimientos.add(MnuEjemplar);

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
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEjemplarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjemplarActionPerformed
        jdManEjemplar objeto = new jdManEjemplar(this, true);
        objeto.setLocationRelativeTo(this);
        objeto.setVisible(true);
    }//GEN-LAST:event_btnEjemplarActionPerformed

    private void mnuMantenimientosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuMantenimientosMouseClicked

    }//GEN-LAST:event_mnuMantenimientosMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        sinBotones();
        frmLogin.setLocationRelativeTo(null);
        frmLogin.setVisible(true);
        lblUser.setText(frmLogin.us1);
        lblCargo.setText(frmLogin.cargo);
        switch (lblCargo.getText()) {
            case "Recepcionista":
                accesoRe();
                break;
            case "Supervisor":
                accesoSuper();
                break;
            case "Administrador":
                botonesMantenimiento();
                botonesReportes();
            default:
                throw new AssertionError();
        }
    }//GEN-LAST:event_formWindowOpened

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        jdManCliente objFm = new jdManCliente(this, true);
        objFm.setLocationRelativeTo(null);
        objFm.setVisible(true);
    }//GEN-LAST:event_btnClienteActionPerformed

    private void mnuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalirActionPerformed
        //this.dispose();
        System.exit(0);
    }//GEN-LAST:event_mnuSalirActionPerformed

    private void mnuReportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuReportesMouseClicked
        sinBotones();
        botonesReportes();
    }//GEN-LAST:event_mnuReportesMouseClicked

    private void MnuUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnuUsuarioActionPerformed
        // TODO add your handling code here:
        jdManUsuario objFrm = new jdManUsuario(this, true);
        objFrm.setVisible(true);
    }//GEN-LAST:event_MnuUsuarioActionPerformed
    private void btnLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLibroActionPerformed
        jdManLibro objFm = new jdManLibro(this, true);
        objFm.setLocationRelativeTo(null);
        objFm.setVisible(true);
    }//GEN-LAST:event_btnLibroActionPerformed

    private void MnuAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnuAutorActionPerformed
        jdManAutor jautor = new jdManAutor(this, true);
        jautor.setLocationRelativeTo(null);
        jautor.setVisible(true);
    }//GEN-LAST:event_MnuAutorActionPerformed

    private void MnuClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnuClienteActionPerformed
        jdManCliente objCliente = new jdManCliente(this, true);
        objCliente.setLocationRelativeTo(null);
        objCliente.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_MnuClienteActionPerformed
    private void btnAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutorActionPerformed
        jdManAutor objFm = new jdManAutor(this, true);
        objFm.setLocationRelativeTo(null);
        objFm.setVisible(true);
    }//GEN-LAST:event_btnAutorActionPerformed

    private void MnuCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnuCategoriaActionPerformed
        jdManCategoria jcategoria = new jdManCategoria(this, true);
        jcategoria.setLocationRelativeTo(null);
        jcategoria.setVisible(true);
    }//GEN-LAST:event_MnuCategoriaActionPerformed

    private void MnuProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnuProveedorActionPerformed
        jdManProveedor jProveedor = new jdManProveedor(this, true);
        jProveedor.setLocationRelativeTo(null);
        jProveedor.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_MnuProveedorActionPerformed

    private void MnuLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnuLibroActionPerformed
        // TODO add your handling code here:
        jdManLibro jLibro = new jdManLibro(this, false);
        jLibro.setLocationRelativeTo(null);
        jLibro.setVisible(true);
    }//GEN-LAST:event_MnuLibroActionPerformed

    private void MnuContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnuContraseñaActionPerformed
        // TODO add your handling code here:
        jdManPass objFm = new jdManPass(this, true);
        objFm.setLocationRelativeTo(null);
        objFm.setVisible(true);
    }//GEN-LAST:event_MnuContraseñaActionPerformed

    private void btnEditorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditorialActionPerformed
        jdManEditorial objFm = new jdManEditorial(this, true);
        objFm.setLocationRelativeTo(null);
        objFm.setVisible(true);
    }//GEN-LAST:event_btnEditorialActionPerformed

    private void MnuEjemplarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnuEjemplarActionPerformed
        jdManEjemplar objFm = new jdManEjemplar(this, true);
        objFm.setLocationRelativeTo(null);
        objFm.setVisible(true);
    }//GEN-LAST:event_MnuEjemplarActionPerformed

    private void btnCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriaActionPerformed
        jdManCategoria objFm = new jdManCategoria(this, true);
        objFm.setLocationRelativeTo(null);
        objFm.setVisible(true);
    }//GEN-LAST:event_btnCategoriaActionPerformed

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
        jdManProveedor objFm = new jdManProveedor(this, true);
        objFm.setLocationRelativeTo(null);
        objFm.setVisible(true);
    }//GEN-LAST:event_btnProveedorActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MnuAutor;
    private javax.swing.JMenuItem MnuCategoria;
    private javax.swing.JMenuItem MnuCliente;
    private javax.swing.JMenuItem MnuContraseña;
    private javax.swing.JMenuItem MnuEjemplar;
    private javax.swing.JMenuItem MnuLibro;
    private javax.swing.JMenuItem MnuProveedor;
    private javax.swing.JMenuItem MnuUsuario;
    private javax.swing.JButton btnAutor;
    private javax.swing.JButton btnCategoria;
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnEditorial;
    private javax.swing.JButton btnEjemplar;
    private javax.swing.JButton btnLibro;
    private javax.swing.JButton btnProveedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblCargo;
    private javax.swing.JLabel lblUser;
    private javax.swing.JMenu mnuMantenimientos;
    private javax.swing.JMenu mnuReportes;
    private javax.swing.JMenuItem mnuSalir;
    private javax.swing.JPanel pnlFondo;
    // End of variables declaration//GEN-END:variables

}
