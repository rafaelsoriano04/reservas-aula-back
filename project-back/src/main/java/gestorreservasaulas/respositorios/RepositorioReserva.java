package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioReserva extends JpaRepository<Reserva, Long> {

    // Método para buscar reservas por hora, fecha y laboratorio
    @Query("SELECT r FROM Reserva r WHERE r.hora = :horario AND r.fecha = :fecha AND r.laboratorio.id = :idLaboratorio")
    Reserva getPorHoraFechaYLaboratorio(@Param("horario") String horario, @Param("fecha") Date fecha, @Param("idLaboratorio") Long idLaboratorio);

    // Método para buscar reservas por hora, fecha y aula
    @Query("SELECT r FROM Reserva r WHERE r.hora = :hora AND r.fecha = :fecha AND r.aula.id = :idAula")
    Reserva getPorHoraFechaYAula(@Param("hora") String hora, @Param("fecha") Date fecha, @Param("idAula") Long idAula);


}
