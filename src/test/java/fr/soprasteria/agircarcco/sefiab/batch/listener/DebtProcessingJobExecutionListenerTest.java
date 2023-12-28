package fr.soprasteria.agircarcco.sefiab.batch.listener;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import fr.soprasteria.agircarcco.sefiab.service.DataCleanupService;
import fr.soprasteria.agircarcco.sefiab.service.EmailService;
import fr.soprasteria.agircarcco.sefiab.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class DebtProcessingJobExecutionListenerTest {

    @InjectMocks
    private DebtProcessingJobExecutionListener listener;

    @Mock
    private JobExecution jobExecution;

    @Mock
    private EmailService emailService;

    @Mock
    private DataCleanupService dataCleanupService;

    @Mock
    private ReportService reportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void tearDown() {
        Mockito.framework().clearInlineMocks();
    }

    @Test
    public void testBeforeJob_NoExceptionThrown() {
        // Simply call the method to ensure no exception is thrown.
        listener.beforeJob(jobExecution);
        // No exceptions should be thrown. No more verifications are needed for now.
    }

    @Test
    public void testAfterJob_WithCompletedStatus() throws IOException {
        when(jobExecution.getExitStatus()).thenReturn(ExitStatus.COMPLETED);

        listener.afterJob(jobExecution);

        verify(emailService).sendSuccessNotification();
        verify(dataCleanupService).performCleanup();
        verify(reportService).generateReport(anyList());
    }

    @Test
    public void testAfterJob_WithCompletedStatus_AndReportGenerationFails() throws IOException {
        when(jobExecution.getExitStatus()).thenReturn(ExitStatus.COMPLETED);
        doThrow(IOException.class).when(reportService).generateReport(anyList());

        try {
            listener.afterJob(jobExecution);
        } catch (RuntimeException ex) {
            assertEquals(IOException.class, ex.getCause().getClass());
        }

        verify(emailService).sendSuccessNotification();
        verify(dataCleanupService).performCleanup();
    }

    @Test
    public void testAfterJob_WithFailedStatus() throws IOException {
        when(jobExecution.getExitStatus()).thenReturn(ExitStatus.FAILED);

        listener.afterJob(jobExecution);

        verify(emailService).sendFailureAlert();
        verify(dataCleanupService, never()).performCleanup();
        verify(reportService, never()).generateReport(anyList());
    }
}


