package se.yrgo.exception;

public class NoAvailableBookCopiesException extends RuntimeException {
    public NoAvailableBookCopiesException(String message) {
        super(message);
    }
}
