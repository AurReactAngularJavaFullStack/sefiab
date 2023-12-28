package fr.soprasteria.agircarcco.sefiab.batch.listener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomItemProcessListenerTest {

    @Mock
    private Object item;

    @Mock
    private Object result;

    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private CustomItemProcessListener<Object, Object> listener;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialise les annotations Mockito

        listener = new CustomItemProcessListener<>();

        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void testBeforeProcess() {
        listener.beforeProcess(item);
        assertThat(outContent.toString()).contains("Before processing: " + item);
    }

    @Test
    public void testAfterProcess() {
        listener.afterProcess(item, result);
        assertThat(outContent.toString()).contains("Processed item: " + item + " to result: " + result);
    }

    @Test
    public void testOnProcessError() {
        Exception exception = new Exception("Test exception");
        listener.onProcessError(item, exception);
        assertThat(errContent.toString()).contains("Error processing item: " + item);
        assertThat(errContent.toString()).contains("Test exception");
    }
}
