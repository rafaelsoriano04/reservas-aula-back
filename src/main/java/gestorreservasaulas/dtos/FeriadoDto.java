package gestorreservasaulas.dtos;

import lombok.Data;

import java.sql.Date;

@Data
public class FeriadoDto {
    private Long id;
    private Date inicio;
    private Date fin;
    private String nombre;
}
