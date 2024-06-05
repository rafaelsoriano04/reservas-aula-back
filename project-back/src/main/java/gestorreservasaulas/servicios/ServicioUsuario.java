package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.AuthDto;
import gestorreservasaulas.dtos.UsuarioDto;
import gestorreservasaulas.entidades.Usuario;
import gestorreservasaulas.exceptions.UnauthorizedException;

public interface ServicioUsuario {

    UsuarioDto validarUsuario(AuthDto authDto) throws UnauthorizedException;

}
