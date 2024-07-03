package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Persona;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioPersona extends JpaRepository<Persona, Long> {
    List<Persona> findAllByTipo(String tipo, Sort sort);

    List<Persona> findAllByTipoAndCedulaStartsWith(String tipo,
                                                   String cedula,
                                                   Sort sort);

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

    boolean existsByCedula(String cedula);
}
