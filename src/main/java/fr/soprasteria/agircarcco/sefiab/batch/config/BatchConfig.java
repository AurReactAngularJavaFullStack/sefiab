package fr.soprasteria.agircarcco.sefiab.batch.config;

import fr.soprasteria.agircarcco.sefiab.batch.config.partitioning.SampleRangePartitioner;
import fr.soprasteria.agircarcco.sefiab.batch.config.process.ContactItemProcessor;
import fr.soprasteria.agircarcco.sefiab.batch.config.process.DetteItemProcessor;
import fr.soprasteria.agircarcco.sefiab.batch.config.process.DiagnosticItemProcessor;
import fr.soprasteria.agircarcco.sefiab.batch.config.process.EntrepriseItemProcessor;
import fr.soprasteria.agircarcco.sefiab.batch.config.reader.LimitedItemReader;
import fr.soprasteria.agircarcco.sefiab.batch.config.writer.ContactItemWriter;
import fr.soprasteria.agircarcco.sefiab.batch.config.writer.DetteItemWriter;
import fr.soprasteria.agircarcco.sefiab.batch.config.writer.EntrepriseItemWriter;
import fr.soprasteria.agircarcco.sefiab.batch.listener.CustomItemReadListener;
import fr.soprasteria.agircarcco.sefiab.batch.listener.CustomItemWriteListener;
import fr.soprasteria.agircarcco.sefiab.batch.listener.CustomSkipListener;
import fr.soprasteria.agircarcco.sefiab.batch.listener.DebtProcessingJobExecutionListener;
import fr.soprasteria.agircarcco.sefiab.model.Contact;
import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.model.MyException;
import fr.soprasteria.agircarcco.sefiab.repository.EntrepriseRepository;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.integration.partition.MessageChannelPartitionHandler;
import org.springframework.batch.integration.partition.RemotePartitioningManagerStepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.PollableChannel;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableIntegration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    @Qualifier("entrepriseItemReaderBean")
    private FlatFileItemReader<Entreprise> entrepriseItemReaderBean;

    @Autowired
    @Qualifier("contactItemReaderBean")
    private FlatFileItemReader<Contact> contactItemReaderBean;

    @Autowired
    @Qualifier("detteItemReaderBean")
    private FlatFileItemReader<Dette> detteItemReaderBean;

    private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);

    @Autowired
    private EntrepriseItemWriter entrepriseItemWriter;

    @Autowired
    private ContactItemWriter contactItemWriter;

    @Autowired
    private DetteItemWriter detteItemWriter;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    private JobRepository jobRepository;

    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    @Bean
    public ItemReader<Entreprise> limitedEntrepriseItemReader() {
        return new LimitedItemReader<>(entrepriseItemReaderBean, 50);
    }

    @Bean
    public DiagnosticItemProcessor diagnosticItemProcessor() {
        return new DiagnosticItemProcessor();
    }


    @Bean(name = "partitionTaskExecutor")
    public TaskExecutor partitionTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setQueueCapacity(25);
        taskExecutor.setThreadNamePrefix("partition-thread-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean(name = "entreprisePartitionTaskExecutor")
    public TaskExecutor entreprisePartitionTaskExecutor() {
        //return new SimpleAsyncTaskExecutor("partition-thread-");
        return new SyncTaskExecutor();
    }

    @Bean
    public Partitioner rangePartitioner() {
        return new SampleRangePartitioner();
    }

    @Bean
    public PollableChannel outboundReplies() {
        return new QueueChannel();
    }

    @Bean
    public DirectChannel outboundRequests() {
        return new DirectChannel();
    }

    @Bean
    public PollableChannel inboundFlowChannel() {
        return new QueueChannel();
    }

    @Bean
    public MessagingTemplate messagingTemplate() {
        MessagingTemplate messagingTemplate = new MessagingTemplate();
        messagingTemplate.setDefaultChannel(outboundRequests());
        messagingTemplate.setReceiveTimeout(100000);
        return messagingTemplate;
    }

    @Bean
    public IntegrationFlow inboundFlow() {
        return flow -> flow
                .channel(inboundFlowChannel())
                .channel(outboundReplies());
    }

    @Bean
    public RemotePartitioningManagerStepBuilderFactory masterStepBuilderFactory(JobRepository jobRepository, JobExplorer jobExplorer) {
        return new RemotePartitioningManagerStepBuilderFactory(jobRepository, jobExplorer);
    }

    @Bean
    public PartitionHandler partitionHandler(Step contactWorkerStep, JobExplorer jobExplorer, MessagingTemplate messagingTemplate) {
        MessageChannelPartitionHandler partitionHandler = new MessageChannelPartitionHandler();
        partitionHandler.setGridSize(10);
        partitionHandler.setStepName(contactWorkerStep.getName());
        partitionHandler.setJobExplorer(jobExplorer);
        partitionHandler.setMessagingOperations(messagingTemplate);
        partitionHandler.setReplyChannel(inboundFlowChannel());
        return partitionHandler;
    }

    @Bean
    public JobExplorer jobExplorer(DataSource dataSource) throws Exception {
        JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
        jobExplorerFactoryBean.setDataSource(dataSource);
        jobExplorerFactoryBean.setTransactionManager(transactionManager);
        jobExplorerFactoryBean.afterPropertiesSet();
        return jobExplorerFactoryBean.getObject();
    }


    private Resource[] getResources(String pattern) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            return resolver.getResources(pattern);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la récupération des fichiers pour le partitionnement", e);
        }
    }

    @Bean
    public Partitioner entreprisePartitioner() {
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        partitioner.setResources(getResources("classpath:entreprise.csv"));
        return partitioner;
    }

    @Bean
    public Partitioner contactPartitioner() {
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        partitioner.setResources(getResources("classpath:contact.csv"));
        return partitioner;
    }

    @Bean
    public Partitioner dettePartitioner() {
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        partitioner.setResources(getResources("classpath:dette.csv"));
        return partitioner;
    }


    @Bean
    public Step entrepriseWorkerStep(JobRepository jobRepository,
                                     PlatformTransactionManager transactionManager,
                                     EntrepriseItemProcessor entrepriseItemprocessor,
                                     FlatFileItemReader<Entreprise> entrepriseItemReaderBean,
                                     ItemWriter<Entreprise> entrepriseItemWriter,
                                     DebtProcessingJobExecutionListener debtProcessingListener) {

        //Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        //retryableExceptions.put(MyException.class, true);
        return new StepBuilder("entrepriseWorkerStep", jobRepository)
                .<Entreprise, Entreprise>chunk(50,transactionManager) // Par exemple, traitez 100 éléments à la fois. Ajustez selon vos besoins.
                .reader(limitedEntrepriseItemReader())
                .processor(entrepriseItemprocessor)
                .writer(entrepriseItemWriter)
                //.faultTolerant()
                //.skipLimit(10)
                //.skip(MyException.class)
                //.retryLimit(3)
                //.retryPolicy(new SimpleRetryPolicy(3))
                .listener(new CustomSkipListener())
                .listener(new CustomItemReadListener<>())
                .listener(new CustomItemWriteListener())
                .listener(debtProcessingListener)
                .build();
    }

    @Bean
    public Step contactWorkerStep(JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager,
                                  ContactItemProcessor contactItemProcessor,
                                  FlatFileItemReader<Contact> contactItemReaderBean,
                                  ItemWriter<Contact> contactItemWriter,
                                  DebtProcessingJobExecutionListener debtProcessingListener) {
        return new StepBuilder("contactWorkerStep", jobRepository)
                .<Contact, Contact>chunk(100, transactionManager) // Par exemple, traitez 100 éléments à la fois. Ajustez selon vos besoins.
                .reader(contactItemReaderBean)
                .processor(contactItemProcessor)
                .writer(contactItemWriter)
                .faultTolerant()
                .skipLimit(10)
                .skip(MyException.class)
                .retryLimit(3)
                .retryPolicy(new SimpleRetryPolicy(3))
                .listener(new CustomSkipListener())
                .listener(new CustomItemReadListener<>())
                .listener(new CustomItemWriteListener())
                .listener(debtProcessingListener)
                .build();
    }

    @Bean
    public Step detteWorkerStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager,
                                DetteItemProcessor detteItemProcessor,
                                FlatFileItemReader<Dette> detteItemReaderBean,
                                ItemWriter<Dette> detteItemWriter,
                                DebtProcessingJobExecutionListener debtProcessingListener) {
        return new StepBuilder("detteWorkerStep", jobRepository)
                .<Dette, Dette>chunk(100,transactionManager) // Par exemple, traitez 100 éléments à la fois. Ajustez selon vos besoins.
                .reader(detteItemReaderBean)
                .processor(detteItemProcessor)
                .writer(detteItemWriter)
                .faultTolerant()
                .skipLimit(10)
                .skip(MyException.class)
                .retryLimit(3)
                .retryPolicy(new SimpleRetryPolicy(3))
                .listener(new CustomSkipListener())
                .listener(new CustomItemReadListener<>())
                .listener(new CustomItemWriteListener())
                .listener(debtProcessingListener)
                .build();
    }


    //@Bean
    //public Step entrepriseMasterStep(JobRepository jobRepository,
                                     //@Qualifier("entreprisePartitioner") Partitioner partitioner,
                                     //@Qualifier("entrepriseWorkerStep") Step entrepriseWorkerStep) {

        //return new StepBuilder("entrepriseMasterStep", jobRepository)
                //.partitioner("entrepriseWorkerStep", entreprisePartitioner())
                //.step(entrepriseWorkerStep)
                //.taskExecutor(entreprisePartitionTaskExecutor())
                //.build();
    //}



    @Bean
    public Step contactMasterStep(JobRepository jobRepository,
                                  @Qualifier("contactPartitioner")Partitioner partitioner,
                                  @Qualifier("contactWorkerStep") Step contactWorkerStep,
                                  JobExplorer jobExplorer,
                                  MessagingTemplate messagingTemplate) {

        return new StepBuilder("contactMasterStep", jobRepository)
                .partitioner("contactWorkerStep", contactPartitioner())
                .step(contactWorkerStep)
                .taskExecutor(partitionTaskExecutor())
                .build();
    }


    @Bean
    public Step detteMasterStep(JobRepository jobRepository,
                                @Qualifier("dettePartitioner")Partitioner partitioner,
                                @Qualifier("detteWorkerStep") Step detteWorkerStep,
                                  JobExplorer jobExplorer,
                                  MessagingTemplate messagingTemplate) {

        return new StepBuilder("detteMasterStep", jobRepository)
                .partitioner("detteWorkerStep", dettePartitioner())
                .step(detteWorkerStep)
                .taskExecutor(partitionTaskExecutor())
                .build();
    }


    @Bean
    public Flow parallelSteps(@Qualifier("contactMasterStep") Step contactMasterStep,
                              @Qualifier("detteMasterStep") Step detteMasterStep) {
        Flow contactFlow = new FlowBuilder<SimpleFlow>("contactFlow")
                .start(contactMasterStep)
                .build();

        Flow detteFlow = new FlowBuilder<SimpleFlow>("detteFlow")
                .start(detteMasterStep)
                .build();

        return new FlowBuilder<SimpleFlow>("parallelSteps")
                .split(new SimpleAsyncTaskExecutor())
                .add(contactFlow, detteFlow)
                .build();
    }
    /*@Bean
    public Job fiabilizationjob(JobRepository jobRepository,
                                //@Qualifier("entrepriseMasterStep") Step entrepriseMasterStep,
                                @Qualifier("entrepriseWorkerStep") Step entrepriseWorkerStep,
                                @Qualifier("contactMasterStep") Step contactMasterStep,
                                @Qualifier("detteMasterStep") Step detteMasterStep
                                ) {
        return new JobBuilder("fiabilizationJob", jobRepository)
                //.start(entrepriseMasterStep)
                .start(entrepriseWorkerStep)
                .next(contactMasterStep)
                .next(detteMasterStep)
                .build();
    }*/

    /*Parallel Steps vs. Partitioning:
    Parallel Steps: These are used to run multiple steps in parallel.
    This is especially useful when you have steps that are independent of each other
    and can run at the same time to improve performance.
    The parallelSteps bean in your configuration creates a flow that splits into
    two parallel flows (contactFlow and detteFlow), essentially running
    contactMasterStep and detteMasterStep in parallel.

     Partitioning: This is used to run the same step multiple times in parallel
     but with different inputs. This is useful when you have a large dataset and
     want to process it in chunks, in parallel.
     Each of these chunks or partitions may be processed independently.
     Your contactPartitioner and dettePartitioner beans are preparing partitions
     for contactMasterStep and detteMasterStep respectively.*/

    /* Valider
    Replace the Flow with a Direct Step Reference:
    If you don't need any decision logic, you can run the parallel steps as a
    single step by using a JobFlowExecutor:*/

    @Bean
    public Step parallelStep(JobRepository jobRepository,
                             @Qualifier("parallelSteps") Flow parallelSteps) {
        return new StepBuilder("parallelStep", jobRepository)
                .flow(parallelSteps)
                .build();
    }

    @Bean
    public Job fiabilizationjob(JobRepository jobRepository,
                                @Qualifier("entrepriseWorkerStep") Step entrepriseWorkerStep,
                                @Qualifier("parallelStep") Step parallelStep) {
        return new JobBuilder("fiabilizationJob", jobRepository)
                .start(entrepriseWorkerStep)
                .next(parallelStep)
                .build();
    }

    /*Use a JobExecutionDecider:
    If you need some decision logic to determine whether or not to run the
    parallel steps:*/
    /*@Bean
    public JobExecutionDecider decideWhetherToRunParallelSteps() {
        return (jobExecution, stepExecution) -> {
            // Retrieve the status from execution context
            String status = jobExecution.getExecutionContext().getString("myStatus");
            Object statusObj = jobExecution.getExecutionContext().get("myStatus");
            // If status is null, set it to a default value
            if (statusObj == null) {
                log.warn("The value for key='myStatus' in ExecutionContext is null. Setting to default value 'SKIP'.");
                status = "SKIP"; // In this case, I'm using SKIP as the default, but you can choose whatever makes sense for your application.
            } else {
                status = statusObj.toString();
            }

            if ("PROCEED".equals(status)) {
                return new FlowExecutionStatus("RUN_PARALLEL");
            } else {
                return new FlowExecutionStatus("DO_NOT_RUN_PARALLEL");
            }
        };
    }

    @Bean
    public Job fiabilizationjob(JobRepository jobRepository,
                                @Qualifier("entrepriseWorkerStep") Step entrepriseWorkerStep,
                                @Qualifier("parallelSteps") Flow parallelSteps,
                                @Qualifier("decideWhetherToRunParallelSteps") JobExecutionDecider decideWhetherToRunParallelSteps) {
        return new JobBuilder("fiabilizationJob", jobRepository)
                .start(entrepriseWorkerStep)
                .next(decideWhetherToRunParallelSteps)
                //.on(FlowExecutionStatus.COMPLETED.getName()).to(parallelSteps)
                .on("RUN_PARALLEL").to(parallelSteps)
                .from(decideWhetherToRunParallelSteps).on("DO_NOT_RUN_PARALLEL").end()
                .end()
                .build();
    }*/
}
