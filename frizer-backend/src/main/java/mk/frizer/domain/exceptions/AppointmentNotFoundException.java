package mk.frizer.domain.exceptions;

public class AppointmentNotFoundException extends RuntimeException{
    public AppointmentNotFoundException() {
        super("Appointment not found");
    }
}
