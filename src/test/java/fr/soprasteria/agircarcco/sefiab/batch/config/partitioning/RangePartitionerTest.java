package fr.soprasteria.agircarcco.sefiab.batch.config.partitioning;/*package fr.soprasteria.agircarcco.sefiab.batch.config.partitioning;

import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RangePartitionerTest {

    private FlatFileItemReader<Entreprise> reader;
    private RangePartitioner partitioner;

    @BeforeEach
    public void setUp() {
        reader = mock(FlatFileItemReader.class);
        partitioner = new RangePartitioner(reader);
    }

    @Test
    public void testPartitioning() throws Exception {
        when(reader.read()).thenReturn(new Entreprise(), new Entreprise(), null);

        Map<String, ExecutionContext> partitions = partitioner.partition(2);

        assertThat(partitions).hasSize(2);
        assertThat(partitions.get("partition1").getInt("startRow")).isEqualTo(1);
        assertThat(partitions.get("partition1").getInt("endRow")).isEqualTo(1);
        assertThat(partitions.get("partition2").getInt("startRow")).isEqualTo(2);
        assertThat(partitions.get("partition2").getInt("endRow")).isEqualTo(2);

        verify(reader, times(3)).read();
    }

    @Test
    public void testRowCountErrorHandling() throws Exception {
        when(reader.read()).thenThrow(new RuntimeException("Read error"));

        // Ici, vous pourriez décider de gérer différemment l'erreur, peut-être attendez-vous une exception spécifique.
        assertThatThrownBy(() -> partitioner.partition(2))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Failed to count rows in CSV file.")
                .hasCause(new RuntimeException("Read error"));

        verify(reader, times(1)).read();
    }

    // Vous pouvez ajouter d'autres cas de test pour couvrir des scénarios plus spécifiques.
}*/
