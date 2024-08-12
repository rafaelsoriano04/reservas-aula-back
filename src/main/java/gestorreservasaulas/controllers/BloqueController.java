package gestorreservasaulas.controllers;

import gestorreservasaulas.dtos.BloqueDto;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.servicios.ServicioBloque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bloque")
@CrossOrigin(origins = "http://serviciosfisei.uta.edu.ec:9060")
public class BloqueController {
    @Autowired
    private ServicioBloque servicioBloque;

    @GetMapping
    public List<BloqueDto> getAll() throws NotFoundException {
        return servicioBloque.getAll();
    }
}