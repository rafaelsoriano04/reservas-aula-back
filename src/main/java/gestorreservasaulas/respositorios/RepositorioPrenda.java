package gestorreservasaulas.respositorios;


import gestorreservasaulas.entidades.Prenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioPrenda extends JpaRepository<Prenda, Long> {

}
