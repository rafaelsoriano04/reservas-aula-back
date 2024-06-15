package gestorreservasaulas.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "bloques")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Bloque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    String nombre;

    String descripcion;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bloque")
    List<Espacio> listaEspacio;

    public Bloque(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

}