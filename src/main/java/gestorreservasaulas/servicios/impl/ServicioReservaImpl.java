package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.entidades.Reserva;
import gestorreservasaulas.respositorios.RepositorioReserva;
import gestorreservasaulas.servicios.ServicioReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioReservaImpl implements ServicioReserva {
@Autowired
RepositorioReserva repositorioReserva;
    @Override
    public List<Reserva> obtenerReservaporDia(String dia) {
        return repositorioReserva.getByDia(dia);
    }
}
