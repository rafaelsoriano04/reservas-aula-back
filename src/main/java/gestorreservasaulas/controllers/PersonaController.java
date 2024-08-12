package gestorreservasaulas.controllers;

import gestorreservasaulas.dtos.PersonaDto;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.servicios.ServicioPersona;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persona")
@CrossOrigin(origins = "http://serviciosfisei.uta.edu.ec:9060")
public class PersonaController {
    @Autowired
    private ServicioPersona servicioPersona;

    @PostMapping
    public PersonaDto save(@RequestBody PersonaDto personaDto) throws NotFoundException, ConflictException {
        return servicioPersona.guardar(personaDto);
    }

    @GetMapping("/{cedula}")
    public PersonaDto getByCedula(@PathVariable String cedula) throws NotFoundException {
        return servicioPersona.buscar(cedula);
    }

    @GetMapping("/docente/filtered")
    public List<PersonaDto> getDocentesFiltered(@RequestParam(required = false) String cedula, @RequestParam(required = false) String nombre) {
        return servicioPersona.findAllDocentesWithParams(cedula, nombre);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws ConflictException {
        servicioPersona.eliminarPersona(id);
    }

}
