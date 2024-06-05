
package gestorreservasaulas.controllers;

import gestorreservasaulas.dtos.ReservaDto;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.servicios.ServicioReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "http://localhost:5173")
public class ReservasController {
    
    @Autowired
    private ServicioReserva servicioReservas;



    @PostMapping
    public ReservaDto save(@RequestBody ReservaDto reserva) throws NotFoundException {
        return servicioReservas.crearReserva(reserva);
    }





}
