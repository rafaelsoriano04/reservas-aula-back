package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.FeriadoDto;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;

import java.sql.Date;
import java.util.List;

public interface ServicioFeriado {
    List<FeriadoDto> getAllFeriados() throws NotFoundException;

    FeriadoDto save(FeriadoDto feriadoDto) throws NotFoundException;

    FeriadoDto updateById(Long id, FeriadoDto feriadoDto) throws NotFoundException, ConflictException;

    void deleteById(Long id) throws NotFoundException, ConflictException;

    List<FeriadoDto> findAllByParams(Date inicio, Date fin);

}
