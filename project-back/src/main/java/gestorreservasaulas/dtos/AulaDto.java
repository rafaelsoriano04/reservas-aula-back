package gestorreservasaulas.dtos;

import lombok.Data;

@Data
public class AulaDto {
    private Long id;
    private String nombre;
    private Integer piso;
    private Integer capacidad;
    private Long id_bloque;
}
