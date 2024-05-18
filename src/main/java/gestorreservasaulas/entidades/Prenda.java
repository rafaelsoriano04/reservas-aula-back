package gestorreservasaulas.entidades;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "prendas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Prenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripción;

    @Enumerated(EnumType.STRING)
    private Estado estado;

   @OneToMany(mappedBy = "prenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> listaReserva;


}
