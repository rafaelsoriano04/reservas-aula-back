package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.HorarioDTO;
import gestorreservasaulas.entidades.Horario;
import gestorreservasaulas.respositorios.RepositorioHorario;
import gestorreservasaulas.servicios.ServicioHorario;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author fredd
 */
@Service
public class ServicioHorarioImpl implements ServicioHorario {

    @Autowired
    private RepositorioHorario repositorioHorario;

    private final ModelMapper modelMapper;

    @Autowired
    public ServicioHorarioImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public Horario obtenerHorario(Long id) {
        return repositorioHorario.findById(id).orElse(null);
    }

    @Override
    public Horario crearHorario(Horario horario) {
        return repositorioHorario.save(horario);
    }

    @Override
    public List<HorarioDTO> obtenerHorariosPorAula(Long id) {
        List<Horario> listaHorarios = repositorioHorario.horariosAulas(id);
        if (listaHorarios.isEmpty()) {
            return null;
        }
        // stream().map() es como un for, que simplifica y se llama directo al metodo por cada item de la lista
        return listaHorarios.stream().map(this::horarioToDto).collect(Collectors.toList());
    }

    public List<Horario> obtenerHorariosPorLabs(Long id) {
        return repositorioHorario.horariosLabos(id);
    }

    @Override
    @Transactional
    public Boolean eliminarHorario(Long idHorario) {
        int count = repositorioHorario.deleteHorario(idHorario);
        if (count > 0) {
            return true;
        } else {
            System.err.println("No se encontró el horario con ID: " + idHorario + " o no pudo ser eliminado.");
            return false;
        }
    }

    /* Metodo para transformar de horario a DTO con ModelMapper, mappea
     los campos de igual nombre pero como HorarioDTO tiene id_laboratorio/id_aula
     y Horario tiene como atributo Aula u Horario, toca mappear manual esos
     atributos
     */
    private HorarioDTO horarioToDto(Horario horario) {
        HorarioDTO horarioDTO = modelMapper.map(horario, HorarioDTO.class);
        if (horario.getAula().getId() == null) {
            horarioDTO.setId_laboratorio(horario.getLaboratorio().getId());
        } else {
            horarioDTO.setId_aula(horario.getAula().getId());
        }
        return horarioDTO;
    }

}
