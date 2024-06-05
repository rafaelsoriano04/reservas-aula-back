package gestorreservasaulas.entidades;

import jakarta.persistence.*;
import java.util.ArrayList;
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

    @Column(unique = true)
    private String nombre;

    private Integer piso;
    private Integer capacidad;

    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Reserva> listaReservas = new ArrayList<>();

    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Horario> listaHorario = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_bloque")
    private Bloque bloque;

}
