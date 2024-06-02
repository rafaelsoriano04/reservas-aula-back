package gestorreservasaulas.interfaces;

import gestorreservasaulas.servicios.ServicioUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FrmLogin extends javax.swing.JFrame {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    private FrmPrincipal frmPrincipal;

    public FrmLogin() {
        initComponents();
        setVisible(true);
        setLocationRelativeTo(null);
        lbMensaje.setVisible(false);
        imgVisible.setVisible(false);
    }

    private void validarLogin() {

        if (servicioUsuario.validarUsuario(txtUsername.getText(), new String(txtContrasenia.getPassword()))) {
            frmPrincipal.setVisible(true);
            frmPrincipal.setLocationRelativeTo(null);
            this.dispose();
        } else {
            lbMensaje.setVisible(true);
        }

    }

    private void ocultarContrasenia() {
        txtContrasenia.setEchoChar((char) 0);
        imgVisible.setVisible(true);
        imgOculto.setVisible(false);
    }

    private void mostrarContrasenia() {
        txtContrasenia.setEchoChar('\u2022');
        imgVisible.setVisible(false);
        imgOculto.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtUsername = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        lbMensaje = new javax.swing.JLabel();
        imgVisible = new javax.swing.JLabel();
        imgOculto = new javax.swing.JLabel();
        txtContrasenia = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1280, 701));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtUsername.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        txtUsername.setBorder(null);
        getContentPane().add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, 310, 30));

        btnLogin.setBackground(new java.awt.Color(155, 43, 43));
        btnLogin.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Iniciar sesión");
        btnLogin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLogin.setOpaque(true);
        btnLogin.setSelected(true);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 540, 350, 50));

        lbMensaje.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbMensaje.setForeground(new java.awt.Color(255, 51, 51));
        lbMensaje.setText("Usuario o contraseña incorrectos. Inténtelo de nuevo.");
        getContentPane().add(lbMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 620, 340, -1));

        imgVisible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-visible-24.png"))); // NOI18N
        imgVisible.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgVisibleMouseClicked(evt);
            }
        });
        getContentPane().add(imgVisible, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 430, 30, 30));

        imgOculto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-ocultar-24.png"))); // NOI18N
        imgOculto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgOcultoMouseClicked(evt);
            }
        });
        getContentPane().add(imgOculto, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 430, 30, 30));

        txtContrasenia.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        txtContrasenia.setBorder(null);
        getContentPane().add(txtContrasenia, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 430, 260, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/login.png"))); // NOI18N
        jLabel3.setOpaque(true);
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        validarLogin();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void imgVisibleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgVisibleMouseClicked
        mostrarContrasenia();
    }//GEN-LAST:event_imgVisibleMouseClicked

    private void imgOcultoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgOcultoMouseClicked
        ocultarContrasenia();
    }//GEN-LAST:event_imgOcultoMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel imgOculto;
    private javax.swing.JLabel imgVisible;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lbMensaje;
    private javax.swing.JPasswordField txtContrasenia;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
