package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.MateriaDto;
import gestorreservasaulas.entidades.Materia;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.respositorios.RespositorioMateria;
import gestorreservasaulas.servicios.ServicioHorario;
import gestorreservasaulas.servicios.ServicioMateria;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioMateriaImpl implements ServicioMateria {
    @Autowired
    private RespositorioMateria repositorioMateria;

    private final ModelMapper modelMapper;
    
    @Autowired
    public ServicioMateriaImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public MateriaDto save(MateriaDto materiaDto) throws ConflictException {
        return materiaToDto(repositorioMateria.save(dtoToMateria(materiaDto)));
    }

    @Override
    public List<MateriaDto> findTodos() throws NotFoundException {
        List<Materia> listaMateria = repositorioMateria.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
        if (listaMateria.isEmpty()) {
            throw new NotFoundException("No hay materias");
        }
        return listaMateria.stream().map(this::materiaToDto).collect(Collectors.toList());
    }

    @Override
    public void eliminarMateria(Long id) throws ConflictException {
        try{
            repositorioMateria.deleteById(id);
        }catch (Exception e){
            throw new ConflictException("Materia Asociada a Horario");
        }

    }

    @Override
    public Materia buscarMateria(Long id) throws NotFoundException {
        return repositorioMateria.findById(id).orElseThrow(() -> new NotFoundException("Materia not found"));
    }

    @Override
    public List<MateriaDto> getByNombreAndCarrera(String nombre, String carrera) throws NotFoundException {
        List<Materia> listaMateria = repositorioMateria.findByNombreContainingAndCarrera(nombre, carrera, Sort.by(Sort.Direction.ASC, "nombre"));
        if (listaMateria.isEmpty()) {
            throw new NotFoundException("No hay materias");
        }
        return listaMateria.stream().map(this::materiaToDto).collect(Collectors.toList());
    }

    @Override
    public List<MateriaDto> getByNombre(String nombre) throws NotFoundException {
        List<Materia> listaMateria = repositorioMateria.findByNombreContaining(nombre, Sort.by(Sort.Direction.ASC, "nombre"));
        if (listaMateria.isEmpty()) {
            throw new NotFoundException("No hay materias");
        }
        return listaMateria.stream().map(this::materiaToDto).collect(Collectors.toList());
    }

    @Override
    public List<MateriaDto> getByCarrera(String carrera) throws NotFoundException {
        List<Materia> listaMateria = repositorioMateria.findByCarrera(carrera, Sort.by(Sort.Direction.ASC, "nombre"));
        if (listaMateria.isEmpty()) {
            throw new NotFoundException("No hay materias");
        }
        return listaMateria.stream().map(this::materiaToDto).collect(Collectors.toList());
    }

    private MateriaDto materiaToDto(Materia materia) {
        return modelMapper.map(materia, MateriaDto.class);
    }

    private Materia dtoToMateria(MateriaDto materiaDto) {
        return modelMapper.map(materiaDto, Materia.class);
    }

}
