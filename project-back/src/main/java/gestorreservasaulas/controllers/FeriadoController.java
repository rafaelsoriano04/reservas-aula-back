package gestorreservasaulas.controllers;

import gestorreservasaulas.dtos.FeriadoDto;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.servicios.ServicioFeriado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/feriado")
@CrossOrigin(origins = "http://localhost:5173")
public class FeriadoController {

    @Autowired
    ServicioFeriado servicioFeriado;

    @PostMapping
    public FeriadoDto save(@RequestBody FeriadoDto feriadoDto) throws NotFoundException, ConflictException {
        return servicioFeriado.save(feriadoDto);
    }

    @PutMapping("/{id}")
    public FeriadoDto updateById(@PathVariable Long id, @RequestBody FeriadoDto request) throws NotFoundException, ConflictException {
        return servicioFeriado.updateById(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) throws NotFoundException , ConflictException{
        servicioFeriado.deleteById(id);
    }

    @GetMapping
    public List<FeriadoDto> getAllFeriados() throws NotFoundException {
        return servicioFeriado.getAllFeriados();
    }

    @GetMapping("/filtered")
    public List<FeriadoDto> getAllFiltered(@RequestParam(required = false) Date inicio,
                                           @RequestParam(required = false) Date fin) {
        return servicioFeriado.findAllByParams(inicio, fin);
    }

}
