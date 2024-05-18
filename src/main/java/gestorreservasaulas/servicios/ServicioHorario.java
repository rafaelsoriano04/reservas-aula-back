/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorreservasaulas.servicios;

import gestorreservasaulas.entidades.Horario;


public interface ServicioHorario {
    Horario obtenerHorario(Long id);
    Boolean crearHorario(Horario horario);
}
