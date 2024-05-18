package gestorreservasaulas.servicios;

import gestorreservasaulas.entidades.Reserva;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ServicioReserva {
    List<Reserva> obtenerReservaporDia(String dia);

}
