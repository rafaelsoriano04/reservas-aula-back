package gestorreservasaulas.controllers;


import gestorreservasaulas.dtos.AuthDto;
import gestorreservasaulas.dtos.EspacioDto;
import gestorreservasaulas.dtos.UsuarioDto;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.exceptions.UnauthorizedException;
import gestorreservasaulas.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @PostMapping("login")
    public UsuarioDto validate(@RequestBody AuthDto authDto) throws UnauthorizedException {
        return servicioUsuario.validarUsuario(authDto);
    }

    @PostMapping
    public UsuarioDto save(@RequestBody UsuarioDto usuarioDto) throws ConflictException {
        return servicioUsuario.save(usuarioDto);
    }

    @GetMapping
    public List<UsuarioDto> getAll() throws NotFoundException {
        return servicioUsuario.getAll();
    }

    @GetMapping("/filter/{tipo}/{username}")
    public List<UsuarioDto> getByTipoOrUsernameStartingWith(@PathVariable String tipo, @PathVariable String username) throws NotFoundException {
        return servicioUsuario.getByTipoOrUsernameStartingWith(tipo, username);
    }

    @GetMapping("/filter-tipo/{tipo}")
    public List<UsuarioDto> getByTipo(@PathVariable String tipo) throws NotFoundException {
        return servicioUsuario.getByTipo(tipo);
    }

    @GetMapping("/filter-username/{username}")
    public List<UsuarioDto> getByUsernameStartingWith(@PathVariable String username) throws NotFoundException {
        return servicioUsuario.getByUsername(username);
    }

    @PutMapping("/{id}")
    public UsuarioDto updateById(@PathVariable Long id, @RequestBody UsuarioDto request) throws NotFoundException, ConflictException {
        return servicioUsuario.updateById(id, request);
    }

    @DeleteMapping("/{id}")
    public void updateById(@PathVariable Long id) throws NotFoundException {
        servicioUsuario.deleteById(id);
    }
}
