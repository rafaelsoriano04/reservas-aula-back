package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.PersonaDto;
import gestorreservasaulas.entidades.Persona;
import gestorreservasaulas.exceptions.NotFoundException;

public interface ServicioPersona {
    PersonaDto buscar(String cedula) throws NotFoundException;

    PersonaDto guardar(PersonaDto persona) throws NotFoundException;

    boolean existePorCedula(String cedula);
}
