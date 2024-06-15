package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Materia;
import gestorreservasaulas.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespositorioMateria extends JpaRepository<Materia, Long> {

}
