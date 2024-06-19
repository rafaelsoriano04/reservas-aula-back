package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.FeriadoDto;
import gestorreservasaulas.entidades.Feriado;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.respositorios.RepositorioFeriado;
import gestorreservasaulas.servicios.ServicioFeriado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioFeriadoImpl implements ServicioFeriado {

    @Autowired
    private RepositorioFeriado repositorioFeriado;

    private final ModelMapper modelMapper;

    @Autowired
    public ServicioFeriadoImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public List<FeriadoDto> getAllFeriados() throws NotFoundException {
        List<Feriado> listaFeriados = repositorioFeriado.findAll();

        if (listaFeriados.isEmpty()) {
            throw new NotFoundException("No hay feriados");
        }

        return listaFeriados.stream().map(this::feriadoToDto).collect(Collectors.toList());
    }

    @Override
    public FeriadoDto save(FeriadoDto feriadoDto) throws NotFoundException {
        feriadoDto.setInicio(sumarUnDia(feriadoDto.getInicio()));
        feriadoDto.setFin(sumarUnDia(feriadoDto.getFin()));
        return feriadoToDto(repositorioFeriado.save(dtoToFeriado(feriadoDto)));
    }

    @Override
    public FeriadoDto updateById(Long id, FeriadoDto feriadoDto) throws NotFoundException, ConflictException {
        Feriado feriadoExistente = repositorioFeriado.findById(id)
                .orElseThrow(() -> new NotFoundException("Feriado not found"));

        // Actualiza campos simples si no son nulos y han cambiado
        if (feriadoDto.getNombre() != null) {
            feriadoExistente.setNombre(feriadoDto.getNombre());
        }
        if (feriadoDto.getInicio() != null) {
            feriadoDto.setInicio(sumarUnDia(feriadoDto.getInicio()));
            feriadoExistente.setInicio(feriadoDto.getInicio());
        }
        if (feriadoDto.getFin() != null) {
            feriadoDto.setFin(sumarUnDia(feriadoDto.getFin()));
            feriadoExistente.setFin(feriadoDto.getFin());
        }

        return feriadoToDto(repositorioFeriado.save(feriadoExistente));
    }

    @Override
    public void deleteById(Long id) throws NotFoundException, ConflictException {
        Feriado feriado = repositorioFeriado.findById(id)
                .orElseThrow(() -> new NotFoundException("Feriado not found"));

        repositorioFeriado.deleteById(id);
    }

    private Feriado dtoToFeriado(FeriadoDto feriadoDto) {
        return modelMapper.map(feriadoDto, Feriado.class);
    }

    private FeriadoDto feriadoToDto(Feriado feriado) {
        return modelMapper.map(feriado, FeriadoDto.class);
    }

    private Date sumarUnDia(Date fecha) {
        // Utilizar Calendar para sumar un día a la fecha
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // Devolver la nueva fecha como Date
        return new Date(calendar.getTimeInMillis());
    }
}
