package authentication.authentication.authen.controller;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException() {
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
