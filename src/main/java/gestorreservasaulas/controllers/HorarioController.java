package gestorreservasaulas.controllers;

import gestorreservasaulas.dtos.HorarioDto;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.servicios.ServicioHorario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horario")
@CrossOrigin(origins = {"http://localhost:5173", "http://serviciosfisei.uta.edu.ec:9060", "http://172.21.123.13:9060"})
public class HorarioController {
    @Autowired
    private ServicioHorario servicioHorario;

    @GetMapping("/aula/{id}")
    public List<HorarioDto> getByAula(@PathVariable Long id) {
        return servicioHorario.obtenerHorariosPorAula(id);
    }

    @GetMapping("/lab/{id}")
    public List<HorarioDto> getByLabs(@PathVariable Long id) {
        return servicioHorario.obtenerHorariosPorLabs(id);
    }

    @GetMapping("/especial/{id}")
    public List<HorarioDto> getByEspecial(@PathVariable Long id) {
        return servicioHorario.obtenerHorariosPorEspecial(id);
    }

    @PostMapping
    public HorarioDto save(@RequestBody HorarioDto horario) throws ConflictException, NotFoundException {
        return servicioHorario.crearHorario(horario);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return servicioHorario.eliminarHorario(id);
    }

}
