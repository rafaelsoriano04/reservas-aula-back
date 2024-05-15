/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorreservasaulas.respositorios;

import gestorreservasaulas.entidades.Horario;
import gestorreservasaulas.entidades.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RepositorioHorario extends JpaRepository<Horario, Long> {
    Optional<Horario> getByID(Long id);
}
