package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Materia;
import gestorreservasaulas.entidades.Usuario;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespositorioMateria extends JpaRepository<Materia, Long> {

    @Query("SELECT m " +
            "FROM Materia m " +
            "WHERE (:nombre IS NULL OR m.nombre LIKE :nombre) " +
            "AND (:carrera IS NULL OR m.carrera LIKE :carrera)")
    List<Materia> findAllWithParams(String nombre, String carrera, Sort sort);
}
