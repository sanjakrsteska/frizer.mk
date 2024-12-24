package mk.frizer.domain.exceptions;

public class SalonNotFoundException extends RuntimeException{
    public SalonNotFoundException() {
        super("Salon not found");
    }
}
