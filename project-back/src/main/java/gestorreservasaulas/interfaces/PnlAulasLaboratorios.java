//package gestorreservasaulas.interfaces;
//
//import gestorreservasaulas.entidades.Aula;
//import gestorreservasaulas.entidades.Bloque;
//import gestorreservasaulas.entidades.Laboratorio;
//import gestorreservasaulas.servicios.ServicioAula;
//import gestorreservasaulas.servicios.ServicioBloque;
//import gestorreservasaulas.servicios.ServicioLaboratorio;
//import jakarta.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.util.List;
//
//@Component
//public class PnlAulasLaboratorios extends javax.swing.JPanel {
//
//private Long idSeleccionado;
//private String tipoSeleccionado;
//private Bloque bloqueSeleccionado;
//    @Autowired
//    private ServicioAula servicioAula;
//
//    @Autowired
//    private ServicioLaboratorio servicioLaboratorio;
//
//    @Autowired
//    private FrmCrearAulas frmcrearAula;
//
//    @Autowired
//    private FrmCrearLaboratorio FrmCrearLaboratorio;
//
//    @Autowired
//    private FrmEditarAulas frmeditarAulas;
//
//    @Autowired
//    private FrmEditarLaboratorio frmeditarLab;
//
//    @Autowired
//    private ServicioBloque servicioBloque;
//    private Aula aulaSeleccionada;
//    private Laboratorio labSeleccionada;
//    private int indextabla;
//    @Autowired
//    private FrmHorarios frmHorarios;
//    @Autowired
//    private FrmReservas frmReservas;
//    @Autowired
//    private ServicioAula servicioaula;
//    @Autowired
//    private ServicioLaboratorio servicioLab;
//
//    private DefaultTableModel model = new DefaultTableModel(new String[]{"id", "Nombre", "Piso", "Capacidad"}, 0);
//    private List<Aula> aulas;
//    private List<Laboratorio> laboratorios;
//    private List<Bloque> bloques;
//
//    public PnlAulasLaboratorios() {
//        initComponents();
//        btnGuardar.setVisible(false);
//        cargarAulasLabPorBloque();
//    }
//
//    @PostConstruct
//    private void iniciar() {
//        setVisible(true);
//        jTable1.setModel(model);
//        combo();
//        cargarAulasLabPorBloque();
//    }
//
//    public void combo() {
//        bloques = servicioBloque.obtenerTodosBloques();
//        for (Bloque block : bloques) {
//            jcbxBloque.addItem(block);
//        }
//
//    }
//
//    public void limpiar() {
//        jtxtnombre1.setText("");
//        jtxtpiso.setText("");
//        jtxtcapacidad.setText("");
//    }
//
//    public void cargarAulasLabPorBloque() {
//        Bloque bloqueSeleccionado = (Bloque) jcbxBloque.getSelectedItem();
//        if (bloqueSeleccionado == null) {
//            return; // Si no hay ningún bloque seleccionado, salir del método
//        }
//
//        Long idBloque = bloqueSeleccionado.getId(); // Obtener el ID del bloque seleccionado
//        String tipoSeleccionado = jcbxAula.getSelectedItem().toString();
//        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//        model.setRowCount(0); // Limpia la tabla antes de añadir nuevos datos
//
//        if ("Aulas".equals(tipoSeleccionado)) {
//            aulas = servicioAula.findByBloque(idBloque);
//            if (aulas != null && !aulas.isEmpty()) {
//                for (Aula aula : aulas) {
//                    Object[] datos = {aula.getId(), aula.getNombre(), aula.getPiso(), aula.getCapacidad()};
//                    model.addRow(datos);
//                }
//            } else {
//                JOptionPane.showMessageDialog(null, "No hay aulas para este bloque");
//            }
//        } else {
//            laboratorios = servicioLaboratorio.findByBloque(idBloque);
//            if (laboratorios != null && !laboratorios.isEmpty()) {
//                for (Laboratorio lab : laboratorios) {
//                    Object[] datos = {lab.getId(), lab.getNombre(), lab.getPiso(), lab.getCapacidad()};
//                    model.addRow(datos);
//                }
//            } else {
//                JOptionPane.showMessageDialog(null, "No hay Laboratorios para este bloque");
//            }
//        }
//    }
//
//    private Aula actualizarAulaSeleccionada() {
//        int fila = jTable1.getSelectedRow();
//
//        if (fila != -1) {
//            indextabla = jTable1.getSelectedRow();
//            Long id = Long.parseLong(jTable1.getValueAt(fila, 0).toString());
//            String nombre = jTable1.getValueAt(fila, 1).toString();
//            int capacidad = Integer.parseInt(jTable1.getValueAt(fila, 2).toString());
//
//            if (aulaSeleccionada == null) {
//                aulaSeleccionada = new Aula(); // Solo se crea una vez si es necesario
//            }
//            aulaSeleccionada.setId(id);
//            aulaSeleccionada.setNombre(nombre);
//            aulaSeleccionada.setCapacidad(capacidad);
//        }
//        return aulaSeleccionada;
//    }
//
//    private Laboratorio actualizarLaboratorioSeleccionado() {
//        int fila = jTable1.getSelectedRow();
//        if (fila != -1) {
//            indextabla = jTable1.getSelectedRow();
//            Long id = Long.parseLong(jTable1.getValueAt(fila, 0).toString());
//            String nombre = jTable1.getValueAt(fila, 1).toString();
//            int capacidad = Integer.parseInt(jTable1.getValueAt(fila, 2).toString());
//
//            if (labSeleccionada == null) {
//                labSeleccionada = new Laboratorio(); // Solo se crea una vez si es necesario
//            }
//            labSeleccionada.setId(id);
//            labSeleccionada.setNombre(nombre);
//            labSeleccionada.setCapacidad(capacidad);
//        }
//        return labSeleccionada;
//    }
//
//    /**
//     * This method is called from within the constructor to initialize the form.
//     * WARNING: Do NOT modify this code. The content of this method is always
//     * regenerated by the Form Editor.
//     */
//
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//    private void initComponents() {
//
//        jPanel1 = new javax.swing.JPanel();
//        jcbxAula = new javax.swing.JComboBox<>();
//        jLabel3 = new javax.swing.JLabel();
//        jLabel4 = new javax.swing.JLabel();
//        jLabel2 = new javax.swing.JLabel();
//        jtxtpiso = new javax.swing.JTextField();
//        btnCrear = new javax.swing.JButton();
//        btnGuardar = new javax.swing.JButton();
//        btneliminar = new javax.swing.JButton();
//        jLabel1 = new javax.swing.JLabel();
//        jtxtcapacidad = new javax.swing.JTextField();
//        jcbxBloque = new javax.swing.JComboBox<>();
//        jLabel5 = new javax.swing.JLabel();
//        jtxtnombre1 = new javax.swing.JTextField();
//        btnEditar1 = new javax.swing.JButton();
//        jPanel2 = new javax.swing.JPanel();
//        jScrollPane1 = new javax.swing.JScrollPane();
//        jTable1 = new javax.swing.JTable();
//        btnReservar = new javax.swing.JButton();
//        btnHorarios = new javax.swing.JButton();
//
//        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
//
//        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
//        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
//
//        jcbxAula.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aulas", "Laboratorios" }));
//        jcbxAula.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jcbxAulaActionPerformed(evt);
//            }
//        });
//        jPanel1.add(jcbxAula, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, 350, -1));
//
//        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
//        jLabel3.setText("Bloque:");
//        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 80, -1));
//
//        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
//        jLabel4.setText("Aulas-Laboratorios:");
//        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, -1, -1));
//
//        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
//        jLabel2.setText("Nombre:");
//        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));
//        jPanel1.add(jtxtpiso, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 360, -1));
//
//        btnCrear.setBackground(new java.awt.Color(153, 0, 0));
//        btnCrear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
//        btnCrear.setForeground(new java.awt.Color(255, 255, 255));
//        btnCrear.setText("Crear");
//        btnCrear.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnCrearActionPerformed(evt);
//            }
//        });
//        jPanel1.add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 160, -1, -1));
//
//        btnGuardar.setBackground(new java.awt.Color(153, 0, 0));
//        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
//        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
//        btnGuardar.setText("Guardar");
//        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnGuardarActionPerformed(evt);
//            }
//        });
//        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 160, -1, -1));
//
//        btneliminar.setBackground(new java.awt.Color(153, 0, 0));
//        btneliminar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
//        btneliminar.setForeground(new java.awt.Color(255, 255, 255));
//        btneliminar.setText("Eliminar");
//        btneliminar.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btneliminarActionPerformed(evt);
//            }
//        });
//        jPanel1.add(btneliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 160, -1, -1));
//
//        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
//        jLabel1.setText("Piso:");
//        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));
//
//        jtxtcapacidad.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jtxtcapacidadActionPerformed(evt);
//            }
//        });
//        jPanel1.add(jtxtcapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 120, 350, -1));
//
//        jcbxBloque.addItemListener(new java.awt.event.ItemListener() {
//            public void itemStateChanged(java.awt.event.ItemEvent evt) {
//
//            }
//        });
//        jcbxBloque.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//
//            }
//        });
//        jcbxBloque.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jcbxBloqueActionPerformed(evt);
//            }
//        });
//        jPanel1.add(jcbxBloque, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 410, -1));
//
//        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
//        jLabel5.setText("Capacidad:");
//        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, -1, -1));
//        jPanel1.add(jtxtnombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 410, -1));
//
//        btnEditar1.setBackground(new java.awt.Color(153, 0, 0));
//        btnEditar1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
//        btnEditar1.setForeground(new java.awt.Color(255, 255, 255));
//        btnEditar1.setText("Editar");
//        btnEditar1.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnEditar1ActionPerformed(evt);
//            }
//        });
//        jPanel1.add(btnEditar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 160, -1, -1));
//
//        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 970, 200));
//
//        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
//        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
//
//        jTable1.setModel(new javax.swing.table.DefaultTableModel(
//            new Object [][] {
//                {null, null, null, null},
//                {null, null, null, null},
//                {null, null, null, null},
//                {null, null, null, null}
//            },
//            new String [] {
//                "Title 1", "Title 2", "Title 3", "Title 4"
//            }
//        ));
//        jScrollPane1.setViewportView(jTable1);
//
//        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 810, 250));
//
//        btnReservar.setBackground(new java.awt.Color(153, 0, 0));
//        btnReservar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
//        btnReservar.setForeground(new java.awt.Color(255, 255, 255));
//        btnReservar.setText("Reservar");
//        btnReservar.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnReservarActionPerformed(evt);
//            }
//        });
//        jPanel2.add(btnReservar, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 140, -1, -1));
//
//        btnHorarios.setBackground(new java.awt.Color(153, 0, 0));
//        btnHorarios.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
//        btnHorarios.setForeground(new java.awt.Color(255, 255, 255));
//        btnHorarios.setText("Horarios");
//        btnHorarios.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnHorariosActionPerformed(evt);
//            }
//        });
//        jPanel2.add(btnHorarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 80, -1, -1));
//
//        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 970, 270));
//    }// </editor-fold>//GEN-END:initComponents
//
//    private void jcbxBloqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxBloqueActionPerformed
//        cargarAulasLabPorBloque();
//    }//GEN-LAST:event_jcbxBloqueActionPerformed
//
//    private void jcbxAulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxAulaActionPerformed
//        cargarAulasLabPorBloque();
//    }//GEN-LAST:event_jcbxAulaActionPerformed
//
//    private void btnHorariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHorariosActionPerformed
//        // TODO add your handling code here:
//        String tipoSeleccionado = jcbxAula.getSelectedItem().toString();
//
//        if ("Aulas".equals(tipoSeleccionado)) {
//            actualizarAulaSeleccionada();
//            if (aulaSeleccionada != null) {
//                frmHorarios.setAula(aulas.get(indextabla));
//                frmHorarios.initializeTable();
//                frmHorarios.setVisible(true);
//                frmHorarios.setLocationRelativeTo(this);
//
//            } else {
//                JOptionPane.showMessageDialog(null, "Seleccione un item de la lista");
//            }
//        } else if ("Laboratorios".equals(tipoSeleccionado)) {
//            actualizarLaboratorioSeleccionado();
//            if (labSeleccionada != null) {
//                frmHorarios.setLaboratorio(laboratorios.get(indextabla));
//                frmHorarios.initializeTable();
//                frmHorarios.setVisible(true);
//                frmHorarios.setLocationRelativeTo(this);
//            } else {
//                JOptionPane.showMessageDialog(null, "Seleccione un item de la lista");
//            }
//        }
//
//    }//GEN-LAST:event_btnHorariosActionPerformed
//
//    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
//        String tipoSeleccionado = jcbxAula.getSelectedItem().toString();
//
//        if ("Aulas".equals(tipoSeleccionado)) {
//            String nombre = jtxtnombre1.getText();
//            String pisoStr = jtxtpiso.getText();
//            String capacidadStr = jtxtcapacidad.getText();
//
//            if (!nombre.matches("[a-zA-Z0-9\\s]+")) {
//                JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras y números.");
//                return;
//            }
//
//            if (!pisoStr.matches("\\d+")) {
//                JOptionPane.showMessageDialog(this, "El piso solo debe contener números.");
//                return;
//            }
//
//            if (!capacidadStr.matches("\\d+")) {
//                JOptionPane.showMessageDialog(this, "La capacidad solo debe contener números.");
//                return;
//            }
//
//            int piso = Integer.parseInt(pisoStr);
//            int capacidad = Integer.parseInt(capacidadStr);
//            Bloque bloques = (Bloque) jcbxBloque.getSelectedItem();
//
//            Aula aula = new Aula();
//            aula.setNombre(nombre);
//            aula.setCapacidad(capacidad);
//            aula.setPiso(piso);
//            aula.setBloque(bloques);
//
//            servicioaula.crearAula(aula);
//            cargarAulasLabPorBloque();
//            limpiar();
//            JOptionPane.showMessageDialog(null, "Se creó el Aula correctamente");
//
//        } else if ("Laboratorios".equals(tipoSeleccionado)) {
//            String nombre = jtxtnombre1.getText();
//            String pisoStr = jtxtpiso.getText();
//            String capacidadStr = jtxtcapacidad.getText();
//
//            if (!nombre.matches("[a-zA-Z0-9\\s]+")) {
//                JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras y números.");
//                return;
//            }
//
//            if (!pisoStr.matches("\\d+")) {
//                JOptionPane.showMessageDialog(this, "El piso solo debe contener números.");
//                return;
//            }
//
//            if (!capacidadStr.matches("\\d+")) {
//                JOptionPane.showMessageDialog(this, "La capacidad solo debe contener números.");
//                return;
//            }
//
//            int piso = Integer.parseInt(pisoStr);
//            int capacidad = Integer.parseInt(capacidadStr);
//            Bloque bloques = (Bloque) jcbxBloque.getSelectedItem();
//
//            Laboratorio lab = new Laboratorio();
//            lab.setNombre(nombre);
//            lab.setPiso(piso);
//            lab.setCapacidad(capacidad);
//            lab.setBloque(bloques);
//
//            servicioLab.crearLaboratorio(lab);
//            cargarAulasLabPorBloque();
//            limpiar();
//
//            JOptionPane.showMessageDialog(null, "Se creo el Laboratorio correctamente");
//
//        }
//
//
//    }//GEN-LAST:event_btnCrearActionPerformed
//
//    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
//         String nombre = jtxtnombre1.getText();
//    String pisoStr = jtxtpiso.getText();
//    String capacidadStr = jtxtcapacidad.getText();
//
//    if (!nombre.matches("[a-zA-Z0-9\\s]+")) {
//        JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras y números.");
//        return;
//    }
//
//    if (!pisoStr.matches("\\d+")) {
//        JOptionPane.showMessageDialog(this, "El piso solo debe contener números.");
//        return;
//    }
//
//    if (!capacidadStr.matches("\\d+")) {
//        JOptionPane.showMessageDialog(this, "La capacidad solo debe contener números.");
//        return;
//    }
//
//    int piso = Integer.parseInt(pisoStr);
//    int capacidad = Integer.parseInt(capacidadStr);
//
//    if ("Aulas".equals(tipoSeleccionado)) {
//        Aula aula = new Aula();
//        aula.setId(idSeleccionado);
//        aula.setNombre(nombre);
//        aula.setPiso(piso);
//        aula.setCapacidad(capacidad);
//        aula.setBloque(bloqueSeleccionado);
//
//        servicioAula.editarAula(aula);
//        cargarAulasLabPorBloque();
//        JOptionPane.showMessageDialog(null, "Se modificó correctamente el aula");
//        btnGuardar.setVisible(false);
//    } else if ("Laboratorios".equals(tipoSeleccionado)) {
//        Laboratorio laboratorio = new Laboratorio();
//        laboratorio.setId(idSeleccionado);
//        laboratorio.setNombre(nombre);
//        laboratorio.setPiso(piso);
//        laboratorio.setCapacidad(capacidad);
//        laboratorio.setBloque(bloqueSeleccionado);
//
//        servicioLaboratorio.editarLaboratorio(laboratorio);
//        cargarAulasLabPorBloque();
//        JOptionPane.showMessageDialog(null, "Se modificó correctamente el laboratorio");
//        btnGuardar.setVisible(false);
//    }
//
//    limpiar();
//    }//GEN-LAST:event_btnGuardarActionPerformed
//
//
//    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
//        int fila = jTable1.getSelectedRow();
//        if (fila != -1) {
//            int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quiere eliminar esta fila?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
//            if (confirm == JOptionPane.YES_OPTION) {
//                Long id = Long.parseLong(jTable1.getValueAt(fila, 0).toString());
//                String tipoSeleccionado = jcbxAula.getSelectedItem().toString();
//
//                if ("Aulas".equals(tipoSeleccionado)) {
//                    servicioAula.eliminarAula(id);
//                } else if ("Laboratorios".equals(tipoSeleccionado)) {
//                    servicioLaboratorio.eliminarLaboratorio(id);
//                }
//
//                cargarAulasLabPorBloque();
//                JOptionPane.showMessageDialog(null, "Fila eliminada correctamente");
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar");
//        }
//    }//GEN-LAST:event_btneliminarActionPerformed
//
//    private void btnReservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarActionPerformed
//        String tipoSeleccionado = jcbxAula.getSelectedItem().toString();
//
//        if ("Aulas".equals(tipoSeleccionado)) {
//            actualizarAulaSeleccionada();
//            if (aulaSeleccionada != null) {
//                frmReservas.setAula(aulas.get(indextabla));
//                frmReservas.initializeTable();
//                frmReservas.setVisible(true);
//                frmReservas.setLocationRelativeTo(this);
//
//            } else {
//                JOptionPane.showMessageDialog(null, "Seleccione un item de la lista");
//            }
//        } else if ("Laboratorios".equals(tipoSeleccionado)) {
//            actualizarLaboratorioSeleccionado();
//            if (labSeleccionada != null) {
//                frmReservas.setLaboratorio(laboratorios.get(indextabla));
//                frmReservas.initializeTable();
//                frmReservas.setVisible(true);
//                frmReservas.setLocationRelativeTo(this);
//            } else {
//                JOptionPane.showMessageDialog(null, "Seleccione un item de la lista");
//            }
//        }
//    }//GEN-LAST:event_btnReservarActionPerformed
//
//    private void jtxtcapacidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtcapacidadActionPerformed
//        // TODO add your handling code here:
//    }//GEN-LAST:event_jtxtcapacidadActionPerformed
//
//    private void btnEditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditar1ActionPerformed
//          int fila = jTable1.getSelectedRow();
//    if (fila != -1) {
//        Long id = Long.parseLong(jTable1.getValueAt(fila, 0).toString());
//        String nombre = jTable1.getValueAt(fila, 1).toString();
//        int piso = Integer.parseInt(jTable1.getValueAt(fila, 2).toString());
//        int capacidad = Integer.parseInt(jTable1.getValueAt(fila, 3).toString());
//        Bloque bloqueSeleccionado = (Bloque) jcbxBloque.getSelectedItem();
//
//        jtxtnombre1.setText(nombre);
//        jtxtpiso.setText(String.valueOf(piso));
//        jtxtcapacidad.setText(String.valueOf(capacidad));
//
//        btnGuardar.setVisible(true);
//        this.idSeleccionado = id;
//        this.tipoSeleccionado = jcbxAula.getSelectedItem().toString();
//        this.bloqueSeleccionado = bloqueSeleccionado;
//    }else{
//     JOptionPane.showMessageDialog(null, "Seleccione una fila para editar");
//    }
//    }//GEN-LAST:event_btnEditar1ActionPerformed
//
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
//            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new PnlAulasLaboratorios().setVisible(true);
//            }
//        });
//    }
//
//    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private javax.swing.JButton btnCrear;
//    private javax.swing.JButton btnEditar1;
//    private javax.swing.JButton btnGuardar;
//    private javax.swing.JButton btnHorarios;
//    private javax.swing.JButton btnReservar;
//    private javax.swing.JButton btneliminar;
//    private javax.swing.JLabel jLabel1;
//    private javax.swing.JLabel jLabel2;
//    private javax.swing.JLabel jLabel3;
//    private javax.swing.JLabel jLabel4;
//    private javax.swing.JLabel jLabel5;
//    private javax.swing.JPanel jPanel1;
//    private javax.swing.JPanel jPanel2;
//    private javax.swing.JScrollPane jScrollPane1;
//    private javax.swing.JTable jTable1;
//    private javax.swing.JComboBox<String> jcbxAula;
//    private javax.swing.JComboBox<Bloque> jcbxBloque;
//    private javax.swing.JTextField jtxtcapacidad;
//    private javax.swing.JTextField jtxtnombre1;
//    private javax.swing.JTextField jtxtpiso;
//    // End of variables declaration//GEN-END:variables
//}
