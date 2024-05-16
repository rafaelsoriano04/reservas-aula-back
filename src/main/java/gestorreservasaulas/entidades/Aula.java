package gestorreservasaulas.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Entity
@Table(name = "aulas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int piso;
    private int capacidad;

    @OneToMany(mappedBy = "aulas", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> listaReservas;

    @OneToMany(mappedBy = "aulas", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Horario> listaHorario;
    
    @ManyToOne
    @JoinColumn(name="id_bloque")
    private Bloque bloque;


}
