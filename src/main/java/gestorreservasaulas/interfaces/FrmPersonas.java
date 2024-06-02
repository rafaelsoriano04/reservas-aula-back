package gestorreservasaulas.interfaces;

import gestorreservasaulas.entidades.Persona;
import gestorreservasaulas.servicios.ServicioPersona;
import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class FrmPersonas extends javax.swing.JFrame {

    private Persona persona;

    @Autowired
    private ServicioPersona servicioPersona;

    @Autowired
    @Lazy
    private FrmReservas frmReservas;

    public FrmPersonas() {
        initComponents();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                frmReservas.setVisible(true);
                dispose();
            }
        });
    }

    private void buscar() {
        if (!txtCedula.getText().isEmpty()) {
            Persona persona = servicioPersona.buscar(txtCedula.getText());
            if (persona != null) {
                txtNombre.setText(persona.getNombre());
                txtApellido.setText(persona.getApellido());
                txtTelefono.setText(persona.getTelefono());
            }
        } else {
            JOptionPane.showMessageDialog(null, "La persona no existe, es necesario crear una nueva");
        }
    }

    private void guardar() {
        String cedula, nombre, apellido, telefono;
        cedula = txtCedula.getText().trim();
        nombre = txtNombre.getText().trim();
        apellido = txtApellido.getText().trim();
        telefono = txtTelefono.getText().trim();

        Persona persona = servicioPersona.buscar(cedula);
        
        if (persona == null) {
            persona = new Persona();
            persona.setCedula(cedula);
            persona.setNombre(nombre);
            persona.setApellido(apellido);
            persona.setTelefono(telefono);
            servicioPersona.guardar(persona);
            JOptionPane.showMessageDialog(null, "Persona creada y asignada");
            frmReservas.setResponsable(persona);
            frmReservas.setResponsable();
            dispose();
            frmReservas.setVisible(true);
        } else {
            if (!persona.getNombre().equals(nombre) || !persona.getApellido().equals(nombre) ||
                    !persona.getTelefono().equals(telefono)) {
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                persona.setTelefono(telefono);
                JOptionPane.showMessageDialog(null, "La persona con cédula " + txtCedula.getText() + " se modificó y fue asignada correctamente");
                servicioPersona.guardar(persona);
            } else {
                JOptionPane.showMessageDialog(null, "Persona asignada");
            }
            frmReservas.setResponsable(persona);
            frmReservas.setResponsable();
            dispose();
            frmReservas.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        txtCedula = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Cedula");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, -1, -1));
        getContentPane().add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 120, -1));

        jLabel2.setText("Nombre");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 120, -1));
        getContentPane().add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 120, -1));

        jLabel3.setText("Apellido");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        jLabel4.setText("Telefono");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));
        getContentPane().add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 120, -1));

        btnGuardar.setText("Asignar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 260, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(FrmPersonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPersonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPersonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPersonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPersonas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
