package fr.soprasteria.agircarcco.sefiab.batch.config.reader;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.repository.EntrepriseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.core.io.ClassPathResource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class DetteItemReaderTest {

    @Mock
    private EntrepriseRepository entrepriseRepository;

    private DetteItemReader detteItemReader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes the mock objects
        detteItemReader = new DetteItemReader(entrepriseRepository);
        when(entrepriseRepository.findById(1L)).thenReturn(Optional.of(new Entreprise(1L, "Entreprise A")));
        when(entrepriseRepository.findById(2L)).thenReturn(Optional.of(new Entreprise(2L, "Entreprise B")));
        when(entrepriseRepository.findById(3L)).thenReturn(Optional.of(new Entreprise(3L, "Entreprise C")));
    }

    @Test
    public void testDetteReader() throws Exception {
        FlatFileItemReader<Dette> reader = detteItemReader.detteReader();
        reader.setResource(new ClassPathResource("test_dette.csv"));

        reader.open(new ExecutionContext());

        // Verifying the first item...
        Dette firstItem = reader.read();
        assertThat(firstItem.getId()).isEqualTo(1L);
        assertThat(firstItem.getAmount()).isEqualByComparingTo(1000.50);
        assertThat(firstItem.getEntreprise().getId()).isEqualTo(1L);

        // Verifying the second item...
        Dette secondItem = reader.read();
        assertThat(secondItem.getId()).isEqualTo(2L);
        assertThat(secondItem.getAmount()).isEqualByComparingTo(2500.75);
        assertThat(secondItem.getEntreprise().getId()).isEqualTo(2L);

        // Verifying the third item...
        Dette thirdItem = reader.read();
        assertThat(thirdItem.getId()).isEqualTo(3L);
        assertThat(thirdItem.getAmount()).isEqualTo(500.25);
        assertThat(thirdItem.getEntreprise().getId()).isEqualTo(3L);


        // Assert that there are no more items
        assertThat(reader.read()).isNull();

        reader.close();
    }

    @Test
    public void testHandlingOfMalformedLines() throws Exception {
        FlatFileItemReader<Dette> reader = detteItemReader.detteReader();
        reader.setResource(new ClassPathResource("malformed_dette.csv"));
        reader.open(new ExecutionContext());

        reader.read();

        assertThatThrownBy(() -> {
            reader.read();
        }).isInstanceOf(FlatFileParseException.class);

        reader.close();
    }



    @Test
    public void testMissingCSVFile() {
        FlatFileItemReader<Dette> reader = detteItemReader.detteReader();
        reader.setResource(new ClassPathResource("missing_dette.csv"));

        assertThatThrownBy(() -> {
            reader.open(new ExecutionContext());
        }).isInstanceOf(ItemStreamException.class);

        reader.close();
    }

}

