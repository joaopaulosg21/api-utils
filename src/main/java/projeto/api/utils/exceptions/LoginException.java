package projeto.api.utils.exceptions;

public class LoginException extends RuntimeException{

    public LoginException() {
        super("Wrong email or password",null,false,false);
    }
}
