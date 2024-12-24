package mk.frizer.domain.exceptions;

public class SalonTreatmentNotFoundException extends RuntimeException {
    public SalonTreatmentNotFoundException() {
        super("Salon treatment not found");
    }
}
