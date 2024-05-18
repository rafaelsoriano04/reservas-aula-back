package gestorreservasaulas.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    Long id;

    @Column(unique = true)
    String nombre;

    String descripcion;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bloque")
    List<Aula> listaAulas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bloque")
    List<Laboratorio> listaLaboratorios;


}