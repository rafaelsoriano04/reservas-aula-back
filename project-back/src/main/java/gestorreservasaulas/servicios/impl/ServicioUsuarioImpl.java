package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.AuthDto;
import gestorreservasaulas.dtos.UsuarioDto;
import gestorreservasaulas.entidades.Usuario;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.exceptions.UnauthorizedException;
import gestorreservasaulas.respositorios.RepositorioUsuario;
import gestorreservasaulas.servicios.ServicioUsuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioUsuarioImpl implements ServicioUsuario {
    @Autowired
    RepositorioUsuario repositorioUsuario;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ServicioUsuarioImpl() {
        this.modelMapper = new ModelMapper();
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UsuarioDto validarUsuario(AuthDto authDto) throws UnauthorizedException {
        Usuario usuario = repositorioUsuario.getByUsername(authDto.getUsername()).orElse(null);
        if (usuario == null) {
            throw new UnauthorizedException("Credenciales inválidas");
        }
        if (!passwordEncoder.matches(authDto.getPassword(), usuario.getContrasenia())) {
            throw new UnauthorizedException("Credenciales inválidas");
        }
        return usuarioToDto(usuario);
    }

    @Override
    public UsuarioDto save(UsuarioDto usuarioDto) throws ConflictException {
        Usuario usuario = repositorioUsuario.getByUsername(usuarioDto.getUsername()).orElse(null);
        usuarioDto.setNewPassword(passwordEncoder.encode(usuarioDto.getNewPassword()));
        if (usuario != null) {
            throw new ConflictException("El usuario ya existe");
        }
        return usuarioToDto(repositorioUsuario.save(dtoToUsuario(usuarioDto)));
    }

    @Override
    public List<UsuarioDto> getAll() throws NotFoundException {
        List<Usuario> listaUsuarios = repositorioUsuario.findAll(Sort.by(Sort.Direction.ASC, "username"));
        if (listaUsuarios.isEmpty()) {
            throw new NotFoundException("No hay usuarios");
        }
        return listaUsuarios.stream().map(this::usuarioToDto).collect(Collectors.toList());
    }

    @Override
    public UsuarioDto updateById(Long id, UsuarioDto request) throws NotFoundException {
        Usuario usuario = repositorioUsuario.findById(id).orElseThrow(() -> new NotFoundException("No existe el usuario"));

        if (!request.getUsername().isEmpty()) {
            usuario.setUsername(request.getUsername());
        }
        if (!request.getNewPassword().isEmpty()) {
            usuario.setContrasenia(request.getNewPassword());
        }
        if (request.getTipo() != null) {
            usuario.setTipo(request.getTipo());
        }

        return usuarioToDto(repositorioUsuario.save(usuario));
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        repositorioUsuario.findById(id).orElseThrow(() -> new NotFoundException("No existe el usuario"));

        repositorioUsuario.deleteById(id);
    }

    @Override
    public List<UsuarioDto> findAllWithParams(String username, String tipo) {
        username = username != null ? "%".concat(username).concat("%") : null;
        List<Usuario> usuarios = repositorioUsuario.findAllWithParams(username, tipo, Sort.by("username"));
        return usuarios.stream().map(this::usuarioToDto).collect(Collectors.toList());
    }

    private UsuarioDto usuarioToDto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDto.class);
    }

    private Usuario dtoToUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);
        usuario.setContrasenia(usuarioDto.getNewPassword());
        return usuario;
    }
}
