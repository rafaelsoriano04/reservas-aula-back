package gestorreservasaulas.entidades;

import gestorreservasaulas.enums.Estado;
import gestorreservasaulas.enums.Prenda;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dia;
    private String hora;
    private String fecha;

    @Enumerated(value = EnumType.STRING)
    private Prenda prenda;

    @ManyToOne
    @JoinColumn(name = "id_aula")
    private Aula aula;

    @ManyToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "id_laboratorio")
    private Laboratorio laboratorio;

}
