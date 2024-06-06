package gestorreservasaulas.controllers;

import gestorreservasaulas.dtos.ReservaDto;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.servicios.ServicioReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createReservation(@RequestBody ReservaDto reservaDto) {
        try {
            ReservaDto createdReserva = servicioReservas.crearReserva(reservaDto);
            return ResponseEntity.ok(createdReserva);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @GetMapping("/semana")
    public List<ReservaDto> getReservations(
            @RequestParam Date fecha,
            @RequestParam Long idEspacio) {
        return servicioReservas.getReservasPorFecha(fecha, idEspacio);
    }
}
