package gestorreservasaulas.respositorios;



import gestorreservasaulas.entidades.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 @Repository
public interface RepositorioLaboratorio extends JpaRepository <Laboratorio, Long>{
    Optional <Laboratorio> getByNombre (String nombre);
    @Query("SELECT l FROM Laboratorio l WHERE l.bloque.id = :id_bloque")
    List<Laboratorio> findByBloque(@Param("id_bloque") Long id_bloque);;
}
