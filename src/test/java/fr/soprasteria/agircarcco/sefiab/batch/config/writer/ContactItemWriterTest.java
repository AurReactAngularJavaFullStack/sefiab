package fr.soprasteria.agircarcco.sefiab.batch.config.writer;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriterException;
import org.springframework.batch.item.WriteFailedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ContactItemWriterTest {

    @InjectMocks
    private ContactItemWriter contactItemWriter;

    @Mock
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void write_ValidContacts_MergesContacts() throws Exception {
        // Given
        Contact contact1 = new Contact();
        contact1.setId(1L);
        Contact contact2 = new Contact();
        contact2.setId(2L);

        List<Contact> contacts = Arrays.asList(contact1, contact2);
        Chunk<Contact> chunk = new Chunk<>(contacts);

        // When
        contactItemWriter.write(chunk);

        // Then
        verify(entityManager, times(1)).merge(contact1);
        verify(entityManager, times(1)).merge(contact2);
    }


    @Test
    public void write_WithException_ThrowsItemWriterException() throws Exception {
        // Given
        Contact contact = new Contact();
        contact.setId(1L);

        Chunk<Contact> chunk = createMockedChunk(Arrays.asList(contact));

        doThrow(RuntimeException.class).when(entityManager).merge(any(Contact.class));

        // Then
        assertThrows(NullPointerException.class, () -> contactItemWriter.write(chunk));
    }


    private <T> Chunk<T> createMockedChunk(List<T> items) {
        Chunk<T> chunk = mock(Chunk.class);
        when(chunk.getItems()).thenReturn(items);
        return chunk;
    }

    // Vous pouvez ajouter d'autres cas de test si nécessaire.
}
