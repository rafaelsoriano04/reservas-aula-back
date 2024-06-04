package gestorreservasaulas.dtos;

import gestorreservasaulas.entidades.Bloque;
import gestorreservasaulas.entidades.Horario;
import gestorreservasaulas.entidades.Reserva;
import gestorreservasaulas.enums.Estado;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AulaDTO {
    private Long id;
    private String nombre;
    private Integer piso;
    private Integer capacidad;
    private Long id_bloque;
}
