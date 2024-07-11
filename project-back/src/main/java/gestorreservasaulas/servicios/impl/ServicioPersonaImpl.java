package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.PersonaDto;
import gestorreservasaulas.entidades.Persona;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.respositorios.RepositorioPersona;
import gestorreservasaulas.servicios.ServicioPersona;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioPersonaImpl implements ServicioPersona {

    @Autowired
    private RepositorioPersona repositorioPersona;
    private final ModelMapper modelMapper;

    @Autowired
    public ServicioPersonaImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public Persona findById(Long id) throws NotFoundException {
        return repositorioPersona.findById(id).orElseThrow(() -> new NotFoundException("Persona not found"));
    }

    @Override
    public PersonaDto buscar(String cedula) throws NotFoundException {
        Persona persona = repositorioPersona.findByCedula(cedula).orElseThrow(() -> new NotFoundException("Persona not found"));
        return personToDto(persona);
    }

    @Override
    public PersonaDto guardar(PersonaDto nuevaPersona) throws NotFoundException {
        if (nuevaPersona.getCedula().equals("editando")) {
            Persona p = findById(nuevaPersona.getId());
            Persona nueva = dtoToPerson(nuevaPersona);
            nueva.setCedula(p.getCedula());
            Persona personaGuardada = repositorioPersona.save(nueva);
            return personToDto(personaGuardada);
        }

        if (this.existePorCedula(nuevaPersona.getCedula())) {
            throw new NotFoundException("cedula existe");

        } else {
            //nueva persona
            Persona nueva = dtoToPerson(nuevaPersona);
            Persona personaGuardada = repositorioPersona.save(nueva);
            return personToDto(personaGuardada);
        }
    }

    @Override
    public List<PersonaDto> listarDocentes() {
        return repositorioPersona.findAllByTipo("Docente", Sort.by("apellido")).stream().map(this::personToDto).collect(Collectors.toList());
    }

    @Override
    public List<PersonaDto> getByCedula(String cedula) {
        return repositorioPersona.findAllByTipoAndCedulaStartsWith("Docente", cedula, Sort.by("apellido")).stream().map(this::personToDto).collect(Collectors.toList());
    }

    @Override
    public List<PersonaDto> getByNombreApellido(String nombre) {
         return repositorioPersona
                .findAllByTipoAndNombreContainsOrApellidoContains("Docente", nombre, nombre, Sort.by("apellido"))
                .stream().map(this::personToDto).collect(Collectors.toList());
    }

    @Override
    public List<PersonaDto> getByCedulaNombreApellido(String cedula, String nombre) {
        return repositorioPersona.findAllByTipoAndCedulaStartsWithAndNombreContainsOrApellidoContains("Docente", cedula, nombre, nombre, Sort.by("apellido"))
                .stream().map(this::personToDto).collect(Collectors.toList());
    }

    @Override
    public void eliminarPersona(Long id) throws ConflictException {
        try {
            repositorioPersona.deleteById(id);
        } catch (Exception e) {
            throw new ConflictException("Persona Asociada a Horario");
        }

    }


    @Override
    public boolean existePorCedula(String cedula) {
        return repositorioPersona.existsByCedula(cedula);
    }

    private Persona dtoToPerson(PersonaDto personaDto) throws NotFoundException {
        Persona persona = modelMapper.map(personaDto, Persona.class);
        return persona;
    }

    private PersonaDto personToDto(Persona persona) {
        PersonaDto personadto = modelMapper.map(persona, PersonaDto.class);
        return personadto;
    }
}
