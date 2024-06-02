package gestorreservasaulas.interfaces;

import javax.swing.table.DefaultTableModel;
import org.springframework.stereotype.Component;

@Component
public class FrmReservas extends javax.swing.JFrame {
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes"}, 0);
    
//    @Autowired
//    private ServicioReserva servicioReserva;

    public FrmReservas() {
        initComponents();
        setTabla();
    }
    
    private void setTabla() {
        for (int hour = 7; hour < 20; hour++) {
            // Formato de hora para mostrar como "7-8", "8-9", etc., sin AM/PM
            String time = String.format("%d-%d", hour, hour + 1);

            // Añadir la fila con el intervalo de tiempo
            model.addRow(new Object[]{time, "LIBRE", "LIBRE", "LIBRE", "LIBRE", "LIBRE"});
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1030, 500));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 560, 310));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
