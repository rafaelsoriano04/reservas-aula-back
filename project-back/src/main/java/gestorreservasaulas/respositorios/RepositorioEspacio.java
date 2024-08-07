package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Bloque;
import gestorreservasaulas.entidades.Espacio;
import gestorreservasaulas.entidades.Usuario;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioEspacio extends JpaRepository<Espacio, Long> {
    List<Espacio> findAllByTipo(String tipo, Sort sort);

    List<Espacio> findAllByNombreContains(String nombre, Sort sort);

    List<Espacio> findAllByBloque(Bloque bloque, Sort sort);

    List<Espacio> findAllByTipoAndNombreContains(String tipo, String nombre, Sort sort);

    List<Espacio> findAllByTipoAndBloque(String tipo, Bloque bloque, Sort sort);

    List<Espacio> findAllByBloqueAndNombreContains(Bloque bloque, String nombre, Sort sort);

    List<Espacio> findAllByBloqueAndNombreContainsAndTipo(Bloque bloque, String nombre, String tipo, Sort sort);

    Optional<Espacio> findByNombre(String nombre);

    @Query("SELECT e " +
            "FROM Espacio e " +
            "WHERE (:nombre IS NULL OR e.nombre LIKE :nombre) " +
            "AND (:tipo IS NULL OR e.tipo = :tipo) " +
            "AND (:bloque IS NULL OR e.bloque = :bloque)")
    List<Espacio> findAllByParams(String nombre, String tipo, Bloque bloque, Sort sort);
}
