package projeto.api.utils.exceptions;

public class EmailAlreadyUsedException extends RuntimeException{
    public EmailAlreadyUsedException() {
        super("Email already used",null,false,false);
    }
}
