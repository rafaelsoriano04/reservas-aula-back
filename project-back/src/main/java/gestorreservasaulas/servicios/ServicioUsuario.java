package gestorreservasaulas.servicios;

import gestorreservasaulas.entidades.Usuario;

public interface ServicioUsuario {

    boolean validarUsuario(String username, String contrasenia);

}
