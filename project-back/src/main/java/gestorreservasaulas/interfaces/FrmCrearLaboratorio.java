///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
// */
//package gestorreservasaulas.interfaces;
//
//import gestorreservasaulas.entidades.*;
//import gestorreservasaulas.entidades.Bloque;
//import gestorreservasaulas.servicios.*;
//import gestorreservasaulas.servicios.ServicioBloque;
//import jakarta.annotation.PostConstruct;
//import java.util.List;
//import javax.swing.JOptionPane;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class FrmCrearLaboratorio extends javax.swing.JFrame {
//
//    @Autowired
//    private ServicioBloque servicioBloque;
//
//    private List<Bloque> bloques;
//
//    @Autowired
//    private ServicioLaboratorio servicioLab;
//
//    public FrmCrearLaboratorio() {
//        initComponents();
//    }
//
//    @PostConstruct
//    public void iniciar() {
//        setLocationRelativeTo(null);
//        combo();
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
//        txtnombre.setText("");
//        txtpiso.setText("");
//        txtcapacidad.setText("");
//    }
//
//    public void crearLaboratorio() {
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
//        Bloque bloques = (Bloque) jcbxBloque.getSelectedItem();
//
//        Laboratorio lab = new Laboratorio();
//        lab.setNombre(nombre);
//        lab.setPiso(piso);
//        lab.setCapacidad(capacidad);
//        lab.setBloque(bloques);
//
//        servicioLab.crearLaboratorio(lab);
//        limpiar();
//        JOptionPane.showMessageDialog(null, "Se creo el Laboratorio correctamente");
//        this.dispose();
//    }
//
//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//    private void initComponents() {
//
//        jLabel2 = new javax.swing.JLabel();
//        jLabel4 = new javax.swing.JLabel();
//        jLabel7 = new javax.swing.JLabel();
//        jLabel8 = new javax.swing.JLabel();
//        jLabel9 = new javax.swing.JLabel();
//        btnGuardar = new javax.swing.JButton();
//        jLabel10 = new javax.swing.JLabel();
//        jcbxBloque = new javax.swing.JComboBox<>();
//        txtcapacidad = new javax.swing.JTextField();
//        txtnombre = new javax.swing.JTextField();
//        txtpiso = new javax.swing.JTextField();
//        jLabel3 = new javax.swing.JLabel();
//        btnRegresar = new javax.swing.JButton();
//        jLabel1 = new javax.swing.JLabel();
//
//        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 48)); // NOI18N
//        jLabel2.setText("Crear Aulas");
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
//
//        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 48)); // NOI18N
//        jLabel4.setText("Crear Laboratorio");
//        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 430, 50));
//
//        jLabel7.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
//        jLabel7.setText("Piso");
//        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, 90, 30));
//
//        jLabel8.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
//        jLabel8.setText("Bloque");
//        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 260, 90, 30));
//
//        jLabel9.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
//        jLabel9.setText("Nombre");
//        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 90, 30));
//
//        btnGuardar.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
//        btnGuardar.setText("Guardar");
//        btnGuardar.setBorder(null);
//        btnGuardar.setBorderPainted(false);
//        btnGuardar.setContentAreaFilled(false);
//        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnGuardarActionPerformed(evt);
//            }
//        });
//        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 450, 210, 40));
//
//        jLabel10.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
//        jLabel10.setText("Capacidad");
//        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, 140, 30));
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
//        getContentPane().add(jcbxBloque, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 310, 160, -1));
//
//        txtcapacidad.setBorder(null);
//        getContentPane().add(txtcapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 394, 100, -1));
//
//        txtnombre.setBorder(null);
//        getContentPane().add(txtnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 280, 210, -1));
//
//        txtpiso.setBorder(null);
//        getContentPane().add(txtpiso, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 340, 100, -1));
//
//        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/regresa.png"))); // NOI18N
//        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, -1, -1));
//
//        btnRegresar.setBorderPainted(false);
//        btnRegresar.setContentAreaFilled(false);
//        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnRegresarActionPerformed(evt);
//            }
//        });
//        getContentPane().add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 30, 30));
//
//        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/crearaula.png"))); // NOI18N
//        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
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
//    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
//       crearLaboratorio();
//    }//GEN-LAST:event_btnGuardarActionPerformed
//
//    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
//        this.dispose();
//    }//GEN-LAST:event_btnRegresarActionPerformed
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
//            java.util.logging.Logger.getLogger(FrmCrearLaboratorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FrmCrearLaboratorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FrmCrearLaboratorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FrmCrearLaboratorio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FrmCrearLaboratorio().setVisible(true);
//            }
//        });
//    }
//
//    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private javax.swing.JButton btnGuardar;
//    private javax.swing.JButton btnRegresar;
//    private javax.swing.JLabel jLabel1;
//    private javax.swing.JLabel jLabel10;
//    private javax.swing.JLabel jLabel2;
//    private javax.swing.JLabel jLabel3;
//    private javax.swing.JLabel jLabel4;
//    private javax.swing.JLabel jLabel7;
//    private javax.swing.JLabel jLabel8;
//    private javax.swing.JLabel jLabel9;
//    private javax.swing.JComboBox<Bloque> jcbxBloque;
//    private javax.swing.JTextField txtcapacidad;
//    private javax.swing.JTextField txtnombre;
//    private javax.swing.JTextField txtpiso;
//    // End of variables declaration//GEN-END:variables
//}
