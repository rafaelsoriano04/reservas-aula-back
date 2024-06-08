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
@CrossOrigin(origins = "http://localhost:5173") // Asegúrate que el origen sea correcto
public class ReservasController {

    @Autowired
    private ServicioReserva servicioReservas;

    @PostMapping
    public ReservaDto save(@RequestBody ReservaDto reservaDto) throws NotFoundException {
        return servicioReservas.crearReserva(reservaDto);
    }

    @GetMapping()
    public List<ReservaDto> getByWeek(
            @RequestParam Date fecha,
            @RequestParam Long id_espacio) throws NotFoundException {
        return servicioReservas.getByWeek(fecha, id_espacio);
    }
}
