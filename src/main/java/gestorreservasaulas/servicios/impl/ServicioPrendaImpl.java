package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.entidades.Prenda;
import gestorreservasaulas.respositorios.RepositorioPrenda;
import gestorreservasaulas.servicios.ServicioPrenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioPrendaImpl implements ServicioPrenda {

    @Autowired
    private RepositorioPrenda repositorioPrenda;

    @Override
    public List<Prenda> obtenerTodasLasPrendas() {
        return repositorioPrenda.findAll();
    }

    @Override
    public Prenda agregarPrenda(Prenda prenda) {
        return repositorioPrenda.save(prenda);
    }
}
