
package gestorreservasaulas.interfaces;

import gestorreservasaulas.entidades.Aula;
import gestorreservasaulas.entidades.Bloque;
import gestorreservasaulas.servicios.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import javax.swing.*;

@Component
public class FrmCrearAulas extends javax.swing.JFrame {

         @Autowired
    private ServicioBloque servicioBloque;

    private List<Bloque> bloques;
     
    @Autowired
     private ServicioAula servicioaula;
    
   @Autowired
   private Aula aulas;
    
    public FrmCrearAulas() {
        initComponents();
        
    }
    
    @PostConstruct
    public void iniciar(){
        setLocationRelativeTo(null);
        combo();
    }
    
      public void combo() {
        bloques = servicioBloque.obtenerTodosBloques();
        for (Bloque block : bloques) {
            jcbxBloque.addItem(block);
        }

    }
      public void limpiar(){
          txtnombre.setText("");
          txtpiso.setText("");
          txtcapacidad.setText("");
      }
      
      public void LlevaraAula(Aula aula){
          this.aulas = aula;
    txtnombre.setText(aula.getNombre());
    txtpiso.setText(String.valueOf(aula.getPiso()));
    txtcapacidad.setText(String.valueOf(aula.getCapacidad()));
    jcbxBloque.setSelectedItem(aula.getBloque());
      }
    public void crearAula() {
        String nombre= txtnombre.getText();
        int piso= Integer.parseInt(txtpiso.getText());
        int capacidad= Integer.parseInt(txtcapacidad.getText());
        Bloque bloques= (Bloque) jcbxBloque.getSelectedItem();
        
        Aula aula= new Aula();
        aula.setNombre(nombre);
        aula.setCapacidad(capacidad);
        aula.setPiso(piso);
        aula.setBloque(bloques);
        
        servicioaula.crearAula(aula);
        limpiar();
       
        JOptionPane.showMessageDialog(null, "Se creo el Aula correctamente");
        this.dispose();
        

    }


      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnCrearAula = new javax.swing.JButton();
        txtnombre = new javax.swing.JTextField();
        txtcapacidad = new javax.swing.JTextField();
        txtpiso = new javax.swing.JTextField();
        jcbxBloque = new javax.swing.JComboBox<>();
        btnregresar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 48)); // NOI18N
        jLabel2.setText("Crear Aulas");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, 260, 50));

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel3.setText("Capacidad");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 130, 30));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel4.setText("Bloque");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 260, 90, 30));

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel5.setText("Piso");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 90, 30));

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel7.setText("Nombre");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 90, 30));

        btnCrearAula.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        btnCrearAula.setText("Crear");
        btnCrearAula.setBorderPainted(false);
        btnCrearAula.setContentAreaFilled(false);
        btnCrearAula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearAulaActionPerformed(evt);
            }
        });
        getContentPane().add(btnCrearAula, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 450, 210, 40));

        txtnombre.setBorder(null);
        txtnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreActionPerformed(evt);
            }
        });
        getContentPane().add(txtnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 280, 210, 20));

        txtcapacidad.setBorder(null);
        txtcapacidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcapacidadActionPerformed(evt);
            }
        });
        getContentPane().add(txtcapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 390, 100, 20));

        txtpiso.setBorder(null);
        txtpiso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpisoActionPerformed(evt);
            }
        });
        getContentPane().add(txtpiso, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 340, 100, 20));

        jcbxBloque.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbxBloqueItemStateChanged(evt);
            }
        });
        jcbxBloque.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcbxBloqueMouseClicked(evt);
            }
        });
        jcbxBloque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxBloqueActionPerformed(evt);
            }
        });
        getContentPane().add(jcbxBloque, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, 130, -1));

        btnregresar.setBorderPainted(false);
        btnregresar.setContentAreaFilled(false);
        btnregresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregresarActionPerformed(evt);
            }
        });
        getContentPane().add(btnregresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 30, 30));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/regresa.png"))); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/crearaula.png"))); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreActionPerformed

    private void txtcapacidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcapacidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcapacidadActionPerformed

    private void txtpisoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpisoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpisoActionPerformed

    private void btnCrearAulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearAulaActionPerformed
        crearAula();
    }//GEN-LAST:event_btnCrearAulaActionPerformed

    private void jcbxBloqueItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbxBloqueItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbxBloqueItemStateChanged

    private void jcbxBloqueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbxBloqueMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbxBloqueMouseClicked

    private void jcbxBloqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxBloqueActionPerformed
       
    }//GEN-LAST:event_jcbxBloqueActionPerformed

    private void btnregresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregresarActionPerformed
        
        this.dispose();
    }//GEN-LAST:event_btnregresarActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(FrmCrearAulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCrearAulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCrearAulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCrearAulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmCrearAulas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrearAula;
    private javax.swing.JButton btnregresar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JComboBox<Bloque> jcbxBloque;
    private javax.swing.JTextField txtcapacidad;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtpiso;
    // End of variables declaration//GEN-END:variables
}
