package mk.frizer.domain.exceptions;

public class CityNotFoundException extends RuntimeException{
    public CityNotFoundException() {
        super("City not found");
    }
}
