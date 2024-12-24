package mk.frizer.domain.exceptions;

public class TreatmentNotFoundException extends RuntimeException{
    public TreatmentNotFoundException() {
        super("Treatment not found");
    }
}
