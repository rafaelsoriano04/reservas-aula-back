package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Feriado;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RepositorioFeriado extends JpaRepository<Feriado, Long> {
    List<Feriado> findByInicioAfter(Date inicio, Sort sort);
    List<Feriado> findByFinBefore(Date fin, Sort sort);
    List<Feriado> findByInicioAfterAndFinBefore(Date inicio, Date fin, Sort sort);
}
