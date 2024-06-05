package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.AulaDto;
import gestorreservasaulas.entidades.Aula;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.respositorios.RepositorioAula;
import gestorreservasaulas.servicios.ServicioAula;
import gestorreservasaulas.servicios.ServicioBloque;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ServicioAulaImpl implements ServicioAula {

    @Autowired
    RepositorioAula repositorioAula;

    @Autowired
    ServicioBloque servicioBloque;

    private final ModelMapper modelMapper;

    @Autowired
    public ServicioAulaImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public Aula obtenerAulaPorId(Long id) {
        return repositorioAula.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public AulaDto editarAula(Long id, AulaDto aulaDTO) throws NotFoundException, ConflictException {

        Aula aulaExistente = repositorioAula.findById(id).orElseThrow(() -> new NotFoundException("Aula not found"));

        if (aulaDTO.getNombre() != null) {
            if (repositorioAula.getByNombre(aulaDTO.getNombre()).isPresent()) {
                throw new ConflictException("El aula ya existe");
            }
            aulaExistente.setNombre(aulaDTO.getNombre());
        }
        if (aulaDTO.getCapacidad() != null) {
            aulaExistente.setCapacidad(aulaDTO.getCapacidad());
        }
        if (aulaDTO.getPiso() != null) {
            aulaExistente.setPiso(aulaDTO.getPiso());
        }

        aulaExistente.setBloque(servicioBloque.obtenerBloque(aulaDTO.getId_bloque()));

        return aulaToDto(repositorioAula.save(aulaExistente));
    }

    @Override
    public List<AulaDto> findByBloque(Long id_bloque) throws NotFoundException {
        servicioBloque.obtenerBloque(id_bloque);

        List<Aula> listaAulas = repositorioAula.findByBloque(id_bloque);
        if (listaAulas.isEmpty()) {
            throw new NotFoundException("El bloque no tiene aulas");
        }

        return listaAulas.stream().map(this::aulaToDto).collect(Collectors.toList());
    }

    @Override
    public AulaDto save(AulaDto aulaDTO) throws NotFoundException, ConflictException {
        Optional<Aula> aula = repositorioAula.getByNombre(aulaDTO.getNombre());
        if (aula.isPresent()) {
            throw new ConflictException("El aula ya existe");
        }
        servicioBloque.obtenerBloque(aulaDTO.getId_bloque());

        return aulaToDto(repositorioAula.save(dtoToAula(aulaDTO)));
    }

    @Override
    @Transactional
    public void eliminarAula(Long id) throws NotFoundException {
        if (repositorioAula.existsById(id)) {
            repositorioAula.deleteById(id);
        } else {
            throw new NotFoundException("Aula not found");
        }
    }

    private AulaDto aulaToDto(Aula aula) {
        AulaDto aulaDto = modelMapper.map(aula, AulaDto.class);
        aulaDto.setId_bloque(aula.getBloque().getId());
        return aulaDto;
    }

    private Aula dtoToAula(AulaDto aulaDTO) throws NotFoundException {
        Aula aula = modelMapper.map(aulaDTO, Aula.class);
        aula.setBloque(servicioBloque.obtenerBloque(aulaDTO.getId_bloque()));
        return aula;
    }


}
