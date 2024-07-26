package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
    Optional<Usuario> getByUsername(String username);

    @Query("SELECT u " +
            "FROM Usuario u " +
            "WHERE (:username IS NULL OR u.username LIKE :username) " +
            "AND (:tipo IS NULL OR u.tipo = :tipo)")
    List<Usuario> findAllWithParams(String username, String tipo, Sort sort);
}


