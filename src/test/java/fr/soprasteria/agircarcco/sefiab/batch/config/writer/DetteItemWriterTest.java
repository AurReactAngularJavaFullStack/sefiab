package fr.soprasteria.agircarcco.sefiab.batch.config.writer;

import fr.soprasteria.agircarcco.sefiab.model.Dette;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DetteItemWriterTest {

    private DetteItemWriter detteItemWriter;
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        entityManager = mock(EntityManager.class);
        detteItemWriter = new DetteItemWriter(entityManager);
    }

    @Test
    public void write_ValidDette_MergesDette() throws Exception {
        // Given
        Dette dette1 = new Dette();
        dette1.setId(1L);
        Dette dette2 = new Dette();
        dette2.setId(2L);

        Chunk<Dette> chunk = createMockedChunk(Arrays.asList(dette1, dette2));

        // When
        detteItemWriter.write(chunk);

        // Then
        verify(entityManager, times(1)).merge(dette1);
        verify(entityManager, times(1)).merge(dette2);
    }

    @Test
    public void write_WithException_ThrowsItemWriterException() throws Exception {
        // Given
        Dette dette = new Dette();
        dette.setId(1L);

        Chunk<Dette> chunk = createMockedChunk(Arrays.asList(dette));

        doThrow(RuntimeException.class).when(entityManager).merge(any(Dette.class));

        // Then
        assertThrows(RuntimeException.class, () -> detteItemWriter.write(chunk));
    }

    private <T> Chunk<T> createMockedChunk(List<T> items) {
        Chunk<T> chunk = mock(Chunk.class);
        when(chunk.getItems()).thenReturn(items);
        return chunk;
    }
}

