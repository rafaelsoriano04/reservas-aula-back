package gestorreservasaulas.interfaces;

import gestorreservasaulas.entidades.*;
import gestorreservasaulas.entidades.Horario;
import gestorreservasaulas.entidades.Laboratorio;
import gestorreservasaulas.enums.Prenda;
import gestorreservasaulas.servicios.ServicioHorario;

import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableModel;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FrmReservas extends javax.swing.JFrame {

    private final DefaultTableModel model = new DefaultTableModel(new String[]{"Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes"}, 0);
    @Autowired
    private ServicioHorario servicioHorario;

    @Autowired
    FrmPersonas frmPersonas;

    @Setter
    private Aula aula;
    @Setter
    private Laboratorio laboratorio;
    private Prenda prenda;

    @Setter
    private Persona responsable;

    public FrmReservas() {
        initComponents();
    }

    public void initializeTable() {
        jTable1.setModel(model);
        for (int hour = 7; hour < 20; hour++) {
            // Formato de hora para mostrar como "7-8", "8-9", etc., sin AM/PM
            String time = String.format("%d-%d", hour, hour + 1);

            // Añadir la fila con el intervalo de tiempo
            model.addRow(new Object[]{time, "", "", "", "", ""});
        }

        if (aula != null) {
            aula.setListaHorario(servicioHorario.obtenerHorariosPorAula(aula.getId()));
            updateTableWithAula(aula, model);
        } else if (laboratorio != null) {
            laboratorio.setListaHorario(servicioHorario.obtenerHorariosPorLabs(laboratorio.getId()));
            updateTableWithLaboratorio(laboratorio, model);
        }
    }

    public void setResponsable() {
        txtresponsable.setText(responsable.getCedula());
    }

    private void updateTableWithAula(Aula aula, DefaultTableModel model) {
        for (Horario horario : aula.getListaHorario()) {
            int hourIndex = Integer.parseInt(horario.getHora()) - 7;

            model.setValueAt(horario.getMateria(), hourIndex, getDayIndex(horario.getDia()));
        }
    }

    private void updateTableWithLaboratorio(Laboratorio laboratorio, DefaultTableModel model) {
        for (Horario horario : laboratorio.getListaHorario()) {
            int hourIndex = Integer.parseInt(horario.getHora()) - 7;

            model.setValueAt(horario.getMateria(), hourIndex, getDayIndex(horario.getDia()));
        }
    }

    private int getDayIndex(String day) {
        return switch (day) {
            case "Lunes" ->
                1;
            case "Martes" ->
                2;
            case "Miercoles" ->
                3;
            case "Jueves" ->
                4;
            case "Viernes" ->
                5;
            default ->
                -1;
        };
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jcbxdia = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jcbxhora = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jtxtMateria = new javax.swing.JTextField();
        btnAsignarResponsable = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jcbxlistahorarios = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtresponsable = new javax.swing.JTextField();
        cmbGarantia = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jPanel1.setBackground(new java.awt.Color(153, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("GESTI�N DE RESERVAS");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 17, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Garantia:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 100, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("D�a:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 31, -1));

        jcbxdia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lunes", "Martes", "Miercoles", "Jueves", "Viernes" }));
        jcbxdia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxdiaActionPerformed(evt);
            }
        });
        jPanel2.add(jcbxdia, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 141, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Hora:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, -1));

        jcbxhora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7-8", "8-9", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-19", "19-20" }));
        jPanel2.add(jcbxhora, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 140, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Materia:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, -1, -1));

        jtxtMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtMateriaActionPerformed(evt);
            }
        });
        jPanel2.add(jtxtMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, 146, -1));

        btnAsignarResponsable.setBackground(new java.awt.Color(153, 0, 0));
        btnAsignarResponsable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAsignarResponsable.setForeground(new java.awt.Color(255, 255, 255));
        btnAsignarResponsable.setText("Agregar");
        btnAsignarResponsable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarResponsableActionPerformed(evt);
            }
        });
        jPanel2.add(btnAsignarResponsable, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 40, 101, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Horario:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        jPanel2.add(jcbxlistahorarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 380, -1));

        jButton4.setBackground(new java.awt.Color(153, 0, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Eliminar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, 101, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Eliminar Reserva:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 164, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Agregar Reserva:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 164, -1));
        jPanel2.add(txtresponsable, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 130, -1));

        cmbGarantia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione---", "C�dula", "Licencia" }));
        jPanel2.add(cmbGarantia, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 130, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Responsable:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 20, 100, -1));

        btnAgregar.setBackground(new java.awt.Color(153, 0, 0));
        btnAgregar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 101, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jcbxdiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxdiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbxdiaActionPerformed

    private void jtxtMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtMateriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtMateriaActionPerformed

    private void btnAsignarResponsableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarResponsableActionPerformed
        frmPersonas.setVisible(true);
        frmPersonas.setLocationRelativeTo(null);
        setVisible(false);
    }//GEN-LAST:event_btnAsignarResponsableActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        Horario horarioSeleccionado = (Horario) jcbxlistahorarios.getSelectedItem();
        if (horarioSeleccionado != null) {
            if (this.servicioHorario.eliminarHorario(horarioSeleccionado.getId())) {
                JOptionPane.showMessageDialog(null, "Se elimino el horario");
                initializeTable();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar");
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        String hora = jcbxhora.getSelectedItem().toString();
        int in = hora.indexOf("-");
        hora = hora.substring(0, in);
        int controlado = 0;
        Prenda prendas = Prenda.valueOf(cmbGarantia.getSelectedItem().toString());
        String responsable = txtresponsable.getText();

        Horario nHorario = new Horario(Long.valueOf("0"), jcbxdia.getSelectedItem().toString(), hora,
                jtxtMateria.getText(), aula, laboratorio);
        //Verificar que no se agreguen horarios para la misma hora y para el mismo dia
        if (aula != null) {
            for (Horario horario : aula.getListaHorario()) {
                if (horario.getDia().equals(jcbxdia.getSelectedItem().toString()) && horario.getHora().equals(hora)) {

                    controlado = 1;
                }
            }
        }

        if (laboratorio != null) {
            for (Horario horario : laboratorio.getListaHorario()) {
                if (horario.getDia().equals(jcbxdia.getSelectedItem().toString()) && horario.getHora().equals(hora)) {

                    controlado = 1;
                }
            }
        }

        if (controlado == 0) {
            JOptionPane.showMessageDialog(null, "Se agrego el horario");
            servicioHorario.crearHorario(nHorario);
            //AGREGAR EL Horario a la tabla o volver a traer el horario por medio del ID
            if (aula != null) {

                this.initializeTable();
            } else {

                this.initializeTable();
            }

        } else {
            JOptionPane.showMessageDialog(null, "No se puede agregar horarios existentes");
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

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
            java.util.logging.Logger.getLogger(FrmReservas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmReservas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmReservas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmReservas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmReservas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAsignarResponsable;
    private javax.swing.JComboBox<String> cmbGarantia;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> jcbxdia;
    private javax.swing.JComboBox<String> jcbxhora;
    private javax.swing.JComboBox<Horario> jcbxlistahorarios;
    private javax.swing.JTextField jtxtMateria;
    private javax.swing.JTextField txtresponsable;
    // End of variables declaration//GEN-END:variables
}
