package gestorreservasaulas.entidades;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "personas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String cedula;
    String nombre;
    String apellido;
    String telefono;
    String tipo;
    
    @OneToMany(mappedBy = "persona", orphanRemoval = true)
    private List<Reserva> listaHorarios = new ArrayList<>();
    
}