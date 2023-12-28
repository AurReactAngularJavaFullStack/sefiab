package fr.soprasteria.agircarcco.sefiab.batch.listener;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CustomStepExecutionListenerTest {

    private CustomStepExecutionListener listener;
    private StepExecution mockStepExecution;

    // For capturing console output
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        listener = new CustomStepExecutionListener();
        mockStepExecution = Mockito.mock(StepExecution.class);

        // Redirect console output to our ByteArrayOutputStream
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void cleanUp() {
        // Reset the System.out to its original state after each test
        System.setOut(originalOut);
    }

    @Test
    public void testBeforeStep() {
        String stepName = "testStep";
        when(mockStepExecution.getStepName()).thenReturn(stepName);

        listener.beforeStep(mockStepExecution);

        String expectedOutput = "Step Execution started: " + stepName + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testAfterStep() {
        String stepName = "testStep";
        ExitStatus exitStatus = new ExitStatus("COMPLETED");
        when(mockStepExecution.getStepName()).thenReturn(stepName);
        when(mockStepExecution.getExitStatus()).thenReturn(exitStatus);

        ExitStatus returnedExitStatus = listener.afterStep(mockStepExecution);

        String expectedOutput = "Step Execution completed: " + stepName + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        assertEquals(exitStatus, returnedExitStatus);
    }
}

