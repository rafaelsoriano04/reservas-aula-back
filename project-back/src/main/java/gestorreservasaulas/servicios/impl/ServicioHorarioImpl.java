package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.HorarioDTO;
import gestorreservasaulas.entidades.Horario;
import gestorreservasaulas.respositorios.RepositorioHorario;
import gestorreservasaulas.servicios.ServicioAula;
import gestorreservasaulas.servicios.ServicioHorario;
import gestorreservasaulas.servicios.ServicioLaboratorio;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ServicioHorarioImpl implements ServicioHorario {

    @Autowired
    private RepositorioHorario repositorioHorario;

    @Autowired
    private ServicioAula servicioAula;

    @Autowired
    private ServicioLaboratorio servicioLaboratorio;

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
    public HorarioDTO crearHorario(HorarioDTO horarioDTO) {
        if (servicioAula.obtenerAulaPorId(horarioDTO.getId_aula()) == null) {
            return null;
        }
        return horarioToDto(repositorioHorario.save(dtoToHorario(horarioDTO)));
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

    @Override
    public List<HorarioDTO> obtenerHorariosPorLabs(Long id) {
        List<Horario> listaHorarios = repositorioHorario.horariosLabos(id);
        if (listaHorarios.isEmpty()) {
            return null;
        }
        // stream().map() es como un for, que simplifica y se llama directo al metodo por cada item de la lista
        return listaHorarios.stream().map(this::horarioToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean eliminarHorario(Long id) {
        int count = repositorioHorario.deleteHorario(id);
        if (count > 0) {
            return true;
        } else {
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
        if (horario.getAula() == null) {
            horarioDTO.setId_laboratorio(horario.getLaboratorio().getId());
        } else {
            horarioDTO.setId_aula(horario.getAula().getId());
        }
        return horarioDTO;
    }

    private Horario dtoToHorario(HorarioDTO horarioDTO) {
        Horario horario = modelMapper.map(horarioDTO, Horario.class);
        if (horarioDTO.getId_aula() == null) {
            horario.setLaboratorio(servicioLaboratorio.obtenerLabPorId(horarioDTO.getId_laboratorio()));
        } else {
            horario.setAula(servicioAula.obtenerAulaPorId(horarioDTO.getId_aula()));
        }
        return horario;
    }

}
