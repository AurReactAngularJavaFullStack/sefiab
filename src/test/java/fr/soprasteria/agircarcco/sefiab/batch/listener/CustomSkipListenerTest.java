package fr.soprasteria.agircarcco.sefiab.batch.listener;

import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.lang.reflect.Field;

public class CustomSkipListenerTest {
    private CustomSkipListener listener;
    private Logger loggerMock = Mockito.mock(Logger.class);

    @BeforeEach
    public void setUp() throws Exception {
        listener = new CustomSkipListener();

        // Injectez le mock du logger en utilisant la réflexion
        Field loggerField = CustomSkipListener.class.getDeclaredField("log");
        loggerField.setAccessible(true);
        loggerField.set(listener, loggerMock);
    }

    @Test
    public void testOnSkipInRead() {
        Throwable throwable = new Throwable("Test read exception");
        listener.onSkipInRead(throwable);
        Mockito.verify(loggerMock).error("Item skipped during reading: Test read exception");
    }

    @Test
    public void testOnSkipInWrite() {
        Entreprise mockEntreprise = Mockito.mock(Entreprise.class);
        Mockito.when(mockEntreprise.getName()).thenReturn("Test Enterprise");

        Throwable throwable = new Throwable("Test write exception");
        listener.onSkipInWrite(mockEntreprise, throwable);
        Mockito.verify(loggerMock).error("Item skipped during writing: Test write exception - Enterprise: Test Enterprise");
    }

    @Test
    public void testOnSkipInProcess() {
        Entreprise mockEntreprise = Mockito.mock(Entreprise.class);
        Mockito.when(mockEntreprise.getName()).thenReturn("Test Enterprise");

        Throwable throwable = new Throwable("Test process exception");
        listener.onSkipInProcess(mockEntreprise, throwable);
        Mockito.verify(loggerMock).error("Item skipped during processing: Test process exception - Enterprise: Test Enterprise");
    }
}

