package gestorreservasaulas.controllers;

import gestorreservasaulas.dtos.AulaDTO;
import gestorreservasaulas.servicios.ServicioAula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aula")
public class AulaController {

    @Autowired
    private ServicioAula servicioAula;

    @GetMapping("/bloque/{id}")
    public List<AulaDTO> getAllByBloque(@PathVariable Long id) {
        return servicioAula.findByBloque(id);
    }

    @PostMapping
    public AulaDTO save(@RequestBody AulaDTO aulaDTO) {
        return servicioAula.save(aulaDTO);
    }


}
