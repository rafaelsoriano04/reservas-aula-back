package gestorreservasaulas.controllers;


import gestorreservasaulas.dtos.AuthDto;
import gestorreservasaulas.dtos.UsuarioDto;
import gestorreservasaulas.exceptions.UnauthorizedException;
import gestorreservasaulas.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @PostMapping
    public UsuarioDto validate(@RequestBody AuthDto authDto) throws UnauthorizedException {
        return servicioUsuario.validarUsuario(authDto);
    }
}
