package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.MateriaDto;
import gestorreservasaulas.entidades.Materia;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;

import java.util.List;

public interface ServicioMateria {

    MateriaDto save(MateriaDto materiaDto) throws ConflictException;
    List<Materia> findTodos();
    void eliminarMateria(Long id);
}
