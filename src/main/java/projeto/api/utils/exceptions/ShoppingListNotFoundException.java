package projeto.api.utils.exceptions;

public class ShoppingListNotFoundException extends RuntimeException{

    public ShoppingListNotFoundException() {
        super("Shopping list not found",null,false,false);
    }
}
