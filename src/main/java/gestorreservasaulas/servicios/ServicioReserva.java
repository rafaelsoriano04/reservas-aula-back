package gestorreservasaulas.servicios;

import gestorreservasaulas.entidades.Horario;
import gestorreservasaulas.entidades.Reserva;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ServicioReserva {
    List<Reserva> obtenerReservaporDia(String dia);
    Boolean crearReserva(Reserva reserva);

}
