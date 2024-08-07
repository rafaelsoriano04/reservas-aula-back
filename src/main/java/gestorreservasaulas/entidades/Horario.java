package gestorreservasaulas.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "horarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dia;
    private String hora;
    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;
    @ManyToOne
    @JoinColumn(name = "id_espacio")
    private Espacio espacio;
    @ManyToOne
    @JoinColumn(name = "id_persona")
    private Persona docente;

}
