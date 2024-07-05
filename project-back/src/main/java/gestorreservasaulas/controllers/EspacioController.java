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
@CrossOrigin(origins = "http://localhost:5173")
public class EspacioController {

    @Autowired
    ServicioEspacio servicioEspacio;

    @GetMapping("/bloque/{id_bloque}")
    public List<EspacioDto> getByBloque(@PathVariable Long id_bloque) throws NotFoundException {
        return servicioEspacio.getByBloque(id_bloque);
    }

    @GetMapping("/filter-nombre/{nombre}")
    public List<EspacioDto> getByNombre(@PathVariable String nombre) throws NotFoundException {
        return servicioEspacio.getByNombre(nombre);
    }

    @GetMapping("/filter-tipo/{tipo}")
    public List<EspacioDto> getByTipo(@PathVariable String tipo) throws NotFoundException {
        return servicioEspacio.getByTipo(tipo);
    }

    @GetMapping("/filter-tipo-nombre/{tipo}/{nombre}")
    public List<EspacioDto> getByTipoNombre(@PathVariable String tipo, @PathVariable String nombre) throws NotFoundException {
        return servicioEspacio.getByTipoNombre(tipo, nombre);
    }

    @GetMapping("/filter-tipo-bloque/{tipo}/{id_bloque}")
    public List<EspacioDto> getByTipoBloque(@PathVariable String tipo, @PathVariable Long id_bloque) throws NotFoundException {
        return servicioEspacio.getByTipoBloque(tipo, id_bloque);
    }

    @GetMapping("/filter-bloque-nombre/{id_bloque}/{nombre}")
    public List<EspacioDto> getByBloqueNombre(@PathVariable Long id_bloque, @PathVariable String nombre) throws NotFoundException {
        return servicioEspacio.getByBloqueNombre(id_bloque, nombre);
    }

    @GetMapping("/filter-bloque-nombre-tipo/{id_bloque}/{nombre}/{tipo}")
    public List<EspacioDto> getByBloqueNombreTipo(@PathVariable Long id_bloque, @PathVariable String nombre, @PathVariable String tipo) throws NotFoundException {
        return servicioEspacio.getByBloqueNombreTipo(id_bloque, nombre, tipo);
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
    public void deleteById(@PathVariable Long id) throws NotFoundException , ConflictException{
        servicioEspacio.deleteById(id);
    }

    @GetMapping
    public List<EspacioDto> getAllEspacios() {
        return servicioEspacio.findAll();
    }
}
