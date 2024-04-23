package test02.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) { super(message);}
}
