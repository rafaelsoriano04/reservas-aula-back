package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Aula;
import gestorreservasaulas.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioAula extends JpaRepository<Aula, Long> {
    Optional<Aula> getByNombre(String nombre);
}
