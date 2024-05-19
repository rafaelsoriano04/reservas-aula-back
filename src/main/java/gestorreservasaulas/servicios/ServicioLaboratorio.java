package gestorreservasaulas.servicios;



import gestorreservasaulas.entidades.Laboratorio;

import java.util.List;

public interface ServicioLaboratorio
{

    Laboratorio obtenerLab (String nombre);
    List<Laboratorio> findByBloque(Long id_bloque);
    Laboratorio crearLaboratorio (Laboratorio lab);
}
