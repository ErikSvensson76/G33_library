package se.lexicon.jpa_workshop.exception;

public class AppResourceNotFoundException extends RuntimeException{
    public AppResourceNotFoundException() {
    }

    public AppResourceNotFoundException(String message) {
        super(message);
    }
}
