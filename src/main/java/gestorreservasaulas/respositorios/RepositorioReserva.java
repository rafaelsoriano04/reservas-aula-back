package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioReserva extends JpaRepository<Reserva, Long> {

    List<Reserva> getByDia (String dia);

}
