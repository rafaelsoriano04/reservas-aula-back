package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Materia;
import gestorreservasaulas.entidades.Usuario;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespositorioMateria extends JpaRepository<Materia, Long> {
    List<Materia> findByNombreContaining(String nombre, Sort sort);

    List<Materia> findByCarrera(String carrera, Sort sort);

    List<Materia> findByNombreContainingAndCarrera(String nombre, String carrera, Sort sort);

}
