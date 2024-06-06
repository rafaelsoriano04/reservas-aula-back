package gestorreservasaulas.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HorarioDto {
    private Long id;
    private String dia;
    private String hora;
    private Long id_materia;
    private Long id_persona;
    private Long id_espacio;
    private String nombre;
}
