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

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

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

        // Comprobar que no exista reservas en esa misma hora y misma fecha
        Reserva coincidenciasReservas = repositorioReserva.getPorHoraFecha(reservaDto.getHora(), reservaDto.getFecha(), reservaDto.getId_espacio());
        if (coincidenciasReservas != null) {
            throw new NotFoundException("Reserva Existente");
        }

        // comprobar que no exista horarios en esa hora y ese dia
        String diaReserva = Util.diaEnEspanol(reservaDto.getFecha().toLocalDate().getDayOfWeek());

        if (lista != null) {
            for (HorarioDto horario : lista) {
                // Asegúrate de que 'horario', 'horario.getDia()' y 'horario.getHora()' no sean nulos antes de usarlos
                if (horario != null && horario.getDia() != null && horario.getHora() != null &&
                        horario.getDia().equalsIgnoreCase(diaReserva) &&
                        horario.getHora().equals(reservaDto.getHora())) {
                    throw new NotFoundException("Horario Existente");
                }
            }
        }

        return reservaToDto(repositorioReserva.save(dtoToReserva(reservaDto)));
    }

    @Override
    public List<ReservaDto> getByWeek(Date fecha, Long id_espacio) throws NotFoundException {
        Date[] weekDates = getMondayAndFriday(fecha);
        Espacio espacio = servicioEspacio.findById(id_espacio);
        //Mapear de Reserva a ReservaDto

        List<Reserva> reservas = repositorioReserva.encontrarSemana(espacio, weekDates[0], weekDates[1]);
        List<ReservaDto> reservasdto = new ArrayList<>();
        for (Reserva reserva : reservas) {
            ReservaDto dto = new ReservaDto();
            dto.setId(reserva.getId());
            dto.setHora(reserva.getHora());
            dto.setFecha(reserva.getFecha());
            dto.setId_espacio(reserva.getEspacio().getId());
            dto.setId_persona(reserva.getPersona().getId());
            dto.setAsunto(reserva.getAsunto());
            reservasdto.add(dto);
        }
        return reservasdto;
    }

    private Reserva dtoToReserva(ReservaDto reservadto) throws NotFoundException {
        Reserva reserva = new Reserva();
        reserva.setId(reservadto.getId());
        reserva.setHora(reservadto.getHora());
        reserva.setFecha(reservadto.getFecha());
        reserva.setAsunto(reservadto.getAsunto());

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
        reservadto.setAsunto(reserva.getAsunto());

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

    public static Date[] getMondayAndFriday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Calcular el lunes
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date monday = new Date(calendar.getTimeInMillis());

        // Calcular el viernes
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Date friday = new Date(calendar.getTimeInMillis());

        return new Date[]{monday, friday};
    }

}
