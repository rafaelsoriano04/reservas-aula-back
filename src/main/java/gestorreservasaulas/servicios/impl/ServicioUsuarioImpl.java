package gestorreservasaulas.servicios.impl;


import gestorreservasaulas.entidades.Usuario;
import gestorreservasaulas.respositorios.RepositorioUsuario;
import gestorreservasaulas.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuarioImpl implements ServicioUsuario{

    @Autowired
    RepositorioUsuario repositorioUsuario;

    @Override
    public Usuario obtenerUsuario(String username) {
        return repositorioUsuario.getByUsername(username).orElse(null);
    }

}
