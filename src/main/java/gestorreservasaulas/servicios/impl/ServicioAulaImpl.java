package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.entidades.Aula;
import gestorreservasaulas.respositorios.RepositorioAula;
import gestorreservasaulas.servicios.ServicioAula;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ServicioAulaImpl implements ServicioAula {

    @Autowired
    RepositorioAula repositorioAula;

    @Override

    public Aula obtenerAula(String nombre) {
        return repositorioAula.getByNombre(nombre).orElse(null);
    }

    @Override
    @Transactional
    public boolean editarAula(Aula aula) {

        Aula aula1 = repositorioAula.findById(aula.getId()).orElse(null);
        if (aula1 == null) {
            return false;
        }
        aula1.setCapacidad(aula.getCapacidad());
        aula1.setPiso(aula.getPiso());
        aula.setNombre(aula.getNombre());
        repositorioAula.save(aula);
        return true;
    }

    @Override
    public List<Aula> findByBloque(Long id_bloque) {
        return repositorioAula.findByBloque(id_bloque);
    }


}
