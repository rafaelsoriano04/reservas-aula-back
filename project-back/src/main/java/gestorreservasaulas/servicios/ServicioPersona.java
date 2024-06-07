package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.PersonaDto;
import gestorreservasaulas.entidades.Persona;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;

import java.util.List;

public interface ServicioPersona {
    Persona findById(Long id) throws NotFoundException;

    PersonaDto buscar(String cedula) throws NotFoundException;

    PersonaDto guardar(PersonaDto persona) throws NotFoundException;

    boolean existePorCedula(String cedula);
     List<PersonaDto> listarDocentes();

}
