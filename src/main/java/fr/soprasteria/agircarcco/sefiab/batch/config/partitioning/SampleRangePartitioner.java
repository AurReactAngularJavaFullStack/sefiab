package fr.soprasteria.agircarcco.sefiab.batch.config.partitioning;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class SampleRangePartitioner implements Partitioner {

    public static final String RANGE_PREFIX = "partition";
    private static final int MAX_GRID_SIZE = 100;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        if (gridSize < 0) {
            throw new IllegalArgumentException("Grid size cannot be negative");
        }

        if (gridSize > MAX_GRID_SIZE) {
            throw new IllegalArgumentException("Grid size cannot exceed " + MAX_GRID_SIZE);
        }

        // Add this line to ensure gridSize doesn't exceed 100
        gridSize = Math.min(gridSize, 100);

        if (gridSize == 0) {
            return new HashMap<>(); // Return empty map if gridSize is zero
        }

        Map<String, ExecutionContext> result = new HashMap<>();
        int totalItems = 100;  // Assuming 100 items
        int range = totalItems / gridSize;

        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = new ExecutionContext();

            // Adjust for the last partition if gridSize doesn't evenly divide totalItems
            if (i == gridSize - 1) {
                context.putInt("minValue", i * range);
                context.putInt("maxValue", totalItems - 1); // End at the last item
            } else {
                context.putInt("minValue", i * range);
                context.putInt("maxValue", (i + 1) * range - 1);
            }

            result.put(RANGE_PREFIX + i, context);
        }

        return result;
    }
}

