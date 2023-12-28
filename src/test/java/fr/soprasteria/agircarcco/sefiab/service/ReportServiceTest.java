package fr.soprasteria.agircarcco.sefiab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReportServiceTest {

    private ReportService reportService;

    // Répertoire temporaire géré par JUnit
    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        reportService = new ReportService();
    }

    @Test
    public void testGenerateReport() throws IOException {
        List<String> data = Arrays.asList("Data1,Data2,Data3", "Data4,Data5,Data6");

        // Modifiez le chemin de destination du rapport pour pointer vers le répertoire temporaire
        Path reportFilePath = tempDir.resolve("entreprise.csv");
        reportService.setReportFilePath(reportFilePath.toString());

        reportService.generateReport(data);

        assertTrue(Files.exists(reportFilePath));

        List<String> lines = Files.readAllLines(reportFilePath);
        assertEquals(3, lines.size());
        assertEquals("Column1,Column2,Column3", lines.get(0));
        assertEquals("Data1,Data2,Data3", lines.get(1));
        assertEquals("Data4,Data5,Data6", lines.get(2));
    }

    @Test
    public void testGenerateReportWithEmptyData() throws IOException {
        List<String> data = Arrays.asList();

        Path reportFilePath = tempDir.resolve("emptyReport.csv");
        reportService.setReportFilePath(reportFilePath.toString());

        reportService.generateReport(data);

        assertTrue(Files.exists(reportFilePath));

        List<String> lines = Files.readAllLines(reportFilePath);
        assertEquals(1, lines.size()); // Only header
        assertEquals("Column1,Column2,Column3", lines.get(0));
    }

    @Test
    public void testGenerateReportWithIOException() {
        List<String> data = Arrays.asList("Data1,Data2,Data3");

        // Définir un chemin invalide pour provoquer une IOException
        reportService.setReportFilePath("Z:/invalidPath/entreprises.csv");

        assertThrows(IOException.class, () -> reportService.generateReport(data));
    }
}

