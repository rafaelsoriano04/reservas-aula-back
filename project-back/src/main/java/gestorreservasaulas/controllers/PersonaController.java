package gestorreservasaulas.controllers;

import gestorreservasaulas.dtos.HorarioDto;
import gestorreservasaulas.dtos.LaboratorioDto;
import gestorreservasaulas.dtos.PersonaDto;
import gestorreservasaulas.dtos.UsuarioDto;
import gestorreservasaulas.entidades.Horario;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.servicios.ServicioPersona;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "http://localhost:5173")
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

    @GetMapping("/docente")
    public List<PersonaDto> getAll() {
        return servicioPersona.listarDocentes();
    }

    @GetMapping("/docente-nombre/{nombre}")
    public List<PersonaDto> getByNombreApellido(@PathVariable String nombre) {
        return servicioPersona.getByNombreApellido(nombre);
    }

    @GetMapping("/docente-cedula/{cedula}")
    public List<PersonaDto> getByTipoOrUsernameStartingWith(@PathVariable String cedula) {
        return servicioPersona.getByCedula(cedula);
    }

    @GetMapping("/docente/{cedula}/{nombre}")
    public List<PersonaDto> getByTipoOrUsernameStartingWith(@PathVariable String cedula, @PathVariable String nombre) {
        return servicioPersona.getByCedulaNombreApellido(cedula, nombre);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws ConflictException  {
        servicioPersona.eliminarPersona(id);
    }

}
