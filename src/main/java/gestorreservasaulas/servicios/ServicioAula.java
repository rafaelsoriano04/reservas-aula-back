package gestorreservasaulas.servicios;

import gestorreservasaulas.entidades.Aula;

public interface ServicioAula {
    Aula obtenerAula(String nombre);
    boolean editarAula(Aula aula);
}
