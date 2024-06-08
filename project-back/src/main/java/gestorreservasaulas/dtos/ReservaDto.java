
package gestorreservasaulas.dtos;

import java.sql.Date;
import lombok.Data;

@Data
public class ReservaDto {
    private Long id;
    private String hora;
    private Date fecha;
    private String asunto;
    private Long id_espacio;
    private Long id_persona;
}
