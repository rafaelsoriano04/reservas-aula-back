package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.AulaDto;
import gestorreservasaulas.dtos.LaboratorioDto;
import gestorreservasaulas.entidades.Aula;
import gestorreservasaulas.entidades.Laboratorio;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.respositorios.RepositorioLaboratorio;
import gestorreservasaulas.servicios.ServicioBloque;
import gestorreservasaulas.servicios.ServicioLaboratorio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicioLaboratorioImpl implements ServicioLaboratorio {

    @Autowired
    RepositorioLaboratorio repositorioLaboratorio;

    @Autowired
    ServicioBloque servicioBloque;

    private final ModelMapper modelMapper;

    @Autowired
    public ServicioLaboratorioImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public Laboratorio obtenerLabPorId(Long id) {
        return repositorioLaboratorio.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public LaboratorioDto editarLab(Long id, LaboratorioDto laboratorioDto) throws NotFoundException, ConflictException {

        Laboratorio labExistente = repositorioLaboratorio.findById(id).orElseThrow(() -> new NotFoundException("Laboratorio not found"));

        if (laboratorioDto.getNombre() != null) {
            if (repositorioLaboratorio.getByNombre(laboratorioDto.getNombre()).isPresent()) {
                throw new ConflictException("El laboratorio ya existe");
            }
            labExistente.setNombre(laboratorioDto.getNombre());
        }
        if (laboratorioDto.getCapacidad() != null) {
            labExistente.setCapacidad(laboratorioDto.getCapacidad());
        }
        if (laboratorioDto.getPiso() != null) {
            labExistente.setPiso(laboratorioDto.getPiso());
        }

        labExistente.setBloque(servicioBloque.obtenerBloque(laboratorioDto.getId_bloque()));

        return labToDto(repositorioLaboratorio.save(labExistente));
    }

    @Override
    public List<LaboratorioDto> findByBloque(Long id_bloque) throws NotFoundException {
        servicioBloque.obtenerBloque(id_bloque);

        List<Laboratorio> listaLabs = repositorioLaboratorio.findByBloque(id_bloque);
        if (listaLabs.isEmpty()) {
            throw new NotFoundException("El bloque no tiene laboratorios");
        }

        return listaLabs.stream().map(this::labToDto).collect(Collectors.toList());
    }

    @Override
    public LaboratorioDto save(LaboratorioDto laboratorioDto) throws NotFoundException, ConflictException {
        Optional<Laboratorio> laboratorio = repositorioLaboratorio.getByNombre(laboratorioDto.getNombre());
        if (laboratorio.isPresent()) {
            throw new ConflictException("El Laboratorio ya existe");
        }
        servicioBloque.obtenerBloque(laboratorioDto.getId_bloque());

        return labToDto(repositorioLaboratorio.save(dtoToAula(laboratorioDto)));
    }

    @Override
    @Transactional
    public void eliminarLab(Long id) throws NotFoundException {
        if (repositorioLaboratorio.existsById(id)) {
            repositorioLaboratorio.deleteById(id);
        } else {
            throw new NotFoundException("Laboratorio not found");
        }
    }

    private LaboratorioDto labToDto(Laboratorio laboratorio) {
        LaboratorioDto laboratorioDto = modelMapper.map(laboratorio, LaboratorioDto.class);
        laboratorioDto.setId_bloque(laboratorio.getBloque().getId());
        return laboratorioDto;
    }

    private Laboratorio dtoToAula(LaboratorioDto laboratorioDto) throws NotFoundException {
        Laboratorio laboratorio = modelMapper.map(laboratorioDto, Laboratorio.class);
        laboratorio.setBloque(servicioBloque.obtenerBloque(laboratorioDto.getId_bloque()));
        return laboratorio;
    }

}
