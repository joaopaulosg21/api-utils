package projeto.api.utils.exceptions;

public class DailyTaskNotFoundException extends RuntimeException{

    public DailyTaskNotFoundException() {
        super("Daily task not found",null,false,false);
    }
}
