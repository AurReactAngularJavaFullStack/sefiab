package fr.soprasteria.agircarcco.sefiab.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import fr.soprasteria.agircarcco.sefiab.service.EmailService;
import fr.soprasteria.agircarcco.sefiab.service.DataCleanupService;
import fr.soprasteria.agircarcco.sefiab.service.ReportService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DebtProcessingJobExecutionListener implements JobExecutionListener {

    @Autowired
    private EmailService emailService;

    @Autowired
    private DataCleanupService dataCleanupService;

    @Autowired
    private ReportService reportService;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // Avant l'exécution de la tâche Batch
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getExitStatus().equals(ExitStatus.COMPLETED)) {
            emailService.sendSuccessNotification();
            dataCleanupService.performCleanup();
            List<String> reportData = generateReportData(); // Replace with your actual report data
            try {
                reportService.generateReport(reportData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            emailService.sendFailureAlert();
        }
    }

    private List<String> generateReportData() {
        List<String> reportData = new ArrayList<>();

        // Generate sample report data
        reportData.add("Report Line 1: Lorem ipsum dolor sit amet");
        reportData.add("Report Line 2: Consectetur adipiscing elit");
        reportData.add("Report Line 3: Sed do eiusmod tempor incididunt");
        reportData.add("Report Line 4: Ut labore et dolore magna aliqua");
        reportData.add("Report Line 5: Ut enim ad minim veniam");

        return reportData;
    }
}
