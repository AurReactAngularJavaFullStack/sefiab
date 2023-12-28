package fr.soprasteria.agircarcco.sefiab.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class CustomStepExecutionListener implements StepExecutionListener {
    // Override beforeStep, afterStep, etc.
    @Override
    public void beforeStep(StepExecution stepExecution) {
        // This is executed before the step is started
        System.out.println("Step Execution started: " + stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        // This is executed after the step is completed
        System.out.println("Step Execution completed: " + stepExecution.getStepName());
        return stepExecution.getExitStatus();
    }

    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new CustomStepExecutionListener();
    }
}
