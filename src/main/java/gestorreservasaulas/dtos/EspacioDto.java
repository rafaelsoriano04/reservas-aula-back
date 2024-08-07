package gestorreservasaulas.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EspacioDto {
    private Long id;
    private String nombre;
    private Integer piso;
    private Integer capacidad;
    private String tipo;
    private Long id_bloque;
}
