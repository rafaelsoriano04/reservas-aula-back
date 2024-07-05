package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.EspacioDto;
import gestorreservasaulas.entidades.Bloque;
import gestorreservasaulas.entidades.Espacio;
import gestorreservasaulas.entidades.Usuario;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.respositorios.RepositorioEspacio;
import gestorreservasaulas.servicios.ServicioBloque;
import gestorreservasaulas.servicios.ServicioEspacio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public List<EspacioDto> getByBloque(Long id_bloque) throws NotFoundException {
        return repositorioEspacio.findAllByBloque(servicioBloque.obtenerBloque(id_bloque), Sort.by(Sort.Direction.ASC, "nombre"))
                .stream().map(this::espacioToDto).collect(Collectors.toList());
    }

    @Override
    public List<EspacioDto> getByNombre(String nombre) throws NotFoundException {
        return repositorioEspacio.findAllByNombreContains(nombre, Sort.by(Sort.Direction.ASC, "nombre"))
                .stream().map(this::espacioToDto).collect(Collectors.toList());
    }

    @Override
    public List<EspacioDto> getByTipo(String tipo) throws NotFoundException {
        return repositorioEspacio.findAllByTipo(tipo, Sort.by(Sort.Direction.ASC, "nombre"))
                .stream().map(this::espacioToDto).collect(Collectors.toList());
    }

    @Override
    public List<EspacioDto> getByTipoNombre(String tipo, String nombre) throws NotFoundException {
        return repositorioEspacio.findAllByTipoAndNombreContains(tipo, nombre, Sort.by(Sort.Direction.ASC, "nombre"))
                .stream().map(this::espacioToDto).collect(Collectors.toList());
    }

    @Override
    public List<EspacioDto> getByTipoBloque(String tipo, Long id_bloque) throws NotFoundException {
        return repositorioEspacio.findAllByTipoAndBloque(tipo, servicioBloque.obtenerBloque(id_bloque), Sort.by(Sort.Direction.ASC, "nombre"))
                .stream().map(this::espacioToDto).collect(Collectors.toList());
    }

    @Override
    public List<EspacioDto> getByBloqueNombre(Long id_bloque, String nombre) throws NotFoundException {
        return repositorioEspacio.findAllByBloqueAndNombreContains(servicioBloque.obtenerBloque(id_bloque), nombre, Sort.by(Sort.Direction.ASC, "nombre"))
                .stream().map(this::espacioToDto).collect(Collectors.toList());
    }

    @Override
    public List<EspacioDto> getByBloqueNombreTipo(Long id_bloque, String nombre, String tipo) throws NotFoundException {
        return repositorioEspacio.findAllByBloqueAndNombreContainsAndTipo(servicioBloque.obtenerBloque(id_bloque), nombre, tipo, Sort.by(Sort.Direction.ASC, "nombre"))
                .stream().map(this::espacioToDto).collect(Collectors.toList());
    }

    @Override
    public EspacioDto save(EspacioDto espacioDto) throws NotFoundException {
        return espacioToDto(repositorioEspacio.save(dtoToEspacio(espacioDto)));
    }

    @Override
    public EspacioDto updateById(Long id, EspacioDto espacioDto) throws NotFoundException, ConflictException {
        Espacio espacioExistente = repositorioEspacio.findById(id)
                .orElseThrow(() -> new NotFoundException("Espacio not found"));

        // Actualiza campos simples si no son nulos y han cambiado
        if (espacioDto.getNombre() != null && !espacioDto.getNombre().equals(espacioExistente.getNombre())) {
            if (repositorioEspacio.findByNombre(espacioDto.getNombre()).isPresent()) {
                throw new ConflictException("El nombre del espacio ya existe");
            }
            espacioExistente.setNombre(espacioDto.getNombre());
        }
        if (espacioDto.getCapacidad() != null) {
            espacioExistente.setCapacidad(espacioDto.getCapacidad());
        }
        if (espacioDto.getPiso() != null) {
            espacioExistente.setPiso(espacioDto.getPiso());
        }
        if (espacioDto.getTipo() != null) {
            espacioExistente.setTipo(espacioDto.getTipo());
        }

        // Actualiza el bloque, asegurándose de que el nuevo bloque existe
        if (espacioDto.getId_bloque() != null && !espacioDto.getId_bloque().equals(espacioExistente.getBloque().getId())) {
            Bloque nuevoBloque = servicioBloque.obtenerBloque(espacioDto.getId_bloque());
            espacioExistente.setBloque(nuevoBloque);
        }


        Espacio espacioGuardado = repositorioEspacio.save(espacioExistente);
        return espacioToDto(espacioGuardado);
    }

    @Override
    public void deleteById(Long id) throws NotFoundException, ConflictException {
        Espacio espacio = repositorioEspacio.findById(id)
                .orElseThrow(() -> new NotFoundException("Espacio not found"));

        // Comprueba si hay horarios asociados
        if (!espacio.getListaHorario().isEmpty()) {
            throw new ConflictException("No se puede eliminar el espacio porque tiene horarios asociados.");
        }

        // Comprueba si hay reservas asociadas
        if (!espacio.getListaReserva().isEmpty()) {
            throw new ConflictException("No se puede eliminar el espacio porque tiene reservas asociadas.");
        }

        repositorioEspacio.deleteById(id);
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

    @Override
    public List<EspacioDto> findAll() {
        List<Espacio> listaEspacios = repositorioEspacio.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
        return listaEspacios.stream().map(this::espacioToDto).collect(Collectors.toList());
    }

}
