package fr.soprasteria.agircarcco.sefiab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SefiabApplicationTest {

	@Mock
	private JobLauncher jobLauncher;

	@Mock
	private Job fiabilizationJob;

	@InjectMocks
	private SefiabApplication sefiabApplication;

	@Test
	public void testRunBatchJob() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("fileDate", "2023-10-31")
				.addLong("time", System.currentTimeMillis())
				.addDouble("thresholdValue", 100.5)
				.toJobParameters();

		JobExecution jobExecution = new JobExecution(1L);
		jobExecution.setStatus(BatchStatus.COMPLETED);

		when(jobLauncher.run(eq(fiabilizationJob), any(JobParameters.class))).thenReturn(jobExecution);

		sefiabApplication.runBatchJob();

		verify(jobLauncher, times(1)).run(eq(fiabilizationJob), any(JobParameters.class));
	}
}
