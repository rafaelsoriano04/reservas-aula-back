package gestorreservasaulas.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class HorarioDto {
    private Long id;
    private String dia;
    private String hora;
    private String materia;
    private Long id_persona;
    private Long id_espacio;
}
