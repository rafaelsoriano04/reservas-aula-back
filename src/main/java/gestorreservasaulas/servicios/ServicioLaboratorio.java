package gestorreservasaulas.servicios;


import gestorreservasaulas.entidades.Laboratorio;

import java.util.List;

public interface ServicioLaboratorio
{
    Laboratorio obtenerLab (String nombre);
    List<Laboratorio> obtenerAulasPorBloque(String bloqueNombre);
}
