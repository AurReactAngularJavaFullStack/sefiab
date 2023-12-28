package fr.soprasteria.agircarcco.sefiab.batch.config.reader;

import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.batch.item.file.FlatFileItemReader;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EntrepriseItemReaderTest {

    @Mock
    private ResourceLoader resourceLoader;

    @InjectMocks
    private EntrepriseItemReader entrepriseItemReader;

    @Mock
    private Resource resource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(resourceLoader.getResource("classpath:entreprise.csv")).thenReturn(resource);
    }

    @Test
    public void testEntrepriseReader() {
        FlatFileItemReader<Entreprise> reader = entrepriseItemReader.entrepriseReader();
        verify(resourceLoader).getResource("classpath:entreprise.csv");
    }

//
//    @Test
//    public void testLimitReachedInHandleSkippedLines() {
//        String line = "Sample line content";
//        for (int i = 0; i < 50; i++) {
//            entrepriseItemReader.handleSkippedLines(line);
//        }
//
//        // On the 51st line, an exception should be thrown.
//        assertThrows(RuntimeException.class, () -> entrepriseItemReader.handleSkippedLines(line));
//    }
}
