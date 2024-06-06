package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioEspacio extends JpaRepository<Espacio, Long> {

    @Query("SELECT e FROM Espacio e WHERE e.bloque.id = :id_bloque")
    List<Espacio> findByBloque(@Param("id_bloque") Long id_bloque);

    Optional<Espacio> findByNombre(String nombre);

}
