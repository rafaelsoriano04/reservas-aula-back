package gestorreservasaulas.dtos;

import lombok.Data;

@Data
public class UsuarioDto {
    Long id;
    String username;
    String newPassword;
    String tipo;
}
