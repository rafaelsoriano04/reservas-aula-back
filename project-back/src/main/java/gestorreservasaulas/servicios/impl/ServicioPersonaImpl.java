package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.PersonaDto;
import gestorreservasaulas.entidades.Persona;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.respositorios.RepositorioPersona;
import gestorreservasaulas.servicios.ServicioPersona;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        if (this.existePorCedula(nuevaPersona.getCedula())) {
            PersonaDto personaExistente = buscar(nuevaPersona.getCedula());
            personaExistente.setNombre(nuevaPersona.getNombre());
            personaExistente.setApellido(nuevaPersona.getApellido());
            personaExistente.setTelefono(nuevaPersona.getTelefono());
            personaExistente.setTipo(nuevaPersona.getTipo());

            return personToDto(repositorioPersona.save(dtoToPerson(personaExistente)));
        } else {
            Persona nueva = dtoToPerson(nuevaPersona);
            Persona personaGuardada = repositorioPersona.save(nueva);
            return personToDto(personaGuardada);
        }

    }

    @Override
    public List<PersonaDto> listarDocentes() {
        List<Persona> docentes = repositorioPersona.findAll();
        List<PersonaDto> nuevaLista= new ArrayList<>();
        for (Persona persona: docentes) {
            if (persona.getTipo().equals("Docente")){
                nuevaLista.add(personToDto(persona));
            }
        }
            return nuevaLista;
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
