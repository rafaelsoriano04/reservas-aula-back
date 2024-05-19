package gestorreservasaulas.servicios;

import gestorreservasaulas.entidades.Aula;

import java.util.List;

public interface ServicioAula {
    Aula obtenerAula(String nombre);
    boolean editarAula(Aula aula);
    List<Aula> findByBloque(Long id_bloque);

    Aula crearAula(Aula aula);
}
