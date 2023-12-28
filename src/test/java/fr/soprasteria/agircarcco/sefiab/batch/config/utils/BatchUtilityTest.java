package fr.soprasteria.agircarcco.sefiab.batch.config.utils;

import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.validation.Validator;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class BatchUtilityTest {

    @InjectMocks
    private BatchUtility batchUtility;

    @Mock
    private Validator validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createDelegateTest() throws IOException {
        String filename = "entreprise_test.csv";
        Class<Entreprise> type = Entreprise.class;
        String[] columnNames = {"id", "name", "debtAmount"};

        FlatFileItemReader<Entreprise> reader = batchUtility.createDelegate(filename, type, columnNames);

        assertThat(reader).isNotNull();
        assertThat(reader.getName()).isEqualTo(filename + "ItemReader");
    }

    @Test
    public void createDelegateTestWithValidator() throws IOException {
        String filename = "entreprise_test.csv";
        Class<Entreprise> type = Entreprise.class;
        String[] columnNames = {"id", "name", "debtAmount"};

        // Mock the validator
        Validator validator = mock(Validator.class);

        FlatFileItemReader<Entreprise> reader = batchUtility.createDelegate(filename, type, columnNames);

        assertThat(reader).isNotNull();
        assertThat(reader.getName()).isEqualTo(filename + "ItemReader");

        // Use reflection or other means to check resource, linesToSkip, etc.
    }

    @Test
    public void createDelegateWithDifferentFileFormatTest() throws IOException {
        String filename = "entreprise_test.txt";
        Class<Entreprise> type = Entreprise.class;
        String[] columnNames = {"id", "name"};

        FlatFileItemReader<Entreprise> reader = batchUtility.createDelegate(filename, type, columnNames);

        assertThat(reader).isNotNull();
        assertThat(reader.getName()).isEqualTo(filename + "ItemReader");

        // Use reflection or other means to check resource, delimiter, linesToSkip, etc.
    }

    @Test
    public void createDelegateWithCustomColumnMappingTest() throws IOException {
        String filename = "custom_format.csv";
        Class<Entreprise> type = Entreprise.class;
        String[] columnNames = {"custom_id", "custom_name"};

        FlatFileItemReader<Entreprise> reader = batchUtility.createDelegate(filename, type, columnNames);

        assertThat(reader).isNotNull();
        assertThat(reader.getName()).isEqualTo(filename + "ItemReader");

        // Use reflection or other means to check resource, linesToSkip, etc.
    }
}
