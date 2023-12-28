package fr.soprasteria.agircarcco.sefiab.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MyExceptionTest {

    @Test
    public void testDefaultConstructor() {
        MyException myException = new MyException();
        assertNull(myException.getMessage());
        assertNull(myException.getCause());
    }

    @Test
    public void testMessageConstructor() {
        String message = "Custom exception message";
        MyException myException = new MyException(message);
        assertEquals(message, myException.getMessage());
        assertNull(myException.getCause());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        String message = "Custom exception message";
        Throwable cause = new Throwable("Cause exception");
        MyException myException = new MyException(message, cause);
        assertEquals(message, myException.getMessage());
        assertEquals(cause, myException.getCause());
    }
}
