package gestorreservasaulas.servicios;

import gestorreservasaulas.entidades.Bloque;
import gestorreservasaulas.exceptions.NotFoundException;

import java.util.List;

public interface ServicioBloque {

    Bloque obtenerBloque(Long id) throws NotFoundException;
    List<Bloque> obtenerTodosBloques();

}