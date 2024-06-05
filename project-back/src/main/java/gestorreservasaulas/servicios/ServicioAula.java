package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.AulaDto;
import gestorreservasaulas.entidades.Aula;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;

import java.util.List;

public interface ServicioAula {

    Aula obtenerAulaPorId(Long id);
    AulaDto editarAula(Long id, AulaDto aula) throws NotFoundException, ConflictException;
    List<AulaDto> findByBloque(Long id_bloque) throws NotFoundException;
    AulaDto save(AulaDto aulaDTO) throws NotFoundException, ConflictException;
    void eliminarAula(Long id) throws NotFoundException;
}
