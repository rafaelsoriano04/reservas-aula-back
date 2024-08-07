package gestorreservasaulas.respositorios;

import gestorreservasaulas.dtos.PersonaDto;
import gestorreservasaulas.entidades.Persona;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioPersona extends JpaRepository<Persona, Long> {
    List<Persona> findAllByTipo(String tipo, Sort sort);

    List<Persona> findAllByTipoAndCedulaStartsWith(String tipo, String cedula, Sort sort);


    List<Persona> findAllByTipoAndNombreContainsOrApellidoContains(String tipo,
                                                                   String nombre,
                                                                   String apellido,
                                                                   Sort sort);

    List<Persona> findAllByTipoAndCedulaStartsWithAndNombreContainsOrApellidoContains(String tipo,
                                                                                      String cedula,
                                                                                      String nombre,
                                                                                      String apellido,
                                                                                      Sort sort);

    Optional<Persona> findByCedula(String cedula);

    @Query("SELECT p " +
            "FROM Persona p " +
            "WHERE (p.tipo = 'Docente') " +
            "AND ((:cedula IS NULL OR p.cedula LIKE :cedula) " +
            "AND (:nombre IS NULL OR p.nombre LIKE :nombre OR p.apellido LIKE :nombre))")
    List<Persona> findAllDocentesWithParams(String cedula, String nombre, Sort sort);


    boolean existsByCedula(String cedula);
}
