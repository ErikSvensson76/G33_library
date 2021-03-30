package se.lexicon.jpa_workshop.exception;

public class BookOnLoanException extends RuntimeException{
    public BookOnLoanException() {
    }

    public BookOnLoanException(String message) {
        super(message);
    }
}
