package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.AuthDto;
import gestorreservasaulas.dtos.UsuarioDto;
import gestorreservasaulas.entidades.Usuario;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.exceptions.UnauthorizedException;

import java.util.List;

public interface ServicioUsuario {

    UsuarioDto validarUsuario(AuthDto authDto) throws UnauthorizedException;

    UsuarioDto save(UsuarioDto newUser) throws ConflictException;

    List<UsuarioDto> getAll() throws NotFoundException;

    UsuarioDto updateById(Long id, UsuarioDto request) throws NotFoundException;

    void deleteById(Long id) throws NotFoundException;

    List<UsuarioDto> getByTipoOrUsernameStartingWith(String tipo, String username) throws NotFoundException;

    List<UsuarioDto> getByTipo(String tipo) throws NotFoundException;

    List<UsuarioDto> getByUsername(String username) throws NotFoundException;
}
