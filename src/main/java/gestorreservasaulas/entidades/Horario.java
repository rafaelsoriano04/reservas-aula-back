package gestorreservasaulas.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "horarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dia;
    private String hora;
    private String materia;

    @ManyToOne
    @JoinColumn(name = "id_aula")
    private Aula aula;

    @ManyToOne
    @JoinColumn(name = "id_laboratorio")
    private Laboratorio laboratorio;

    @Override
    public String toString() {
        int ss = Integer.valueOf(getHora()) +1;
        return getDia() +" " + getHora() + "-"+ss;
    }
    
    
    
}
