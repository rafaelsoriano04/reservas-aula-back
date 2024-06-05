package gestorreservasaulas.dtos;

import gestorreservasaulas.entidades.Aula;
import gestorreservasaulas.entidades.Laboratorio;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
public class BloqueDto {
    private Long id;
    String nombre;
}
