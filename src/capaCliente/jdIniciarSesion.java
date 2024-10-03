/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package capaCliente;

import capaLogica.clsUsuario;
import javax.swing.JOptionPane;
import java.sql.ResultSet;

/**
 *
 * @author ander
 */
public class jdIniciarSesion extends javax.swing.JDialog {

    public String us = "";
    public String us1 = "";
    public String cargo = "";

    /**
     * Creates new form jdIniciarSesion
     */
    public String generarCaptcha() {
        StringBuilder cap = new StringBuilder();
        int random;

        for (int i = 0; i < 5; i++) {
            random = (int) (Math.random() * 10);
            cap.append(random);
        }

        for (int i = 0; i < 2; i++) {
            random = (int) (Math.random() * 12) + 65;
            cap.append((char) random);
        }

        System.out.println(cap.toString());
        return cap.toString();
    }

    public jdIniciarSesion(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

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
        btnEnviarDatos = new javax.swing.JButton();
        txtUsuario = new javax.swing.JTextField();
        txtContrasena = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        lblCaptcha = new javax.swing.JLabel();
        btnCambiarCaptcha = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCaptcha = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Iniciar Sesión");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(230, 182, 139));

        jLabel1.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(113, 49, 18));
        jLabel1.setText("Usuario:");

        jLabel2.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(113, 49, 18));
        jLabel2.setText("Contraseña:");

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/login_fondo_biblio.jpg"))); // NOI18N

        btnEnviarDatos.setBackground(new java.awt.Color(113, 49, 18));
        btnEnviarDatos.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        btnEnviarDatos.setForeground(new java.awt.Color(230, 182, 139));
        btnEnviarDatos.setText("ENVIAR");
        btnEnviarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarDatosActionPerformed(evt);
            }
        });

        txtUsuario.setBackground(new java.awt.Color(255, 215, 171));
        txtUsuario.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(113, 49, 18));
        txtUsuario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtContrasena.setBackground(new java.awt.Color(255, 215, 171));
        txtContrasena.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtContrasena.setForeground(new java.awt.Color(113, 49, 18));
        txtContrasena.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setFont(new java.awt.Font("Courier New", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(113, 49, 18));
        jLabel4.setText("Captcha:");

        lblCaptcha.setBackground(new java.awt.Color(255, 215, 171));
        lblCaptcha.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        lblCaptcha.setForeground(new java.awt.Color(0, 0, 0));
        lblCaptcha.setText("\"Captcha\"");
        lblCaptcha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnCambiarCaptcha.setBackground(new java.awt.Color(113, 49, 18));
        btnCambiarCaptcha.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        btnCambiarCaptcha.setForeground(new java.awt.Color(230, 182, 139));
        btnCambiarCaptcha.setText("Cambiar");
        btnCambiarCaptcha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarCaptchaActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/login_candado.jpg"))); // NOI18N

        txtCaptcha.setBackground(new java.awt.Color(255, 215, 171));
        txtCaptcha.setFont(new java.awt.Font("Courier New", 0, 20)); // NOI18N
        txtCaptcha.setForeground(new java.awt.Color(113, 49, 18));
        txtCaptcha.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel6))
                    .addComponent(txtUsuario)
                    .addComponent(jLabel2)
                    .addComponent(txtContrasena)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(btnCambiarCaptcha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtCaptcha, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCaptcha, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnviarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(btnCambiarCaptcha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCaptcha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtCaptcha, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(btnEnviarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCambiarCaptchaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarCaptchaActionPerformed
        // TODO add your handling code here:
        lblCaptcha.setText(generarCaptcha());
        txtCaptcha.setText("");
    }//GEN-LAST:event_btnCambiarCaptchaActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        lblCaptcha.setText(generarCaptcha());
    }//GEN-LAST:event_formWindowOpened

    private void btnEnviarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarDatosActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        boolean vacio = txtUsuario.getText().isBlank() || txtContrasena.getText().isBlank() || txtCaptcha.getText().isBlank();
        clsUsuario objUsuario = new clsUsuario();
        ResultSet rs_login = null;
        ResultSet rs_datos = null;
        if (!vacio) {
            if (txtCaptcha.getText().equals(lblCaptcha.getText())) {
                try {
                    rs_login = objUsuario.login(txtUsuario.getText(), txtContrasena.getText());
                    if (rs_login.next()) {
                        us = rs_login.getString("usuario");
                        rs_datos = objUsuario.obtenerData(us);
                        if (rs_datos.next()) {
                            us1 = rs_datos.getString("nombre_completo");
                            cargo = rs_datos.getString("cargo");
                        }
                    }
                    if (!us.equals("")) {
                        if (objUsuario.validar_vigencia(rs_login.getInt("codigo")) == 0) {
                            JOptionPane.showMessageDialog(this, "Bienvenido al sistema " + us);
                            this.dispose();
                        } else if (objUsuario.validar_vigencia(rs_login.getInt("codigo")) == 1) {
                            JOptionPane.showMessageDialog(this, "Este usuario no tiene permitido el acceso " + us);
                        } else {
                            JOptionPane.showMessageDialog(this, "Ocurrio un problema al validar el usuario");
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al intentar iniciar sesion: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error al ingresar el captcha");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ningun campo debe estar vacío", "Campos vacios", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnEnviarDatosActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiarCaptcha;
    private javax.swing.JButton btnEnviarDatos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCaptcha;
    private javax.swing.JTextField txtCaptcha;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
