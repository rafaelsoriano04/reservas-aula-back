package gestorreservasaulas.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bloques")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Bloque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nombre;
    String descripcion;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bloques")
    List<Labotorio> listaLaboratorios;

}