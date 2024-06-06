package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.EspacioDto;
import gestorreservasaulas.entidades.Bloque;
import gestorreservasaulas.entidades.Espacio;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.respositorios.RepositorioEspacio;
import gestorreservasaulas.servicios.ServicioBloque;
import gestorreservasaulas.servicios.ServicioEspacio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioEspacioImpl implements ServicioEspacio {

    @Autowired
    private RepositorioEspacio repositorioEspacio;

    @Autowired
    private ServicioBloque servicioBloque;

    private final ModelMapper modelMapper;

    @Autowired
    public ServicioEspacioImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public Espacio findById(Long id) throws NotFoundException {
        return repositorioEspacio.findById(id).orElseThrow(() -> new NotFoundException("Espacio not found"));
    }

    @Override
    public List<EspacioDto> findAllByBloque(Long id_bloque) throws NotFoundException {
        Bloque bloque = servicioBloque.obtenerBloque(id_bloque);
        
        List<Espacio> listaEspacios = repositorioEspacio.findByBloque(id_bloque);

        if (listaEspacios.isEmpty()) {
            throw new NotFoundException("No hay espacios en este bloque");
        }

        return listaEspacios.stream().map(this::espacioToDto).collect(Collectors.toList());
    }

    @Override
    public EspacioDto save(EspacioDto espacioDto) throws NotFoundException {
        Bloque bloque = servicioBloque.obtenerBloque(espacioDto.getId_bloque());
        return espacioToDto(repositorioEspacio.save(dtoToEspacio(espacioDto)));
    }

    @Override
    public EspacioDto updateById(Long id, EspacioDto espacioDto) throws NotFoundException, ConflictException {
        Espacio espacio = repositorioEspacio.findById(id)
                .orElseThrow(() ->new NotFoundException("Espacio not found"));

        if (espacioDto.getNombre() != null) {
            if (repositorioEspacio.findByNombre(espacioDto.getNombre()).isPresent()) {
                throw new ConflictException("El aula ya existe");
            }
            espacio.setNombre(espacioDto.getNombre());
        }
        if (espacioDto.getCapacidad() != null) {
            espacio.setCapacidad(espacioDto.getCapacidad());
        }
        if (espacioDto.getPiso() != null) {
            espacio.setPiso(espacioDto.getPiso());
        }
        if (espacioDto.getTipo() != null) {
            espacio.setTipo(espacioDto.getTipo());
        }

        // Si no encuentra el bloque el metodo .obtenerBloque lanza exception
        espacio.setBloque(servicioBloque.obtenerBloque(espacioDto.getId_bloque()));

        return espacioToDto(repositorioEspacio.save(espacio));
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        if (repositorioEspacio.existsById(id)) {
            repositorioEspacio.deleteById(id);
        } else {
            throw new NotFoundException("Aula not found");
        }
    }

    private Espacio dtoToEspacio(EspacioDto espacioDto) throws NotFoundException {
        Espacio espacio = modelMapper.map(espacioDto, Espacio.class);
        espacio.setBloque(servicioBloque.obtenerBloque(espacioDto.getId_bloque()));
        return espacio;
    }

    private EspacioDto espacioToDto(Espacio espacio) {
        EspacioDto espacioDto = modelMapper.map(espacio, EspacioDto.class);
        espacioDto.setId_bloque(espacio.getBloque().getId());
        return espacioDto;
    }
}
