package gestorreservasaulas.servicios;

import gestorreservasaulas.dtos.AulaDto;
import gestorreservasaulas.dtos.LaboratorioDto;
import gestorreservasaulas.entidades.Aula;
import gestorreservasaulas.entidades.Laboratorio;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;

import java.util.List;

public interface ServicioLaboratorio
{
    Laboratorio obtenerLabPorId(Long id);
    LaboratorioDto editarLab(Long id, LaboratorioDto laboratorioDto) throws NotFoundException, ConflictException;
    List<LaboratorioDto> findByBloque(Long id_bloque) throws NotFoundException;
    LaboratorioDto save(LaboratorioDto laboratorioDto) throws NotFoundException, ConflictException;
    void eliminarLab(Long id) throws NotFoundException;
}
