package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.entidades.Persona;
import gestorreservasaulas.respositorios.RepositorioPersona;
import gestorreservasaulas.servicios.ServicioPersona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioPersonaImpl implements ServicioPersona {

    @Autowired
    private RepositorioPersona repositorioPersona;

    @Override
    public Persona buscar(String cedula) {
        return repositorioPersona.findByCedula(cedula).orElse(null);
    }

    @Override
    public Persona guardar(Persona nuevaPersona) {
        Persona persona = buscar(nuevaPersona.getCedula());
        if (persona != null) {
            persona.setNombre(nuevaPersona.getNombre());
            persona.setApellido(nuevaPersona.getApellido());
            persona.setTelefono(nuevaPersona.getTelefono());
            return repositorioPersona.save(nuevaPersona);
        }
        return repositorioPersona.save(nuevaPersona);
    }

    @Override
    public boolean existePorCedula(String cedula) {
        return repositorioPersona.existsByCedula(cedula);
    }
}
