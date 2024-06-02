
package gestorreservasaulas.interfaces;

import gestorreservasaulas.entidades.Aula;
import gestorreservasaulas.entidades.Horario;
import gestorreservasaulas.entidades.Laboratorio;
import gestorreservasaulas.servicios.ServicioHorario;

import javax.swing.table.DefaultTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


  @Component
public class FrmReservas extends javax.swing.JFrame {
private final DefaultTableModel model = new DefaultTableModel(new String[]{"Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes"}, 0);
     @Autowired
    private ServicioHorario servicioHorario;

    private Aula aula;
    private Laboratorio laboratorio;
   
    public FrmReservas() {
        initComponents();
        initializeTable();
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

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
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
        switch (day) {
            case "Lunes":
                return 1;
            case "Martes":
                return 2;
            case "Miercoles":
                return 3;
            case "Jueves":
                return 4;
            case "Viernes":
                return 5;
            default:
                return -1;
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(202, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
