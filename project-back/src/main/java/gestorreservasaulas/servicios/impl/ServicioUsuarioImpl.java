package gestorreservasaulas.servicios.impl;


import gestorreservasaulas.dtos.AuthDto;
import gestorreservasaulas.dtos.UsuarioDto;
import gestorreservasaulas.entidades.Usuario;
import gestorreservasaulas.exceptions.UnauthorizedException;
import gestorreservasaulas.respositorios.RepositorioUsuario;
import gestorreservasaulas.servicios.ServicioUsuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuarioImpl implements ServicioUsuario {

    @Autowired
    RepositorioUsuario repositorioUsuario;

    private final ModelMapper modelMapper;

    @Autowired
    public ServicioUsuarioImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public UsuarioDto validarUsuario(AuthDto authDto) throws UnauthorizedException {
        Usuario usuario = repositorioUsuario.getByUsername(authDto.getUsername()).orElse(null);
        if (usuario == null) {
            throw new UnauthorizedException("Credenciales inválidas");
        }
        if (!usuario.getContrasenia().equals(authDto.getPassword())) {
            throw new UnauthorizedException("Credenciales inválidas");
        }
        return usuarioToDto(usuario);
    }

    private UsuarioDto usuarioToDto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDto.class);
    }
}
