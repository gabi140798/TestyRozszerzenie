package test02.exceptions;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) { super(message);}
}
