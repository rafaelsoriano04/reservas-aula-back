package gestorreservasaulas.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HorarioDto {
    private Long id;
    private String dia;
    private String hora;
    private String materia;
    private Long id_aula;
    private Long id_laboratorio;
    private Long id_persona;
}
