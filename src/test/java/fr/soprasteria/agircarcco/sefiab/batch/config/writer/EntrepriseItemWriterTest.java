package fr.soprasteria.agircarcco.sefiab.batch.config.writer;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.Chunk;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EntrepriseItemWriterTest {

    @InjectMocks
    private EntrepriseItemWriter writer;

    @Mock
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        entityManager = mock(EntityManager.class);
        writer = new EntrepriseItemWriter(entityManager);
    }

    private <T> Chunk<T> createMockedChunk(List<T> items) {
        Chunk<T> chunk = mock(Chunk.class);
        when(chunk.getItems()).thenReturn(items);
        return chunk;
    }

    @Test
    public void write_ValidEntreprise_MergesEntrepriseAndItsContactsAndDettes() throws Exception {
        // Given
        Entreprise entreprise = new Entreprise();

        Contact contact = new Contact();
        Dette dette = new Dette();

        entreprise.setContacts(Arrays.asList(contact));
        entreprise.setDettes(Arrays.asList(dette));

        Chunk<Entreprise> chunk = createMockedChunk(Arrays.asList(entreprise));

        // When
        writer.write(chunk);

        // Then
        verify(entityManager, times(1)).merge(entreprise);
        verify(entityManager, times(1)).merge(contact);
        verify(entityManager, times(1)).merge(dette);

        assertThat(contact.getEntreprise()).isEqualTo(entreprise);
        assertThat(dette.getEntreprise()).isEqualTo(entreprise);
    }

    @Test
    public void write_WithException_ThrowsException() throws Exception {
        // Given
        Entreprise entreprise = new Entreprise();

        Chunk<Entreprise> chunk = createMockedChunk(Arrays.asList(entreprise));

        doThrow(RuntimeException.class).when(entityManager).merge(any(Entreprise.class));

        // Then
        assertThrows(RuntimeException.class, () -> writer.write(chunk));
    }


    @Test
    public void testWrite() throws Exception {
        Entreprise entreprise = new Entreprise();
        // TODO: Set any required fields on the `entreprise` object

        Chunk<Entreprise> chunk = createMockedChunk(Arrays.asList(entreprise));

        writer.write(chunk);

        verify(entityManager, times(1)).merge(entreprise);
    }


}

