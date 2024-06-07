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
@CrossOrigin(origins = "http://localhost:5173")
public class MateriaController
{
    @Autowired
    private ServicioMateria servicioMateria;

    @PostMapping
    public MateriaDto save(@RequestBody MateriaDto materiaDto) throws NotFoundException, ConflictException {
        return servicioMateria.save(materiaDto);
    }

    @GetMapping("/todos")
    public List<MateriaDto> getAll() throws NotFoundException {
        return servicioMateria.findTodos();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws NotFoundException {
        servicioMateria.eliminarMateria(id);
    }
    
}
