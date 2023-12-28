package fr.soprasteria.agircarcco.sefiab.batch.config.partitioning;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleRangePartitionerTest {

    private SampleRangePartitioner partitioner;

    @BeforeEach
    public void setUp() {
        partitioner = new SampleRangePartitioner();
    }

    @Test
    public void testPartitionWithValidGridSize() {
        int gridSize = 4; // Example grid size
        Map<String, ExecutionContext> partitions = partitioner.partition(gridSize);

        assertThat(partitions).isNotNull();
        assertThat(partitions.size()).isEqualTo(gridSize);

        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = partitions.get(SampleRangePartitioner.RANGE_PREFIX + i);
            assertThat(context).isNotNull();

            int expectedMinValue = i * (100 / gridSize);
            int expectedMaxValue = (i + 1) * (100 / gridSize) - 1;

            assertThat(context.getInt("minValue")).isEqualTo(expectedMinValue);
            assertThat(context.getInt("maxValue")).isEqualTo(expectedMaxValue);
        }
    }

    @Test
    public void testPartitionWithGridSizeOne() {
        int gridSize = 1;
        Map<String, ExecutionContext> partitions = partitioner.partition(gridSize);

        assertThat(partitions).isNotNull();
        assertThat(partitions.size()).isEqualTo(gridSize);

        ExecutionContext context = partitions.get(SampleRangePartitioner.RANGE_PREFIX + 0);
        assertThat(context.getInt("minValue")).isEqualTo(0);
        assertThat(context.getInt("maxValue")).isEqualTo(99); // Assuming 100 items
    }

    // Vous pouvez ajouter d'autres tests pour couvrir différents cas :
    // - gridSize négatif (si cela a du sens pour votre logique métier)
    // - gridSize supérieur à 100 (en supposant 100 éléments)
    // - etc.
    @Test
    public void testPartitionWithNegativeGridSize() {
        int gridSize = -1;
        assertThrows(IllegalArgumentException.class, () -> partitioner.partition(gridSize));
    }


    @Test
    public void testPartitionWithGridSizeGreaterThan100() {
        int gridSize = 101; // Un de plus que le nombre d'éléments supposé

        // Expect an exception when gridSize exceeds 100
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> partitioner.partition(gridSize))
                .withMessage("Grid size cannot exceed 100");
    }
    @Test
    public void testPartitionWithGridSizeZero() {
        int gridSize = 0;

        Map<String, ExecutionContext> partitions = partitioner.partition(gridSize);

        // En supposant que cela devrait renvoyer une partition vide
        assertThat(partitions).isNotNull();
        assertThat(partitions).isEmpty();
    }


    @Test
    public void testIntervalsDoNotOverlap() {
        int gridSize = 5; // juste un exemple, mais cela pourrait être n'importe quelle valeur valide

        Map<String, ExecutionContext> partitions = partitioner.partition(gridSize);

        int previousMax = -1;
        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = partitions.get(SampleRangePartitioner.RANGE_PREFIX + i);
            int currentMin = context.getInt("minValue");
            int currentMax = context.getInt("maxValue");

            assertThat(currentMin).isGreaterThan(previousMax);
            previousMax = currentMax;
        }
    }

}
