package gestorreservasaulas.dtos;

import lombok.Data;

@Data
public class LaboratorioDto {
    private Long id;
    private String nombre;
    private Integer piso;
    private Integer capacidad;
    private Long id_bloque;
}
