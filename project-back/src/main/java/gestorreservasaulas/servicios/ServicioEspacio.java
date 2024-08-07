package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.EspacioDto;
import gestorreservasaulas.entidades.Espacio;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;

import java.util.List;

public interface ServicioEspacio {
    Espacio findById(Long id) throws NotFoundException;

    List<EspacioDto> findAll();

    EspacioDto save(EspacioDto espacioDto) throws NotFoundException;

    EspacioDto updateById(Long id, EspacioDto espacioDto) throws NotFoundException, ConflictException;

    void deleteById(Long id) throws NotFoundException, ConflictException;

    List<EspacioDto> getByBloque(Long id_bloque) throws NotFoundException;

    List<EspacioDto> findAllWithParams(String nombre, String tipo, Long id_bloque);


}
