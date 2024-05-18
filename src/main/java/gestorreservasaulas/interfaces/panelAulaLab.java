package gestorreservasaulas.interfaces;

import gestorreservasaulas.entidades.Aula;
import gestorreservasaulas.entidades.Bloque;
import gestorreservasaulas.entidades.Laboratorio;
import gestorreservasaulas.servicios.ServicioAula;
import gestorreservasaulas.servicios.ServicioBloque;
import gestorreservasaulas.servicios.ServicioLaboratorio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

@Component
public class PanelAulaLab extends javax.swing.JPanel {

    @Autowired
    private ServicioAula servicioAula;

    @Autowired
    private ServicioLaboratorio servicioLaboratorio;

    @Autowired
    private ServicioBloque servicioBloque;

    private DefaultTableModel model = new DefaultTableModel(new String[]{"id", "Nombre", "Piso", "Capacidad"}, 0);
    private List<Aula> aulas;
    private List<Laboratorio> laboratorios;
    private List<Bloque> bloques;

    public PanelAulaLab() {
        initComponents();
    }

    @PostConstruct
    private void iniciar() {
        setVisible(true);
        jTable1.setModel(model);
        combo();
        cargarAulasLabPorBloque();
    }

    public void combo() {
        bloques = servicioBloque.obtenerTodosBloques();
        for (Bloque block : bloques) {
            jcbxBloque.addItem(block);
        }

    }

    public void cargarAulasLabPorBloque() {
        Bloque bloqueSeleccionado = (Bloque) jcbxBloque.getSelectedItem();
        if (bloqueSeleccionado == null) {
            return; // Si no hay ningún bloque seleccionado, salir del método
        }

        Long idBloque = bloqueSeleccionado.getId(); // Obtener el ID del bloque seleccionado
        String tipoSeleccionado = jcbxAula.getSelectedItem().toString();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Limpia la tabla antes de añadir nuevos datos

        if ("Aulas".equals(tipoSeleccionado)) {
            aulas = servicioAula.findByBloque(idBloque);
            if (aulas != null && !aulas.isEmpty()) {
                for (Aula aula : aulas) {
                    Object[] datos = {aula.getId(), aula.getNombre(), aula.getPiso(), aula.getCapacidad()};
                    model.addRow(datos);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No hay aulas para este bloque");
            }
        } else {
            laboratorios = servicioLaboratorio.findByBloque(idBloque);
            if (laboratorios != null && !laboratorios.isEmpty()) {
                for (Laboratorio lab : laboratorios) {
                    Object[] datos = {lab.getId(), lab.getNombre(), lab.getPiso(), lab.getCapacidad()};
                    model.addRow(datos);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No hay Laboratorios para este bloque");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jcbxBloque = new javax.swing.JComboBox<>();
        jcbxAula = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Capacidad:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, -1));
        jcbxBloque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxBloqueActionPerformed(evt);
            }
        });
        add(jcbxBloque, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 250, -1));

        jcbxAula.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Aulas", "Laboratorios", " "}));
        jcbxAula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxAulaActionPerformed(evt);
            }
        });
        add(jcbxAula, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 250, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Nombre:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, -1, -1));

        jTextField1.setText("jTextField1");
        add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 170, -1));

        jTextField2.setText("jTextField1");
        add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 170, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{{null, null, null, null}, {null, null, null, null}, {null, null, null, null}, {null, null, null, null}}, new String[]{"Title 1", "Title 2", "Title 3", "Title 4"}));
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 810, 250));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Crear");
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, -1, -1));

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setText("Editar");
        add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 140, -1, -1));

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setText("Reservar");
        add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 430, -1, -1));

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setText("Eliminar");
        add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 90, -1, -1));

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setText("Horarios");
        add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 140, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void jcbxBloqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxBloqueActionPerformed
        cargarAulasLabPorBloque();
    }//GEN-LAST:event_jcbxBloqueActionPerformed

    private void jcbxAulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxAulaActionPerformed
        cargarAulasLabPorBloque();
    }//GEN-LAST:event_jcbxAulaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JComboBox<String> jcbxAula;
    private javax.swing.JComboBox<Bloque> jcbxBloque;
    // End of variables declaration//GEN-END:variables
}
