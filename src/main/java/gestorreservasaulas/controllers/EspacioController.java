package gestorreservasaulas.controllers;

import gestorreservasaulas.dtos.EspacioDto;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.servicios.ServicioEspacio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/espacio")
@CrossOrigin(origins = {"http://localhost:5173", "http://serviciosfisei.uta.edu.ec:9060"})
public class EspacioController {
    @Autowired
    ServicioEspacio servicioEspacio;

    @GetMapping("/bloque/{id_bloque}")
    public List<EspacioDto> getByBloque(@PathVariable Long id_bloque) throws NotFoundException {
        return servicioEspacio.getByBloque(id_bloque);
    }

    @PostMapping
    public EspacioDto save(@RequestBody EspacioDto espacioDto) throws NotFoundException, ConflictException {
        return servicioEspacio.save(espacioDto);
    }

    @PutMapping("/{id}")
    public EspacioDto updateById(@PathVariable Long id, @RequestBody EspacioDto request) throws NotFoundException, ConflictException {
        return servicioEspacio.updateById(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) throws NotFoundException, ConflictException {
        servicioEspacio.deleteById(id);
    }

    @GetMapping
    public List<EspacioDto> getAllEspacios() {
        return servicioEspacio.findAll();
    }

    @GetMapping("/filtered")
    public List<EspacioDto> findAllWithParams(@RequestParam(required = false) String nombre,
                                              @RequestParam(required = false) String tipo,
                                              @RequestParam(required = false) Long id_bloque) {
        return servicioEspacio.findAllWithParams(nombre, tipo, id_bloque);
    }
}
