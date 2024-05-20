/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.entidades.Horario;
import gestorreservasaulas.respositorios.RepositorioHorario;
import gestorreservasaulas.servicios.ServicioHorario;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author fredd
 */
@Service
public class ServicioHorarioImpl implements ServicioHorario{
    @Autowired
    RepositorioHorario repositorioHorario;
     @Override
    public Horario obtenerHorario(Long id) {
        return repositorioHorario.findById(id).orElse(null);
    }

    @Override
    public Boolean crearHorario(Horario horario) {
        if(repositorioHorario.save(horario) != null ){
            return true;
        }else{
            return false;
        }
        
    }

    @Override
    public List<Horario> obtenerHorariosPorAula(Long id) {
       return repositorioHorario.horariosAulas( id); 
    }

    public List<Horario> obtenerHorariosPorLabs(Long id) {
       return repositorioHorario.horariosLabos( id); 
    }
    
    
    @Override
    @Transactional
    public void eliminarHorario(Horario horario) {
         try {
        repositorioHorario.delete(horario);
    } catch (Exception e) {
        System.err.println("Error al eliminar el horario: " + e.getMessage());
        
    }
    }
    
    
}
