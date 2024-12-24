package mk.frizer.domain.exceptions;

public class AppointmentNotDivisibleBy20Minutes extends RuntimeException{
    public AppointmentNotDivisibleBy20Minutes(String message){
        super(message);
    }
}
