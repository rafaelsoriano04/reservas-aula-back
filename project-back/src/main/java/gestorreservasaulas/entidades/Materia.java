package gestorreservasaulas.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "materias")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String nombre;
    @Column()
    private String carrera;

    @OneToMany( mappedBy = "materia")
    List<Horario> listaHorario;
}
