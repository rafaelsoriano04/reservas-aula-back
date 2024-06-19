package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Feriado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioFeriado extends JpaRepository<Feriado, Long> {
}
