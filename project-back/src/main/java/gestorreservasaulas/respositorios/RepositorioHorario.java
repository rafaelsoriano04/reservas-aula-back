package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Horario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioHorario extends JpaRepository<Horario, Long> {

    @Query("SELECT h FROM Horario h WHERE h.espacio.id = :id_espacio AND h.espacio.tipo = :tipo")
    List<Horario> horarios(@Param("id_espacio") Long id_espacio, @Param("tipo") String tipo);

    @Modifying
    @Query("DELETE FROM Horario h WHERE h.id = :id_horario")
    int deleteHorario(@Param("id_horario") Long idHorario);
}
