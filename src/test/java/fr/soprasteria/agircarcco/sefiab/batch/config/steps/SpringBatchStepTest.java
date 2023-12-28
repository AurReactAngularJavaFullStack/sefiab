//package fr.soprasteria.agircarcco.sefiab.batch.config.steps;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Timeout;
//import org.springframework.batch.core.*;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.test.JobLauncherTestUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@ActiveProfiles("test")
//public class SpringBatchStepTest {
//
//    @Autowired
//    private JobLauncherTestUtils jobLauncherTestUtils;
//
//    @Autowired
//    @Qualifier("entrepriseWorkerStep")
//    private Step entrepriseWorkerStep;
//
//    @Autowired
//    private Step contactWorkerStep;
//
//    @Autowired
//    private Step detteWorkerStep;
//
//    @Autowired
//    private JobRepository jobRepository;
//
//    @Test
//    public void testEntrepriseWorkerStep() throws Exception {
//        // Set up JobParameters if needed
//        JobParameters jobParameters = new JobParametersBuilder()
//                // .addString("parameterName", "value")  // Uncomment and modify as per your requirements
//                .toJobParameters();
//
//        // Execute the step
//        JobExecution stepExecution = jobLauncherTestUtils.launchStep("entrepriseWorkerStep", jobParameters);
//
//        // Assert the results
//        assertEquals(ExitStatus.COMPLETED, stepExecution.getExitStatus());
//        // Add any other assertions based on your specific business logic
//    }
//
//
//    @Test
//    @Timeout(5)
//    public void testEntrepriseWorkerStepCompletion() throws Exception {
//        runStepTest(entrepriseWorkerStep);
//    }
//
//    @Test
//    @Timeout(5)
//    public void testContactWorkerStepCompletion() throws Exception {
//        runStepTest(contactWorkerStep);
//    }
//
//    @Test
//    @Timeout(5)
//    public void testDetteWorkerStepCompletion() throws Exception {
//        runStepTest(detteWorkerStep);
//    }
//
//    private void runStepTest(Step step) throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder().toJobParameters();
//
//        JobExecution jobExecution = jobLauncherTestUtils.launchStep(String.valueOf(step.getName()), jobParameters);
//
//        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
//
//        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
//
//        assertThat(stepExecution.getReadCount()).isPositive();
//        assertThat(stepExecution.getWriteCount()).isEqualTo(stepExecution.getReadCount());
//        assertThat(stepExecution.getSkipCount()).isEqualTo(0);
//    }
//
//    @Configuration
//    static class TestConfig {
//
//        @Bean
//        public JobLauncherTestUtils jobLauncherTestUtils() {
//            JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
//
//            // If you have a job bean, set it here:
//            // jobLauncherTestUtils.setJob(yourJobBean);
//
//            return jobLauncherTestUtils;
//        }
//    }
//}
//
