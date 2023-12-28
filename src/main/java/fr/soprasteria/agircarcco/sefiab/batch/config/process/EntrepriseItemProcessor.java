package fr.soprasteria.agircarcco.sefiab.batch.config.process;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.service.EntrepriseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntrepriseItemProcessor implements ItemProcessor<Entreprise, Entreprise> {

    private static final Logger log = LoggerFactory.getLogger(EntrepriseItemProcessor.class);
    private final EntrepriseService entrepriseService;

    @Autowired
    public EntrepriseItemProcessor(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @Override
    public Entreprise process(Entreprise entreprise) throws Exception {
        // Créez une nouvelle instance d'Entreprise pour éviter les problèmes de session Hibernate
        Entreprise newEntreprise = new Entreprise();
        newEntreprise.setName(entreprise.getName());
        newEntreprise.setDebtAmount(entreprise.getDebtAmount());

        // Associez les contacts et les dettes à l'entreprise
        newEntreprise.setContacts(entreprise.getContacts());
        newEntreprise.setDettes(entreprise.getDettes());

        for (Contact contact : entreprise.getContacts()) {
            contact.setEntreprise(newEntreprise);
        }

        for (Dette dette : entreprise.getDettes()) {
            dette.setEntreprise(newEntreprise);
        }

        // Sauvegardez l'entreprise UNE SEULE fois
        return entrepriseService.saveEntreprise(newEntreprise);
    }
}

