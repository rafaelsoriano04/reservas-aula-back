package gestorreservasaulas.controllers;

import gestorreservasaulas.dtos.ReservaDto;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.servicios.ServicioReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = {"http://localhost:5173", "http://serviciosfisei.uta.edu.ec:9060", "http://172.21.123.13:9060"})
public class ReservasController {

    @Autowired
    private ServicioReserva servicioReservas;

    @PostMapping
    public ReservaDto save(@RequestBody ReservaDto reservaDto) throws NotFoundException {
        return servicioReservas.crearReserva(reservaDto);
    }
    @PutMapping("/{id}")
    public ReservaDto update(@PathVariable Long id, @RequestBody ReservaDto reservaDto) throws NotFoundException {
        return servicioReservas.actualizarReserva(id, reservaDto);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws NotFoundException {
        servicioReservas.eliminarReserva(id);
    }

    @GetMapping()
    public List<ReservaDto> getByWeek(
            @RequestParam Date fecha,
            @RequestParam Long id_espacio) throws NotFoundException {
        return servicioReservas.getByWeek(fecha, id_espacio);
    }
}
