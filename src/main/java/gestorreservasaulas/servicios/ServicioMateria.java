package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.MateriaDto;
import gestorreservasaulas.entidades.Materia;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;

import java.util.List;

public interface ServicioMateria {
    MateriaDto save(MateriaDto materiaDto) throws ConflictException;

    List<MateriaDto> findTodos() throws NotFoundException;

    void eliminarMateria(Long id) throws ConflictException;

    Materia buscarMateria(Long id) throws NotFoundException;

    List<MateriaDto> findAllWithParams(String nombre, String carrera);
}
