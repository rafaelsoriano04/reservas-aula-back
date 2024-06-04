package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.AulaDTO;
import gestorreservasaulas.entidades.Aula;

import java.util.List;

public interface ServicioAula {

    Aula obtenerAulaPorId(Long id);
    Aula obtenerAula(String nombre);
    boolean editarAula(Aula aula);
    List<AulaDTO> findByBloque(Long id_bloque);
    AulaDTO save(AulaDTO aulaDTO);
    void eliminarAula(Long id);
}
