package gestorreservasaulas.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "espacios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Espacio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer piso;
    private Integer capacidad;
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "id_bloque")
    private Bloque bloque;

    @OneToMany(mappedBy = "espacio",orphanRemoval = true)
    private List<Reserva> listaReserva;

    @OneToMany(mappedBy = "espacio",orphanRemoval = true)
    private List<Horario> listaHorario= new ArrayList<>();
}
