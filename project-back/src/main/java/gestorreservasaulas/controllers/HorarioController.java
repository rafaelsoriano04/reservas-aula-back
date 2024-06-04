package gestorreservasaulas.controllers;

import gestorreservasaulas.dtos.HorarioDTO;
import gestorreservasaulas.entidades.Horario;
import gestorreservasaulas.servicios.ServicioHorario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horario")
public class HorarioController {

    @Autowired
    private ServicioHorario servicioHorario;

    @GetMapping("/aula/{id}")
    public List<HorarioDTO> getByAula(@PathVariable Long id) {
        return servicioHorario.obtenerHorariosPorAula(id);
    }

    @GetMapping("/lab/{id}")
    public List<Horario> getByLabs(@PathVariable Long id) {
        return servicioHorario.obtenerHorariosPorLabs(id);
    }

    @PostMapping
    public Horario saveHorario(@RequestBody Horario horario) {
        return servicioHorario.crearHorario(horario);
    }

    @DeleteMapping("/{id}")
    public void deleteHorario(@PathVariable Long id) {
        servicioHorario.eliminarHorario(id);
    }

}
