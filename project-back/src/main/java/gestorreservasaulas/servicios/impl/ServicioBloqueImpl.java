package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.BloqueDto;
import gestorreservasaulas.entidades.Bloque;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.respositorios.RepositorioBloque;
import gestorreservasaulas.servicios.ServicioBloque;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioBloqueImpl implements ServicioBloque {

    @Autowired
    RepositorioBloque repositorioBloque;



    private final ModelMapper modelMapper;

    @Autowired
    public ServicioBloqueImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public Bloque obtenerBloque(Long id) throws NotFoundException {
        return repositorioBloque.findById(id).orElseThrow(() -> new NotFoundException("Bloque not found"));
    }

    @Override
    public List<BloqueDto> getAll() throws NotFoundException {
        List<Bloque> listaBloques = repositorioBloque.findAll();
        if (listaBloques.isEmpty()) {
            throw new NotFoundException("No existen bloques");
        }
        return listaBloques.stream().map(this::bloqueToDto).collect(Collectors.toList());
    }

    private BloqueDto bloqueToDto(Bloque bloque) {
        return modelMapper.map(bloque, BloqueDto.class);
    }
    
}