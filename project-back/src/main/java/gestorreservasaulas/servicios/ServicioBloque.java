package gestorreservasaulas.servicios;

import gestorreservasaulas.entidades.Bloque;
import java.util.List;

public interface ServicioBloque {

    Bloque obtenerBloque(Long id);
    List<Bloque> obtenerTodosBloques();

}