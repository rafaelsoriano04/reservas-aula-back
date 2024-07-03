package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
    Optional<Usuario> getByUsername(String username);
    List<Usuario> findByTipoAndUsernameStartingWith(String tipo, String username, Sort sort);
    List<Usuario> findByTipo(String tipo, Sort sort);
    List<Usuario> findByUsernameStartingWith(String username, Sort sort);
}

