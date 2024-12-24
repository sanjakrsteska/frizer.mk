package mk.frizer.domain.exceptions;

public class TagNotFoundException extends RuntimeException{
    public TagNotFoundException() {
        super("Tag not found");
    }
}
