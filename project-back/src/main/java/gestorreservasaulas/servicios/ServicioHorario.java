/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.HorarioDTO;
import gestorreservasaulas.entidades.Horario;

import java.util.List;


public interface ServicioHorario {
    Horario obtenerHorario(Long id);

    HorarioDTO crearHorario(HorarioDTO horarioDTO);

    List<HorarioDTO> obtenerHorariosPorAula(Long id);

    List<HorarioDTO> obtenerHorariosPorLabs(Long id);

    boolean eliminarHorario(Long id);
}
