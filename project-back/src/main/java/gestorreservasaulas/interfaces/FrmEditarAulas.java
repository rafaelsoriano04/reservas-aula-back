//package gestorreservasaulas.interfaces;
//
//import gestorreservasaulas.entidades.Aula;
//import gestorreservasaulas.entidades.Bloque;
//import gestorreservasaulas.servicios.*;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import jakarta.annotation.PostConstruct;
//
//import javax.swing.*;
//
//@Component
//public class FrmEditarAulas extends javax.swing.JFrame {
//
//    private Aula aulas;
//
//    @Autowired
//    private ServicioBloque servicioBloque;
//
//    private List<Bloque> bloques;
//
//    @Autowired
//    private ServicioAula servicioaula;
//
//    public FrmEditarAulas() {
//        initComponents();
//    }
//
//    @PostConstruct
//    public void iniciar() {
//        setLocationRelativeTo(null);
//        combo();
//        txtid.setEditable(false);
//    }
//
//    public void combo() {
//        bloques = servicioBloque.obtenerTodosBloques();
//        for (Bloque block : bloques) {
//            jcbxBloque.addItem(block);
//        }
//    }
//
//    public void limpiar() {
//        txtid.setText("");
//        txtnombre.setText("");
//        txtpiso.setText("");
//        txtcapacidad.setText("");
//    }
//
//    public void LlevaraAula(Aula aula) {
//        this.aulas = aula;
//        txtid.setText(String.valueOf(aula.getId()));
//        txtnombre.setText(aula.getNombre());
//        txtpiso.setText(String.valueOf(aula.getPiso()));
//        txtcapacidad.setText(String.valueOf(aula.getCapacidad()));
//
//        jcbxBloque.setSelectedItem(aula.getBloque());
//    }
//
//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//    private void initComponents() {
//
//        jLabel2 = new javax.swing.JLabel();
//        jLabel3 = new javax.swing.JLabel();
//        jLabel4 = new javax.swing.JLabel();
//        jLabel5 = new javax.swing.JLabel();
//        jLabel7 = new javax.swing.JLabel();
//        btnGuardar = new javax.swing.JButton();
//        txtnombre = new javax.swing.JTextField();
//        txtcapacidad = new javax.swing.JTextField();
//        txtpiso = new javax.swing.JTextField();
//        jcbxBloque = new javax.swing.JComboBox<>();
//        btnregresar = new javax.swing.JButton();
//        jLabel8 = new javax.swing.JLabel();
//        jLabel9 = new javax.swing.JLabel();
//        txtid = new javax.swing.JTextField();
//        jLabel6 = new javax.swing.JLabel();
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
//
//        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 48)); // NOI18N
//        jLabel2.setText("Modificar Aula");
//        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, 343, 50));
//
//        jLabel3.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
//        jLabel3.setText("Capacidad");
//        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 130, 30));
//
//        jLabel4.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
//        jLabel4.setText("Bloque");
//        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 260, 90, 30));
//
//        jLabel5.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
//        jLabel5.setText("Piso");
//        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, 90, 30));
//
//        jLabel7.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
//        jLabel7.setText("ID");
//        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 90, 30));
//
//        btnGuardar.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
//        btnGuardar.setText("Guardar");
//        btnGuardar.setBorderPainted(false);
//        btnGuardar.setContentAreaFilled(false);
//        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnGuardarActionPerformed(evt);
//            }
//        });
//        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 450, 210, 40));
//
//        txtnombre.setBorder(null);
//        txtnombre.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                txtnombreActionPerformed(evt);
//            }
//        });
//        getContentPane().add(txtnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 320, 210, -1));
//
//        txtcapacidad.setBorder(null);
//        txtcapacidad.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                txtcapacidadActionPerformed(evt);
//            }
//        });
//        getContentPane().add(txtcapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, 100, -1));
//
//        txtpiso.setBorder(null);
//        txtpiso.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                txtpisoActionPerformed(evt);
//            }
//        });
//        getContentPane().add(txtpiso, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 370, 100, 20));
//
//        jcbxBloque.addItemListener(new java.awt.event.ItemListener() {
//            public void itemStateChanged(java.awt.event.ItemEvent evt) {
//                jcbxBloqueItemStateChanged(evt);
//            }
//        });
//        jcbxBloque.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                jcbxBloqueMouseClicked(evt);
//            }
//        });
//        jcbxBloque.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jcbxBloqueActionPerformed(evt);
//            }
//        });
//        getContentPane().add(jcbxBloque, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 310, 130, -1));
//
//        btnregresar.setBorderPainted(false);
//        btnregresar.setContentAreaFilled(false);
//        btnregresar.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnregresarActionPerformed(evt);
//            }
//        });
//        getContentPane().add(btnregresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 30, 30));
//
//        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/regresa.png"))); // NOI18N
//        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));
//
//        jLabel9.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
//        jLabel9.setText("Nombre");
//        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 90, 30));
//
//        txtid.setBorder(null);
//        getContentPane().add(txtid, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 280, 100, -1));
//
//        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editaraulas.jpg"))); // NOI18N
//        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 510));
//
//        pack();
//    }// </editor-fold>//GEN-END:initComponents
//
//    private void jcbxBloqueItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbxBloqueItemStateChanged
//        // TODO add your handling code here:
//    }//GEN-LAST:event_jcbxBloqueItemStateChanged
//
//    private void jcbxBloqueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbxBloqueMouseClicked
//        // TODO add your handling code here:
//    }//GEN-LAST:event_jcbxBloqueMouseClicked
//
//    private void jcbxBloqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxBloqueActionPerformed
//
//    }//GEN-LAST:event_jcbxBloqueActionPerformed
//
//    private void btnregresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregresarActionPerformed
//        this.dispose();
//    }//GEN-LAST:event_btnregresarActionPerformed
//
//    private void txtpisoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpisoActionPerformed
//        // TODO add your handling code here:
//    }//GEN-LAST:event_txtpisoActionPerformed
//
//    private void txtcapacidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcapacidadActionPerformed
//        // TODO add your handling code here:
//    }//GEN-LAST:event_txtcapacidadActionPerformed
//
//    private void txtnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreActionPerformed
//        // TODO add your handling code here:
//    }//GEN-LAST:event_txtnombreActionPerformed
//
//    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
//        String nombre = txtnombre.getText();
//        String pisoStr = txtpiso.getText();
//        String capacidadStr = txtcapacidad.getText();
//
//        if (!nombre.matches("[a-zA-Z0-9\\s]+")) {
//            JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras y números.");
//            return;
//        }
//
//        if (!pisoStr.matches("\\d+")) {
//            JOptionPane.showMessageDialog(this, "El piso solo debe contener números.");
//            return;
//        }
//
//        if (!capacidadStr.matches("\\d+")) {
//            JOptionPane.showMessageDialog(this, "La capacidad solo debe contener números.");
//            return;
//        }
//
//        int piso = Integer.parseInt(pisoStr);
//        int capacidad = Integer.parseInt(capacidadStr);
//        if (aulas != null) {
//            aulas.setNombre(txtnombre.getText());
//            aulas.setCapacidad(Integer.parseInt(txtcapacidad.getText()));
//            aulas.setPiso(Integer.parseInt(txtpiso.getText()));
//            aulas.setBloque((Bloque) jcbxBloque.getSelectedItem());
//
//            servicioaula.editarAula(aulas);
//
//            JOptionPane.showMessageDialog(null, "Se modificó correctamente el aula");
//        }
//
//        limpiar();
//        this.dispose();
//    }//GEN-LAST:event_btnGuardarActionPerformed
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FrmEditarAulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FrmEditarAulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FrmEditarAulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FrmEditarAulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FrmEditarAulas().setVisible(true);
//            }
//        });
//    }
//
//    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private javax.swing.JButton btnGuardar;
//    private javax.swing.JButton btnregresar;
//    private javax.swing.JLabel jLabel2;
//    private javax.swing.JLabel jLabel3;
//    private javax.swing.JLabel jLabel4;
//    private javax.swing.JLabel jLabel5;
//    private javax.swing.JLabel jLabel6;
//    private javax.swing.JLabel jLabel7;
//    private javax.swing.JLabel jLabel8;
//    private javax.swing.JLabel jLabel9;
//    private javax.swing.JComboBox<Bloque> jcbxBloque;
//    private javax.swing.JTextField txtcapacidad;
//    private javax.swing.JTextField txtid;
//    private javax.swing.JTextField txtnombre;
//    private javax.swing.JTextField txtpiso;
//    // End of variables declaration//GEN-END:variables
//}
