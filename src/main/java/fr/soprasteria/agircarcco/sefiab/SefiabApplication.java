package fr.soprasteria.agircarcco.sefiab;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "fr.soprasteria.agircarcco.sefiab.model")
@EnableJpaRepositories(basePackages = "fr.soprasteria.agircarcco.sefiab.repository")
public class SefiabApplication {

	private static final Logger logger = LoggerFactory.getLogger(SefiabApplication.class);

	private JobLauncher jobLauncher;

	private Job fiabilizationJob;

	public SefiabApplication(JobLauncher jobLauncher, Job fiabilizationJob) {
		this.jobLauncher = jobLauncher;
		this.fiabilizationJob = fiabilizationJob;
	}

//	Instead of executing the batch job immediately after the application context is created (i.e.,
//	SpringApplication.run(SefiabApplication.class, args).getBean(SefiabApplication.class).runBatchJob();),
//	you might consider using the @PostConstruct annotation, which will run after the Spring context is fully initialized and the bean is
//	created:
	@PostConstruct
	public void init() {
		runBatchJob();
	}

	public static void main(String[] args) {
		SpringApplication.run(SefiabApplication.class, args).getBean(SefiabApplication.class).runBatchJob();
	}

	public void runBatchJob() {
		try {
			JobParameters jobParameters = new JobParametersBuilder()
					.addLong("time", System.currentTimeMillis())
					.toJobParameters();

			JobExecution jobExecution = jobLauncher.run(fiabilizationJob, jobParameters);
			if (jobExecution != null) {
				logger.info("Job Status: {}", jobExecution.getStatus());
			} else {
				logger.warn("Job Execution returned null.");
			}

		} catch (JobExecutionAlreadyRunningException e) {
			logger.error("Job execution already running.", e);
		} catch (JobRestartException e) {
			logger.error("Job restart failed.", e);
		} catch (JobInstanceAlreadyCompleteException e) {
			logger.error("Job instance already completed.", e);
		} catch (JobExecutionException e) {
			logger.error("Job execution failed.", e);
		}
	}

}
