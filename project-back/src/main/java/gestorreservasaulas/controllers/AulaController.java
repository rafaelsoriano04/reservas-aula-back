package gestorreservasaulas.controllers;

import gestorreservasaulas.dtos.AulaDTO;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.servicios.ServicioAula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aula")
@CrossOrigin(origins = "http://localhost:5173")
public class AulaController {

    @Autowired
    private ServicioAula servicioAula;

    @GetMapping("/bloque/{id}")
    public List<AulaDTO> getAllByBloque(@PathVariable Long id) throws NotFoundException {
        return servicioAula.findByBloque(id);
    }

    @PostMapping
    public AulaDTO save(@RequestBody AulaDTO aulaDTO) throws NotFoundException, ConflictException {
        return servicioAula.save(aulaDTO);
    }

    @PutMapping("/{id}")
    public AulaDTO update(@PathVariable Long id, @RequestBody AulaDTO request) throws NotFoundException, ConflictException {
        return servicioAula.editarAula(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws NotFoundException {
        servicioAula.eliminarAula(id);
    }
}