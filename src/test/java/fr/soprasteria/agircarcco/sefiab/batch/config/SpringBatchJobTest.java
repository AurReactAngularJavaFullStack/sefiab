package fr.soprasteria.agircarcco.sefiab.batch.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringBootTest
@Import(BatchConfig.class) // Import the BatchConfig class to the test context
public class SpringBatchJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private Job fiabilizationJob;

    @BeforeEach
    public void setUp() {
        jobLauncherTestUtils.setJob(fiabilizationJob);
    }

    @Test
    public void testFiabilizationJob() throws Exception {
        // Create job parameters.
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("fileDate", "2023-10-31")
                .addLong("time", System.currentTimeMillis())
                .addDouble("thresholdValue", 100.5)
                .toJobParameters();

        // Launch the job with the provided parameters
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // Assert the job's status
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}


