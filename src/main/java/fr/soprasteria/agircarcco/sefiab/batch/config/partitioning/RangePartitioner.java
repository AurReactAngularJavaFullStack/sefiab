//package fr.soprasteria.agircarcco.sefiab.batch.config.partitioning;
//
//import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
//import org.springframework.batch.core.partition.support.Partitioner;
//import org.springframework.batch.item.ExecutionContext;
//import org.springframework.batch.item.file.FlatFileItemReader;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//
//
//
//public class RangePartitioner implements Partitioner {
//
//    private FlatFileItemReader<Entreprise> reader;
//
//    public RangePartitioner(FlatFileItemReader<Entreprise> reader) {
//        this.reader = reader;
//    }
//
//    @Override
//    public Map<String, ExecutionContext> partition(int gridSize) {
//        int min = 1; // First row after header
//        int max = getRowCount() - 1; // Subtract header row
//
//        int range = (max - min) / gridSize + 1;
//
//        Map<String, ExecutionContext> result = new HashMap<>();
//
//        int start = min;
//        int end = start + range - 1;
//
//        for (int i = 1; i <= gridSize; i++) {
//            ExecutionContext value = new ExecutionContext();
//
//            if (i == gridSize) {
//                // for the last partition, ensure it includes any potential remaining records
//                end = max;
//            }
//
//            value.putInt("startRow", start);
//            value.putInt("endRow", end);
//            result.put("partition" + i, value);
//
//            start += range;
//            end += range;
//        }
//
//        return result;
//    }
//
//    private int getRowCount() {
//        int count = 0;
//        try {
//            reader.open(new ExecutionContext());
//            while (reader.read() != null) {
//                count++;
//            }
//            reader.close();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to count rows in CSV file.", e);
//        }
//        return count;
//    }
//}
