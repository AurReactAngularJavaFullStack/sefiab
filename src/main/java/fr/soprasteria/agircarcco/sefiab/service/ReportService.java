package fr.soprasteria.agircarcco.sefiab.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class ReportService {

    private String reportFilePath = "C:/Users/ahamzaoui/Documents/AGIRC_ARCCO/Spring/Spring_Batch/RemotePartitioningMasterWorker/sefiab/src/main/resources/entreprise.csv";  // valeur par défaut

    public void setReportFilePath(String reportFilePath) {
        this.reportFilePath = reportFilePath;
    }

    public void generateReport(List<String> data) throws IOException {
        try (FileWriter writer = new FileWriter(reportFilePath)) {
            // Écrivez l'en-tête du rapport (si nécessaire)
            writer.append("Column1,Column2,Column3\n");

            // Écrivez les données dans le rapport
            for (String rowData : data) {
                writer.append(rowData);
                writer.append("\n");
            }

            writer.flush();
        } catch (IOException e) {
            // Gérez les exceptions liées à l'écriture du fichier
            throw e;
        }
    }
}

