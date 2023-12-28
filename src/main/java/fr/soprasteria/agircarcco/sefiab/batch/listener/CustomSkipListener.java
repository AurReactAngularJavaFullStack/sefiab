package fr.soprasteria.agircarcco.sefiab.batch.listener;

import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

@Component
public class CustomSkipListener implements SkipListener<Entreprise, Entreprise> {
    private final Logger log = LoggerFactory.getLogger(CustomSkipListener.class);

    @Override
    public void onSkipInRead(Throwable throwable) {
        // Log or handle skipped item during reading
        log.error("Item skipped during reading: " + throwable.getMessage());
    }

    @Override
    public void onSkipInWrite(Entreprise entreprise, Throwable throwable) {
        // Log or handle skipped item during writing
        log.error("Item skipped during writing: " + throwable.getMessage() + " - Enterprise: " + entreprise.getName());
    }

    @Override
    public void onSkipInProcess(Entreprise entreprise, Throwable throwable) {
        // Log or handle skipped item during processing
        log.error("Item skipped during processing: " + throwable.getMessage() + " - Enterprise: " + entreprise.getName());
    }
}

