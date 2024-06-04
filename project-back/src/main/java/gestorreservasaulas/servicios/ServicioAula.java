package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.AulaDTO;
import gestorreservasaulas.entidades.Aula;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;

import java.util.List;

public interface ServicioAula {

    Aula obtenerAulaPorId(Long id);
    Aula obtenerAula(String nombre);
    AulaDTO editarAula(Long id, AulaDTO aula) throws NotFoundException, ConflictException;
    List<AulaDTO> findByBloque(Long id_bloque) throws NotFoundException;
    AulaDTO save(AulaDTO aulaDTO) throws NotFoundException, ConflictException;
    void eliminarAula(Long id) throws NotFoundException;
}
