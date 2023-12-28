package fr.soprasteria.agircarcco.sefiab.batch.listener;

import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.repository.CustomItemWriteListenerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomItemWriteListener implements
        CustomItemWriteListenerInterface {

    private static Logger log = LoggerFactory.getLogger(CustomItemWriteListener.class);

    public void setLogger(Logger log) {
        this.log = log;
    }

    public void beforeWrite(List<? extends Entreprise> items) {
        log.info("About to write {} items.", items.size());
        // Vous pouvez ajouter ici toute autre logique avant que les items ne soient écrits.
    }


    public void afterWrite(List<? extends Entreprise> items) {
        log.info("{} items have been written successfully.", items.size());
        // Vous pouvez ajouter ici toute autre logique après que les items ont été écrits.
    }


    public void onWriteError(Exception exception, List<? extends Entreprise> items) {
        log.error("Error occurred while writing items. Error: {}", exception.getMessage(), exception);
        // Vous pouvez ajouter ici toute autre logique en cas d'erreur d'écriture.
    }
}

