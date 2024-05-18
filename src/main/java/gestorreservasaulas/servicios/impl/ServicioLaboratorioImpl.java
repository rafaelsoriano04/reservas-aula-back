package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.entidades.Laboratorio;
import gestorreservasaulas.respositorios.RepositorioLaboratorio;
import gestorreservasaulas.servicios.ServicioLaboratorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioLaboratorioImpl implements ServicioLaboratorio {
    @Autowired
    RepositorioLaboratorio repositorioLaboratorio;
    @Override
    public Laboratorio obtenerLab(String nombre) {
        return repositorioLaboratorio.getByNombre(nombre).orElse(null);
        
    }


    @Override
    public List<Laboratorio> findByBloque(Long id_bloque) {
      return repositorioLaboratorio.findByBloque(id_bloque);
    }
}
