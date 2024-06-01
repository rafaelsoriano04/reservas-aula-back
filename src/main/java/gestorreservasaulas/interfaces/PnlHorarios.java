package gestorreservasaulas.interfaces;

import gestorreservasaulas.entidades.Aula;
import gestorreservasaulas.entidades.Horario;
import gestorreservasaulas.entidades.Laboratorio;
import gestorreservasaulas.servicios.ServicioHorario;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.factory.annotation.Autowired;

public class PnlHorarios extends javax.swing.JPanel {

    @Autowired
    private ServicioHorario servicioHorario;

    private Aula aula = null;
    private Laboratorio laboratorio = null;

    public PnlHorarios() {
        //Recibir un aula
        //llenar la tabla con los horario correspondiente a ese id de aula, agarrar de la lista
        initComponents();
        
        String[] columnNames = {"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};


        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (int hour = 7; hour < 20; hour++) {
            // Formato de hora para mostrar como "7-8", "8-9", etc., sin AM/PM
            String time = String.format("%d-%d", hour, hour + 1);

            // Añadir la fila con el intervalo de tiempo
            model.addRow(new Object[]{time, "LIBRE", "LIBRE", "LIBRE", "LIBRE", "LIBRE"});
        }
        jTable1.setModel(model);
    }
    
    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }
    
    public PnlHorarios(Aula aula) {
        initComponents();
        String[] columnNames = {"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};


        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (int hour = 7; hour < 20; hour++) {

            String time = String.format("%d-%d", hour, hour + 1);


            model.addRow(new Object[]{time, "LIBRE", "LIBRE", "LIBRE", "LIBRE", "LIBRE"});
        }
        jTable1.setModel(model);
        this.aula = aula;
        for (Horario horario : aula.getListaHorario()) {
            switch (horario.getDia()) {
                case "Lunes":
                    model.setValueAt(horario.getMateria(), Integer.valueOf(horario.getHora()), 1);
                    break;
                case "Martes":
                    model.setValueAt(horario.getMateria(), Integer.valueOf(horario.getHora()), 2);
                    break;
                case "Miercoles":
                    model.setValueAt(horario.getMateria(), Integer.valueOf(horario.getHora()), 3);
                    break;
                case "Jueves":
                    model.setValueAt(horario.getMateria(), Integer.valueOf(horario.getHora()), 4);
                    break;
                case "Viernes":
                    model.setValueAt(horario.getMateria(), Integer.valueOf(horario.getHora()), 5);
                    break;

            }
        }
    }

    public PnlHorarios(Laboratorio laboratorio) {
        initComponents();
        String[] columnNames = {"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (int hour = 7; hour < 20; hour++) {

            String time = String.format("%d-%d", hour, hour + 1);


            model.addRow(new Object[]{time, "LIBRE", "LIBRE", "LIBRE", "LIBRE", "LIBRE"});
        }
        jTable1.setModel(model);
        this.laboratorio = laboratorio;
        for (Horario horario : laboratorio.getListaHorario()) {
            switch (horario.getDia()) {
                case "Lunes":
                    model.setValueAt(horario.getMateria(), Integer.valueOf(horario.getHora()), 1);
                    break;
                case "Martes":
                    model.setValueAt(horario.getMateria(), Integer.valueOf(horario.getHora()), 2);
                    break;
                case "Miercoles":
                    model.setValueAt(horario.getMateria(), Integer.valueOf(horario.getHora()), 3);
                    break;
                case "Jueves":
                    model.setValueAt(horario.getMateria(), Integer.valueOf(horario.getHora()), 4);
                    break;
                case "Viernes":
                    model.setValueAt(horario.getMateria(), Integer.valueOf(horario.getHora()), 5);
                    break;

            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jcbxdia = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jcbxhora = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jtxtMateria = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1030, 500));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Lunes", "Martes", "Miercoles", "Jueves", "Viernes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 950, 325));

        jPanel1.setBackground(new java.awt.Color(153, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("GESTIÓN DE HORARIOS");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 17, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 10, 950, 50));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Agregar Nuevo Horario:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 164, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Día:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 31, -1));

        jcbxdia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lunes", "Martes", "Miercoles", "Jueves", "Viernes" }));
        jPanel2.add(jcbxdia, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 141, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Hora:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, -1));

        jcbxhora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7-8", "8-9", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-19", "19-20" }));
        jPanel2.add(jcbxhora, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 140, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Materia:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, -1, -1));

        jtxtMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtMateriaActionPerformed(evt);
            }
        });
        jPanel2.add(jtxtMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, 146, -1));

        jButton1.setBackground(new java.awt.Color(153, 0, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 101, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Eliminar Horario:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 164, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Horario:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 410, -1));

        jButton2.setBackground(new java.awt.Color(153, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Eliminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 120, 101, -1));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 950, 170));
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Horario nHorario = new Horario(Long.valueOf("0"), jcbxdia.getSelectedItem().toString(), jcbxhora.getSelectedItem().toString(),
                jtxtMateria.getText(), aula, laboratorio);
        //Verificar que no se agreguen horarios para la misma hora y para el mismo dia
        if (this.servicioHorario.crearHorario(nHorario)) {
            JOptionPane.showMessageDialog(null, "Se agrego el horario");
        } else {
            JOptionPane.showMessageDialog(null, "No se puede agregar horarios existentes");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtxtMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtMateriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtMateriaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> jcbxdia;
    private javax.swing.JComboBox<String> jcbxhora;
    private javax.swing.JTextField jtxtMateria;
    // End of variables declaration//GEN-END:variables

}
