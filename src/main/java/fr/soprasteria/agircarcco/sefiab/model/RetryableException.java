package fr.soprasteria.agircarcco.sefiab.model;

public class RetryableException extends Exception {

    public RetryableException() {
        super();
    }

    public RetryableException(String message) {
        super(message);
    }

    public RetryableException(String message, Throwable cause) {
        super(message, cause);
    }
}