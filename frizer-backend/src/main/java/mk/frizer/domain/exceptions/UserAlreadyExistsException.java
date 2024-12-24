package mk.frizer.domain.exceptions;

public class UserAlreadyExistsException  extends  RuntimeException{
    public UserAlreadyExistsException(String message) {
        super(String.format(message));
    }
}
