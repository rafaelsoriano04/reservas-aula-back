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

    public Bloque(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return  nombre ;
    }
    
    

}