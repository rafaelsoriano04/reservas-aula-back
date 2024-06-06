package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface RepositorioReserva extends JpaRepository<Reserva, Long> {

    // Método para buscar reservas por hora, fecha y laboratorio
    @Query("SELECT r FROM Reserva r WHERE r.hora = :horario AND r.fecha = :fecha AND r.espacio.id = :id_espacio")
    Reserva getPorHoraFecha(@Param("horario") String horario, @Param("fecha") Date fecha, @Param("id_espacio") Long id_espacio);
}
