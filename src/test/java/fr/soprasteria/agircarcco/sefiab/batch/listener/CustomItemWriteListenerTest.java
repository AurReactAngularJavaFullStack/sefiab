package fr.soprasteria.agircarcco.sefiab.batch.listener;

import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.helpers.MessageFormatter;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomItemWriteListenerTest {

    @Mock
    private Logger log;

    @InjectMocks
    private CustomItemWriteListener customItemWriteListener;

    @Captor
    private ArgumentCaptor<String> logCaptor;

    @Captor
    private ArgumentCaptor<String> formatCaptor;

    @Captor
    private ArgumentCaptor<Integer> itemCountCaptor;

    @Captor
    private ArgumentCaptor<String> errorMessageCaptor;

    @Captor
    private ArgumentCaptor<Object> argCaptor;

    @Captor
    private ArgumentCaptor<Exception> exceptionCaptor;


    @BeforeEach
    public void setUp() {
        // Try manually setting the logger for the CustomItemWriteListener
        customItemWriteListener.setLogger(log);
    }

    @Test
    public void testBeforeWrite() {
        List<Entreprise> items = Arrays.asList(new Entreprise(), new Entreprise());
        customItemWriteListener.beforeWrite(items);

        verify(log, times(1)).info(formatCaptor.capture(), argCaptor.capture());

        String loggedMessage = MessageFormatter.arrayFormat(formatCaptor.getValue(), new Object[]{argCaptor.getValue()}).getMessage();

        assertEquals("About to write 2 items.", loggedMessage);
    }

    @Test
    public void testAfterWrite() {
        List<Entreprise> items = Arrays.asList(new Entreprise(), new Entreprise());
        customItemWriteListener.afterWrite(items);

        verify(log, times(1)).info(formatCaptor.capture(), itemCountCaptor.capture());

        String loggedMessage = MessageFormatter.arrayFormat(formatCaptor.getValue(), new Object[]{itemCountCaptor.getValue()}).getMessage();
        assertEquals("2 items have been written successfully.", loggedMessage);
    }

    @Test
    public void testOnWriteError() {
        Exception exception = new RuntimeException("Test Error");
        List<Entreprise> items = Arrays.asList(new Entreprise());
        customItemWriteListener.onWriteError(exception, items);

        verify(log, times(1)).error(formatCaptor.capture(), errorMessageCaptor.capture(), exceptionCaptor.capture());

        String loggedMessage = MessageFormatter.arrayFormat(formatCaptor.getValue(), new Object[]{errorMessageCaptor.getValue()}).getMessage();

        assertEquals("Error occurred while writing items. Error: Test Error", loggedMessage);
        assertEquals(exception, exceptionCaptor.getValue());
    }

}
