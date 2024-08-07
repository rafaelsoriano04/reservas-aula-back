package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.HorarioDto;
import gestorreservasaulas.entidades.Horario;
import gestorreservasaulas.entidades.Materia;
import gestorreservasaulas.entidades.Persona;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.respositorios.RepositorioHorario;
import gestorreservasaulas.servicios.ServicioEspacio;
import gestorreservasaulas.servicios.ServicioHorario;
import gestorreservasaulas.servicios.ServicioPersona;
import gestorreservasaulas.servicios.ServicioMateria;
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
    private ServicioPersona servicioPersona;

    @Autowired
    private ServicioEspacio servicioEspacio;

    @Autowired
    private ServicioMateria servicioMateria;

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
    public HorarioDto crearHorario(HorarioDto horarioDTO) throws NotFoundException {
        servicioEspacio.findById(horarioDTO.getId_espacio());

        return horarioToDto(repositorioHorario.save(dtoToHorario(horarioDTO)));
    }

    @Override
    public List<HorarioDto> obtenerHorariosPorAula(Long id) {
        List<Horario> listaHorarios = repositorioHorario.horarios(id, "Aula");
        if (listaHorarios.isEmpty()) {
            return null;
        }
        // stream().map() es como un for, que simplifica y se llama directo al metodo por cada item de la lista
        return listaHorarios.stream().map(this::horarioToDto).collect(Collectors.toList());
    }

    @Override
    public List<HorarioDto> obtenerHorariosPorLabs(Long id) {
        List<Horario> listaHorarios = repositorioHorario.horarios(id, "Laboratorio");
        if (listaHorarios.isEmpty()) {
            return null;
        }
        // stream().map() es como un for, que simplifica y se llama directo al metodo por cada item de la lista
        return listaHorarios.stream().map(this::horarioToDto).collect(Collectors.toList());
    }

    @Override
    public List<HorarioDto> obtenerHorariosPorEspecial(Long id) {
        List<Horario> listaHorarios = repositorioHorario.horarios(id, "Especial");
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
        horarioDTO.setId_espacio(horario.getEspacio().getId());
        horarioDTO.setId_persona(horario.getDocente().getId());
        horarioDTO.setId_materia(horario.getMateria().getId());

        horarioDTO.setNombre(horario.getMateria().getNombre() + " - " + horario.getDocente().getNombre() + " " +
                horario.getDocente().getApellido());
        return horarioDTO;
    }

    private Horario dtoToHorario(HorarioDto horarioDTO) throws NotFoundException {
        Horario horario = modelMapper.map(horarioDTO, Horario.class);
        horario.setEspacio(servicioEspacio.findById(horarioDTO.getId_espacio()));
        Persona persona = servicioPersona.findById(horarioDTO.getId_persona());
        horario.setDocente(persona);
        Materia materia = servicioMateria.buscarMateria(horarioDTO.getId_materia());
        horario.setMateria(materia);
        return horario;
    }
}
