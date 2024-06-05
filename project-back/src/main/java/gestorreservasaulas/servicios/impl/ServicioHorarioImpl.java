package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.HorarioDto;
import gestorreservasaulas.entidades.Horario;
import gestorreservasaulas.respositorios.RepositorioHorario;
import gestorreservasaulas.servicios.ServicioAula;
import gestorreservasaulas.servicios.ServicioHorario;
import gestorreservasaulas.servicios.ServicioLaboratorio;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public HorarioDto crearHorario(HorarioDto horarioDTO) {
        if (servicioAula.obtenerAulaPorId(horarioDTO.getId_aula()) == null) {
            return null;
        }
        return horarioToDto(repositorioHorario.save(dtoToHorario(horarioDTO)));
    }

    @Override
    public List<HorarioDto> obtenerHorariosPorAula(Long id) {
        List<Horario> listaHorarios = repositorioHorario.horariosAulas(id);
        if (listaHorarios.isEmpty()) {
            return null;
        }
        // stream().map() es como un for, que simplifica y se llama directo al metodo por cada item de la lista
        return listaHorarios.stream().map(this::horarioToDto).collect(Collectors.toList());
    }

    @Override
    public List<HorarioDto> obtenerHorariosPorLabs(Long id) {
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
    private HorarioDto horarioToDto(Horario horario) {
        HorarioDto horarioDTO = modelMapper.map(horario, HorarioDto.class);
        if (horario.getAula() == null) {
            horarioDTO.setId_laboratorio(horario.getLaboratorio().getId());
        } else {
            horarioDTO.setId_aula(horario.getAula().getId());
        }
        horarioDTO.setId_persona(horario.getDocente().getId());
        return horarioDTO;
    }

    private Horario dtoToHorario(HorarioDto horarioDTO) {
        Horario horario = modelMapper.map(horarioDTO, Horario.class);
        if (horarioDTO.getId_aula() == null) {
            horario.setLaboratorio(servicioLaboratorio.obtenerLabPorId(horarioDTO.getId_laboratorio()));
        } else {
            horario.setAula(servicioAula.obtenerAulaPorId(horarioDTO.getId_aula()));
        }
        return horario;
    }

}
