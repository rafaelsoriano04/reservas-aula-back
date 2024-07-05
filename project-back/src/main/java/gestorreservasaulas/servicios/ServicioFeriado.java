package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.FeriadoDto;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;

import java.util.Date;
import java.util.List;

public interface ServicioFeriado {
    List<FeriadoDto> getAllFeriados() throws NotFoundException;
    FeriadoDto save(FeriadoDto feriadoDto) throws NotFoundException;
    FeriadoDto updateById(Long id, FeriadoDto feriadoDto) throws NotFoundException, ConflictException;
    void deleteById(Long id) throws NotFoundException, ConflictException;
    List<FeriadoDto> getAfterInicio(Date inicio) throws NotFoundException;
    List<FeriadoDto> getBeforeFin(Date fin) throws NotFoundException;
    List<FeriadoDto> getBetweenDates(Date inicio, Date fin) throws NotFoundException;

}
