package gestorreservasaulas.servicios;

import gestorreservasaulas.entidades.Prenda;

import java.util.List;

public interface ServicioPrenda {
   List<Prenda> obtenerTodasLasPrendas();
    Prenda agregarPrenda(Prenda prenda);
}
