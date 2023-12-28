package fr.soprasteria.agircarcco.sefiab.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetryableExceptionTest {

    @Test
    public void testDefaultConstructor() {
        RetryableException exception = new RetryableException();
        assertEquals(null, exception.getMessage());
        assertEquals(null, exception.getCause());
    }

    @Test
    public void testConstructorWithMessage() {
        String message = "Test Exception Message";
        RetryableException exception = new RetryableException(message);
        assertEquals(message, exception.getMessage());
        assertEquals(null, exception.getCause());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String message = "Test Exception Message";
        Throwable cause = new RuntimeException("Test Cause");
        RetryableException exception = new RetryableException(message, cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}

