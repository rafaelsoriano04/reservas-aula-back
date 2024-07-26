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

    @PutMapping("/{id}")
    public UsuarioDto updateById(@PathVariable Long id, @RequestBody UsuarioDto request) throws NotFoundException, ConflictException {
        return servicioUsuario.updateById(id, request);
    }

    @DeleteMapping("/{id}")
    public void updateById(@PathVariable Long id) throws NotFoundException {
        servicioUsuario.deleteById(id);
    }

    @GetMapping("/filtered")
    public List<UsuarioDto> findAllWithParams(@RequestParam(required = false) String username,
                                              @RequestParam(required = false) String tipo) {
        return servicioUsuario.findAllWithParams(username, tipo);
    }
}
