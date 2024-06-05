package gestorreservasaulas.controllers;

import gestorreservasaulas.dtos.AulaDto;
import gestorreservasaulas.dtos.LaboratorioDto;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.servicios.ServicioAula;
import gestorreservasaulas.servicios.ServicioLaboratorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lab")
@CrossOrigin(origins = "http://localhost:5173")
public class LaboratorioController {
    @Autowired
    private ServicioLaboratorio servicioLaboratorio;

    @GetMapping("/bloque/{id}")
    public List<LaboratorioDto> getAllByBloque(@PathVariable Long id) throws NotFoundException {
        return servicioLaboratorio.findByBloque(id);
    }

    @PostMapping
    public LaboratorioDto save(@RequestBody LaboratorioDto laboratorioDto) throws NotFoundException, ConflictException {
        return servicioLaboratorio.save(laboratorioDto);
    }

    @PutMapping("/{id}")
    public LaboratorioDto update(@PathVariable Long id, @RequestBody LaboratorioDto request) throws NotFoundException, ConflictException {
        return servicioLaboratorio.editarLab(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws NotFoundException {
        servicioLaboratorio.eliminarLab(id);
    }
}
