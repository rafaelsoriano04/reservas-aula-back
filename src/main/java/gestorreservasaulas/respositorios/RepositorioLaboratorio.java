package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
 @Repository
public interface RepositorioLaboratorio extends JpaRepository <Laboratorio, Long>{
    Optional <Laboratorio> getByNombre (String nombre);
}
