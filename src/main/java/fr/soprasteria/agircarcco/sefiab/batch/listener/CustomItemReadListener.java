package fr.soprasteria.agircarcco.sefiab.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
public class CustomItemReadListener<Entreprise> implements ItemReadListener<Entreprise> {

    private final Logger log = LoggerFactory.getLogger(CustomItemReadListener.class);

    @Override
    public void beforeRead() {
        // Log before reading an item. This can be useful for debugging or performance tracking.
        log.debug("About to read an item.");
    }

    @Override
    public void afterRead(Entreprise item) {
        // Log after reading an item. This is where you'd probably add logic to handle the read data.
        log.debug("Successfully read an item: {}", item);
    }

    @Override
    public void onReadError(Exception ex) {
        // Log and handle read errors. In a real-world scenario, this could involve alerting administrators,
        // marking the specific item as failed, or retrying the read operation.
        log.error("Error while reading item.", ex);
    }
}





