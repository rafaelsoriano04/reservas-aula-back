package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.entidades.Aula;
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

    @Override
    public Laboratorio crearLaboratorio(Laboratorio lab) {
        return repositorioLaboratorio.save(lab);
    }

    @Override
    public boolean editarLaboratorio(Laboratorio lab) {
        Laboratorio labExistente = repositorioLaboratorio.findById(lab.getId()).orElse(null);
        if (labExistente == null) {
            return false;
        }

        labExistente.setNombre(lab.getNombre());
        labExistente.setCapacidad(lab.getCapacidad());
        labExistente.setPiso(lab.getPiso());
        labExistente.setBloque(lab.getBloque());


        labExistente.setListaReservas(labExistente.getListaReservas());
        labExistente.setListaHorario(labExistente.getListaHorario());


        repositorioLaboratorio.save(labExistente);
        return true;
    }

    @Override
    public void eliminarLaboratorio(Long id) {
        repositorioLaboratorio.deleteById(id);
    }


}
