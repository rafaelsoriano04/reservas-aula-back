/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.HorarioDto;
import gestorreservasaulas.entidades.Horario;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;

import java.util.List;


public interface ServicioHorario {
    Horario obtenerHorario(Long id);

    HorarioDto crearHorario(HorarioDto horarioDTO) throws ConflictException, NotFoundException;

    List<HorarioDto> obtenerHorariosPorAula(Long id);

    List<HorarioDto> obtenerHorariosPorLabs(Long id);

    List<HorarioDto> obtenerHorariosPorEspecial(Long id);

    boolean eliminarHorario(Long id);
}
