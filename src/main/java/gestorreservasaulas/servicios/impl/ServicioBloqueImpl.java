package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.entidades.Bloque;
import gestorreservasaulas.respositorios.RepositorioBloque;
import gestorreservasaulas.servicios.ServicioBloque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioBloqueImpl implements ServicioBloque {

    @Autowired
    RepositorioBloque repositorioBloque;

    @Override
    public Bloque obtenerBloque(Long id) {
        return repositorioBloque.findById(id).orElse(null);
    }
}