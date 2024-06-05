package gestorreservasaulas.util;

import java.time.DayOfWeek;


public class Util {
    public static String diaEnEspanol(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "Lunes";
            case TUESDAY:
                return "Martes";
            case WEDNESDAY:
                return "Miercoles";
            case THURSDAY:
                return "Jueves";
            case FRIDAY:
                return "Viernes";
            case SATURDAY:
                return "Sabado";
            case SUNDAY:
                return "Domingo";
            default:
                throw new IllegalArgumentException("Invalid day of the week: " + dayOfWeek);
        }
    }
}

