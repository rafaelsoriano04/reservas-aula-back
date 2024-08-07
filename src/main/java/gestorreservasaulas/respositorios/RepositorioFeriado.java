package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Feriado;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RepositorioFeriado extends JpaRepository<Feriado, Long> {
    @Query("SELECT f " +
            "FROM Feriado f " +
            "WHERE (:inicio IS NULL OR f.inicio >= :inicio) " +
            "AND (:fin IS NULL OR f.inicio <= :fin)")
    List<Feriado> findAllByParams(Date inicio, Date fin, Sort sort);
}
