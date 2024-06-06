package gestorreservasaulas.servicios.impl;

import gestorreservasaulas.dtos.HorarioDto;
import gestorreservasaulas.dtos.ReservaDto;
import gestorreservasaulas.entidades.*;
import gestorreservasaulas.exceptions.ConflictException;
import gestorreservasaulas.exceptions.NotFoundException;
import gestorreservasaulas.respositorios.RepositorioReserva;
import gestorreservasaulas.servicios.ServicioEspacio;
import gestorreservasaulas.servicios.ServicioHorario;
import gestorreservasaulas.servicios.ServicioPersona;
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
    private ServicioEspacio servicioEspacio;
    @Autowired
    private ServicioPersona servicioPersona;
    @Autowired
    ServicioHorario servicioHorario;

    private final ModelMapper modelMapper;

    @Autowired
    public ServicioReservaImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public ReservaDto crearReserva(ReservaDto reservaDto) throws NotFoundException {
        // Para espacios
        List<HorarioDto> lista = this.servicioHorario.obtenerHorariosPorLabs(reservaDto.getId_espacio());

        //comprobar que no exista reservas en esa misma hora y misma fecha
        Reserva coincidenciasReservas = repositorioReserva.getPorHoraFecha(reservaDto.getHora(), reservaDto.getFecha(), reservaDto.getId_espacio());
        if (coincidenciasReservas != null) {
            throw new NotFoundException("Reserva Existente");
        }

        //comprobar que no exista horarios en esa hora y ese dia
        String diaReserva = Util.diaEnEspanol(reservaDto.getFecha().toLocalDate().getDayOfWeek());

        for (HorarioDto horario : lista) {
            if (horario.getDia().equalsIgnoreCase(diaReserva) &&
                    horario.getHora().equals(reservaDto.getHora())) {
                throw new NotFoundException("Horario Existente");
            }
        }

        return reservaToDto(repositorioReserva.save(dtoToReserva(reservaDto)));
    }

    private Reserva dtoToReserva(ReservaDto reservadto) throws NotFoundException {
        Reserva reserva = new Reserva();
        reserva.setId(reservadto.getId());
        reserva.setHora(reservadto.getHora());
        reserva.setFecha(reservadto.getFecha());


        if (reservadto.getId_espacio() != null) {
            reserva.setEspacio(servicioEspacio.findById(reservadto.getId_espacio()));
        }

        if (reservadto.getId_persona() != null) {
            reserva.setPersona(servicioPersona.findById(reservadto.getId_persona()));
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
        if (reserva.getEspacio() != null) {
            reservadto.setId_espacio(reserva.getEspacio().getId());
        }

        // Asigna id_persona solo si la relación Persona no es nula
        if (reserva.getPersona() != null) {
            reservadto.setId_persona(reserva.getPersona().getId());
        }

        return reservadto;
    }

}
