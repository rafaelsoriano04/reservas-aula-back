package gestorreservasaulas.servicios;

import gestorreservasaulas.entidades.Persona;

public interface ServicioPersona {
    Persona buscar(String cedula);

    Persona guardar(Persona persona);

    boolean existePorCedula(String cedula);
}
