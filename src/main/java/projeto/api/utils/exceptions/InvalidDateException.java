package projeto.api.utils.exceptions;

public class InvalidDateException extends RuntimeException{

    public InvalidDateException() {
        super("Invalid date",null,false,false);
    }
}
