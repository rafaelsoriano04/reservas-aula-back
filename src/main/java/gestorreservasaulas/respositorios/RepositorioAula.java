package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Aula;
import gestorreservasaulas.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RepositorioAula extends JpaRepository<Aula, Long> {

    Optional<Aula> getByNombre(String nombre);

    @Query("SELECT a FROM Aula a WHERE a.bloque.id = :id_bloque")
    List<Aula> findByBloque(@Param("id_bloque") Long id_bloque);

}
