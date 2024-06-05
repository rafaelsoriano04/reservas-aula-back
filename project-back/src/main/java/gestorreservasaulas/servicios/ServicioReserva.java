package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.ReservaDto;
import gestorreservasaulas.entidades.Horario;
import gestorreservasaulas.entidades.Reserva;
import gestorreservasaulas.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ServicioReserva {
    //List<Reserva> obtenerReservaporDia(String dia);
    ReservaDto crearReserva(ReservaDto reserva) throws NotFoundException;
}
