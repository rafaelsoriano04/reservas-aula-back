/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorreservasaulas.servicios;

import gestorreservasaulas.entidades.Horario;
import java.util.List;


public interface ServicioHorario {
    Horario obtenerHorario(Long id);
    Boolean crearHorario(Horario horario);
    List<Horario> obtenerHorariosPorAula (Long id);
    List<Horario> obtenerHorariosPorLabs (Long id);
    void eliminarHorario(Horario id);
}
