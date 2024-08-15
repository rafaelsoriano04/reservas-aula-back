package gestorreservasaulas.controllers;

import gestorreservasaulas.dtos.MateriaDto;
import gestorreservasaulas.entidades.Materia;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.servicios.ServicioMateria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materia")
@CrossOrigin(origins = {"http://localhost:5173", "http://serviciosfisei.uta.edu.ec:9060"})
public class MateriaController {
    @Autowired
    private ServicioMateria servicioMateria;

    @PostMapping
    public MateriaDto save(@RequestBody MateriaDto materiaDto) throws NotFoundException, ConflictException {
        return servicioMateria.save(materiaDto);
    }

    @GetMapping
    public List<MateriaDto> getAll() throws NotFoundException {
        return servicioMateria.findTodos();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws ConflictException {
        servicioMateria.eliminarMateria(id);
    }

    @GetMapping("/filtered")
    public List<MateriaDto> findAllWithParams(@RequestParam(required = false) String nombre,
                                              @RequestParam(required = false) String carrera,
                                              @RequestParam(required = false) String curso) {
        return servicioMateria.findAllWithParams(nombre, carrera, curso);
    }
}
