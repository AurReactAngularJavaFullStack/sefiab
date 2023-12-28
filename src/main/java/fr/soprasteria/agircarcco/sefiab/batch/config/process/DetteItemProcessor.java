package fr.soprasteria.agircarcco.sefiab.batch.config.process;

import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.service.DetteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DetteItemProcessor implements ItemProcessor<Dette, Dette> {

    private static final Logger log = LoggerFactory.getLogger(DetteItemProcessor.class);
    private final DetteService detteService;
    private final double DEBT_THRESHOLD = 2000.00;

    @Autowired
    public DetteItemProcessor(DetteService detteService) {
        this.detteService = detteService;
    }

    @Override
    public Dette process(Dette dette) throws Exception {
        if (dette.getAmount() > DEBT_THRESHOLD || dette.getAmount() < 0) {
            // Indicate that this entreprise needs to be contacted
//            return dette;
            log.warn("Enterprise with ID {} needs to be contacted. Amount: {}", dette.getEntreprise().getId(), dette.getAmount());
        }

        // Log or handle other specific cases
        if (dette.getAmount() == 0) {
            log.info("Debt has no amount.");
        } else {
            log.info("Debt has an amount below the threshold.");
        }

        // Sauvegardez la dette UNE SEULE fois (si nécessaire, sinon supprimez cette ligne)
        return detteService.saveDette(dette);
    }
}
