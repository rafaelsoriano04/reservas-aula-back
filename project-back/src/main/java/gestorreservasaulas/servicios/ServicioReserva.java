package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.ReservaDto;
import gestorreservasaulas.exceptions.NotFoundException;

import java.sql.Date;
import java.util.List;


public interface ServicioReserva {
    //List<Reserva> obtenerReservaporDia(String dia);
    ReservaDto crearReserva(ReservaDto reserva) throws NotFoundException;

    List<ReservaDto> getByWeek(Date fecha, Long id_espacio) throws NotFoundException ;
}
