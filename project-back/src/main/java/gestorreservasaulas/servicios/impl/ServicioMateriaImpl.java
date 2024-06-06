package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.AulaDto;
import gestorreservasaulas.dtos.MateriaDto;
import gestorreservasaulas.entidades.Aula;
import gestorreservasaulas.entidades.Materia;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.respositorios.RepositorioPersona;
import gestorreservasaulas.respositorios.RespositorioMateria;
import gestorreservasaulas.servicios.ServicioMateria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioMateriaImpl implements ServicioMateria {
    @Autowired
    private RespositorioMateria repositorioMateria;

    @Override
    public MateriaDto save(MateriaDto materiaDto) throws ConflictException {
        Materia materia = new Materia();
        materia.setId(materiaDto.getId());
        materia.setNombre(materiaDto.getNombre());
        Materia obtenido = repositorioMateria.save(materia);
        if (obtenido == null)
            throw new ConflictException("No se creo");
        MateriaDto regreso = new MateriaDto();
        regreso.setId(obtenido.getId());
        regreso.setNombre(obtenido.getNombre());
        return regreso;
    }
    @Override
    public List<Materia> findTodos(){
        return repositorioMateria.findAll();
    }

    @Override
    public void eliminarMateria(Long id) {
        repositorioMateria.deleteById(id);
    }


}
