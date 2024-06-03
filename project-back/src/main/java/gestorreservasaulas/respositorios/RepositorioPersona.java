package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioPersona extends JpaRepository<Persona, Long> {
    Optional<Persona> findByCedula(String cedula);

    boolean existsByCedula(String cedula);
}
