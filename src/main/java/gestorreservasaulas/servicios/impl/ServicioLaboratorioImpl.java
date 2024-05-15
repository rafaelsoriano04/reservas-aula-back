package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.entidades.Laboratorio;
import gestorreservasaulas.respositorios.RepositorioLaboratorio;
import gestorreservasaulas.servicios.ServicioLaboratorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioLaboratorioImpl implements ServicioLaboratorio {
    @Autowired
    RepositorioLaboratorio repositorioLaboratorio;
    @Override
    public Laboratorio obtenerLab(String nombre) {
        return repositorioLaboratorio.getByNombre(nombre).orElse(null);
        
    }
}
