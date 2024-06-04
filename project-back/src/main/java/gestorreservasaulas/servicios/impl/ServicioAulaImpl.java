package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.AulaDTO;
import gestorreservasaulas.entidades.Aula;
import gestorreservasaulas.respositorios.RepositorioAula;
import gestorreservasaulas.respositorios.RepositorioBloque;
import gestorreservasaulas.servicios.ServicioAula;
import gestorreservasaulas.servicios.ServicioBloque;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Aula obtenerAula(String nombre) {
        return repositorioAula.findByNombreWithHorarios(nombre).orElse(null);
    }

    @Override
    @Transactional
    public boolean editarAula(Aula aula) {

        Aula aulaExistente = repositorioAula.findById(aula.getId()).orElse(null);
        if (aulaExistente == null) {
            return false;
        }

        aulaExistente.setNombre(aula.getNombre());
        aulaExistente.setCapacidad(aula.getCapacidad());
        aulaExistente.setPiso(aula.getPiso());
        aulaExistente.setBloque(aula.getBloque());


        aulaExistente.setListaReservas(aulaExistente.getListaReservas());
        aulaExistente.setListaHorario(aulaExistente.getListaHorario());

        repositorioAula.save(aulaExistente);
        return true;
    }

    @Override
    public List<AulaDTO> findByBloque(Long id_bloque) {
        List<Aula> listaAulas = repositorioAula.findByBloque(id_bloque);
        if (listaAulas.isEmpty()) {
            return null;
        }
        return listaAulas.stream().map(this::aulaToDto).collect(Collectors.toList());
    }

    @Override
    public AulaDTO save(AulaDTO aulaDTO) {
        if (servicioBloque.obtenerBloque(aulaDTO.getId_bloque()) == null) {
            return null;
        }
        return aulaToDto(repositorioAula.save(dtoToAula(aulaDTO)));
    }

    @Override
    public void eliminarAula(Long id) {
        repositorioAula.deleteById(id);
    }

    private AulaDTO aulaToDto(Aula aula) {
        AulaDTO aulaDto = modelMapper.map(aula, AulaDTO.class);
        aulaDto.setId_bloque(aula.getBloque().getId());
        return aulaDto;
    }

    private Aula dtoToAula(AulaDTO aulaDTO) {
        Aula aula = modelMapper.map(aulaDTO, Aula.class);
        aula.setBloque(servicioBloque.obtenerBloque(aulaDTO.getId_bloque()));
        return aula;
    }


}
