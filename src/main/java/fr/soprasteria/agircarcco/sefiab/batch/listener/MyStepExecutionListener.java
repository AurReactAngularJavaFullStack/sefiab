package fr.soprasteria.agircarcco.sefiab.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

public class MyStepExecutionListener extends StepExecutionListenerSupport {
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        // If the step has any write count, we set the status as "PROCEED", else "SKIP".
        String status = stepExecution.getWriteCount() > 0 ? "PROCEED" : "SKIP";
        stepExecution.getExecutionContext().put("myStatus", status);
        return super.afterStep(stepExecution);
    }
}


