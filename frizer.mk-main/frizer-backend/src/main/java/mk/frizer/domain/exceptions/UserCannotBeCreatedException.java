package mk.frizer.domain.exceptions;

public class UserCannotBeCreatedException extends RuntimeException{
    public UserCannotBeCreatedException() {
        super("User cannot be created");
    }
}
