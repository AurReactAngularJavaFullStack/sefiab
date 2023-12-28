package fr.soprasteria.agircarcco.sefiab.batch.config;

import fr.soprasteria.agircarcco.sefiab.batch.config.writer.ContactItemWriter;
import fr.soprasteria.agircarcco.sefiab.batch.config.writer.DetteItemWriter;
import fr.soprasteria.agircarcco.sefiab.batch.config.writer.EntrepriseItemWriter;
import fr.soprasteria.agircarcco.sefiab.model.Contact;
import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.repository.EntrepriseRepository;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BatchConfigTest {

    @InjectMocks
    private BatchConfig batchConfig;

    @Mock
    private FlatFileItemReader<Entreprise> entrepriseItemReaderBean;

    @Mock
    private FlatFileItemReader<Contact> contactItemReaderBean;

    @Mock
    private FlatFileItemReader<Dette> detteItemReaderBean;

    @Mock
    private EntrepriseItemWriter entrepriseItemWriter;

    @Mock
    private ContactItemWriter contactItemWriter;

    @Mock
    private DetteItemWriter detteItemWriter;

    @Mock
    private EntityManagerFactory entityManagerFactory;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Mock
    private EntrepriseRepository entrepriseRepository;

    @Mock
    private ResourceLoader resourceLoader;


    @Mock
    private JobRepository jobRepository;

    @Mock
    private JobExplorer jobExplorer;

    @Mock
    private DataSource dataSource;

    @Mock
    private MessagingTemplate messagingTemplate;

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private JobLocator jobLocator;

    @Mock
    private Job myJob;

    // This is now an actual instance, not a mock
    private JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();





    @BeforeEach
    public void setUp() throws Exception {
        // Set the necessary dependencies on jobLauncherTestUtils
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJob(myJob);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        batchConfig.setJobRepository(jobRepository);
    }

    @Test
    public void testBeansCreation() {
        assertNotNull(batchConfig.limitedEntrepriseItemReader());
        assertNotNull(batchConfig.diagnosticItemProcessor());
        assertNotNull(batchConfig.partitionTaskExecutor());
        assertNotNull(batchConfig.entreprisePartitionTaskExecutor());
        assertNotNull(batchConfig.rangePartitioner());
        assertNotNull(batchConfig.outboundReplies());
        assertNotNull(batchConfig.outboundRequests());
        assertNotNull(batchConfig.inboundFlowChannel());
        assertNotNull(batchConfig.messagingTemplate());
        assertNotNull(batchConfig.inboundFlow());
        //... continue for other beans
    }

//    @Test
//    public void testJobCreation() {
//        assertNotNull(batchConfig.fiabilizationjob(jobRepository, null, null, null));
//        // You can expand this to test other properties and behaviors of the job, or more detailed aspects of other beans
//    }

    @Test
    public void testPartitionTaskExecutor() {
        assertNotNull(batchConfig.partitionTaskExecutor());
        assertThat(batchConfig.partitionTaskExecutor()).isNotNull();
    }

    @Test
    public void testMyJob() throws Exception {
        JobInstance jobInstance = new JobInstance(1L, "myJobName"); // Create a JobInstance

        // Using argument matcher to accept any JobParameters instance
        when(jobLauncher.run(eq(myJob), any(JobParameters.class))).thenReturn(new JobExecution(jobInstance, 1L, new JobParameters()));

        JobExecution execution = jobLauncherTestUtils.launchJob();
        assertThat(execution.getStatus()).isEqualTo(BatchStatus.STARTING);
    }

    @Test
    public void testDiagnosticItemProcessor() {
        assertThat(batchConfig.diagnosticItemProcessor()).isNotNull();
    }

    @Test
    public void testRangePartitioner() {
        assertThat(batchConfig.rangePartitioner()).isNotNull();
    }

    @Test
    public void testOutboundReplies() {
        assertThat(batchConfig.outboundReplies()).isNotNull();
    }

    @Test
    public void testOutboundRequests() {
        assertThat(batchConfig.outboundRequests()).isNotNull();
    }

    @Test
    public void testInboundFlowChannel() {
        assertThat(batchConfig.inboundFlowChannel()).isNotNull();
    }

    @Test
    public void testMessagingTemplate() {
        assertThat(batchConfig.messagingTemplate()).isNotNull();
    }

    @Test
    public void testInboundFlow() {
        IntegrationFlow flow = batchConfig.inboundFlow();
        assertThat(flow).isNotNull();
        // You may need to do more specific checks on the flow
    }

//    @Test
//    public void testFiabilizationJob() throws Exception {
//        Job mockJob = mock(Job.class);
//        when(jobLocator.getJob("fiabilizationJob")).thenReturn(mockJob);
//        jobLauncherTestUtils.launchJob();
////        verify(jobLauncher).run(eq(mockJob), any(JobParameters.class));
//        ArgumentCaptor<JobParameters> jobParametersCaptor = ArgumentCaptor.forClass(JobParameters.class);
//        verify(jobLauncher).run(eq(mockJob), jobParametersCaptor.capture());
//
//        ArgumentCaptor<Job> jobCaptor = ArgumentCaptor.forClass(Job.class);
//        verify(jobLauncher).run(jobCaptor.capture(), jobParametersCaptor.capture());
//
//        Job actualJob = jobCaptor.getValue();
//        JobParameters actualParams = jobParametersCaptor.getValue();
//
//    }


    // ... similar tests for other beans ...

//    @Test
//    public void testFiabilizationjob() {
//        Job job = batchConfig.fiabilizationjob(jobRepository, mock(Step.class), mock(Step.class), mock(Step.class));
//        assertThat(job).isNotNull();
//    }

    // Add more detailed tests as needed...
}
