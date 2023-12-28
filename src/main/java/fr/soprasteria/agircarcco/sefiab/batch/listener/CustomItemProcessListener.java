package fr.soprasteria.agircarcco.sefiab.batch.listener;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

@Component
public class CustomItemProcessListener<I, O> implements ItemProcessListener<I, O> {
    // ... implement the methods for the processor
    @Override
    public void beforeProcess(I item) {
        // Logic before processing an item
        System.out.println("Before processing: " + item);
    }

    @Override
    public void afterProcess(I item, O result) {
        // Logic after processing an item
        System.out.println("Processed item: " + item + " to result: " + result);
    }

    @Override
    public void onProcessError(I item, Exception e) {
        // Handling errors during processing
        System.err.println("Error processing item: " + item);
        e.printStackTrace();  // Consider logging the exception instead of printing
    }
}

