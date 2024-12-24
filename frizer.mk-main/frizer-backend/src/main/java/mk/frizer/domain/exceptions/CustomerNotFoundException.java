package mk.frizer.domain.exceptions;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException() {
        super("Customer not found");
    }
}
