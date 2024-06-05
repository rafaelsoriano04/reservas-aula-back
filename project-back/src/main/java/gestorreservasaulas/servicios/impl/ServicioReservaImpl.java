package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.HorarioDto;
import gestorreservasaulas.dtos.ReservaDto;
import gestorreservasaulas.entidades.*;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.respositorios.RepositorioAula;
import gestorreservasaulas.respositorios.RepositorioLaboratorio;
import gestorreservasaulas.respositorios.RepositorioPersona;
import gestorreservasaulas.respositorios.RepositorioReserva;
import gestorreservasaulas.servicios.ServicioHorario;
import gestorreservasaulas.servicios.ServicioReserva;
import gestorreservasaulas.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.modelmapper.ModelMapper;

@Service
public class ServicioReservaImpl implements ServicioReserva {
@Autowired
RepositorioReserva repositorioReserva;
    @Autowired
    private RepositorioAula repositorioAula;
    @Autowired
    private RepositorioPersona repositorioPersona;
    @Autowired
    private RepositorioLaboratorio repositorioLaboratorio;
@Autowired
ServicioHorario servicioHorario;

private final ModelMapper modelMapper;

    @Autowired
    public ServicioReservaImpl() {
        this.modelMapper = new ModelMapper();
    }
   /* @Override
    public List<Reserva> obtenerReservaporDia(String dia) {
        return repositorioReserva.getByDia(dia);
    }*/

    @Override
    public ReservaDto crearReserva(ReservaDto reservadto) throws NotFoundException {



        if (reservadto.getId_aula() == null) {
            //ESTO ES PARA LABORATORIOS
            List<HorarioDto> lista = this.servicioHorario.obtenerHorariosPorLabs(reservadto.getId_laboratorio());
            //comprobar que no exista reservas en esa misma hora y misma fecha

            Reserva coincidenciasReservas = repositorioReserva.getPorHoraFechaYLaboratorio(reservadto.getHora() , reservadto.getFecha(),reservadto.getId_laboratorio());
            if (coincidenciasReservas != null){
                throw new NotFoundException("Reserva Existente");
            }

            //comprobar que no exista horarios en esa hora y ese dia
            String diaReserva = Util.diaEnEspanol(reservadto.getFecha().toLocalDate().getDayOfWeek());

            for (HorarioDto horario : lista) {
                if (horario.getDia().equalsIgnoreCase(diaReserva) &&
                        horario.getHora().equals(reservadto.getHora())) {
                    throw new NotFoundException("Horario Existente");
                }
            }


        }else{
            //ESTE ES PARA AULAS
            List<HorarioDto> lista = this.servicioHorario.obtenerHorariosPorAula(reservadto.getId_aula());
            //comprobar que no exista reservas en esa misma hora y misma fecha

            Reserva coincidenciasReservas = repositorioReserva.getPorHoraFechaYAula(reservadto.getHora() , reservadto.getFecha(), reservadto.getId_aula());
            if (coincidenciasReservas !=null){
                throw new NotFoundException("Reserva Existente");
            }

            //comprobar que no exista horarios en esa hora y ese dia
            String diaReserva = Util.diaEnEspanol(reservadto.getFecha().toLocalDate().getDayOfWeek());
            System.out.println("dto");
            System.out.println(diaReserva);
            System.out.println(reservadto.getHora());
            for (HorarioDto horario : lista) {
                System.out.println("lista");
                System.out.println(horario.getDia());
                System.out.println(horario.getHora());
                if (horario.getDia().equalsIgnoreCase(diaReserva) &&
                        horario.getHora().equals(reservadto.getHora())) {
                    throw new NotFoundException("Horario Existente");
                }
            }
             
        }
        Reserva reserva = dtoToReserva(reservadto);
        Reserva reservaGuardada = repositorioReserva.save(reserva);
        return reservaToDto(reservaGuardada);
    }

    private Reserva dtoToReserva(ReservaDto reservadto) throws NotFoundException {
        Reserva reserva = new Reserva();
        reserva.setId(reservadto.getId());
        reserva.setHora(reservadto.getHora());
        reserva.setFecha(reservadto.getFecha());

        if (reservadto.getId_aula() != null) {
            Aula aula = repositorioAula.findById(reservadto.getId_aula())
                    .orElseThrow(() -> new NotFoundException("Aula no encontrada con ID: " + reservadto.getId_aula()));
            reserva.setAula(aula);
        }

        if (reservadto.getId_persona() != null) {
            Persona persona = repositorioPersona.findById(reservadto.getId_persona())
                    .orElseThrow(() -> new NotFoundException("Persona no encontrada con ID: " + reservadto.getId_persona()));
            reserva.setPersona(persona);
        }

        if (reservadto.getId_laboratorio() != null) {
            Laboratorio laboratorio = repositorioLaboratorio.findById(reservadto.getId_laboratorio())
                    .orElseThrow(() -> new NotFoundException("Laboratorio no encontrado con ID: " + reservadto.getId_laboratorio()));
            reserva.setLaboratorio(laboratorio);
        }

        return reserva;
    }

    private ReservaDto reservaToDto(Reserva reserva) {
        if (reserva == null) {
            return null; // O manejar como se considere necesario
        }
        ReservaDto reservadto = new ReservaDto();
        reservadto.setId(reserva.getId());
        reservadto.setHora(reserva.getHora());
        reservadto.setFecha(reserva.getFecha());

        // Asigna id_aula solo si la relación Aula no es nula
        if (reserva.getAula() != null) {
            reservadto.setId_aula(reserva.getAula().getId());
        }

        // Asigna id_laboratorio solo si la relación Laboratorio no es nula
        if (reserva.getLaboratorio() != null) {
            reservadto.setId_laboratorio(reserva.getLaboratorio().getId());
        }

        // Asigna id_persona solo si la relación Persona no es nula
        if (reserva.getPersona() != null) {
            reservadto.setId_persona(reserva.getPersona().getId());
        }

        return reservadto;
    }

}
