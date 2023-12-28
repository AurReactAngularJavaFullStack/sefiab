package fr.soprasteria.agircarcco.sefiab.batch.config.process;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import fr.soprasteria.agircarcco.sefiab.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ContactItemProcessor implements ItemProcessor<Contact, Contact> {

    private static final Logger log = LoggerFactory.getLogger(ContactItemProcessor.class);
    private final ContactService contactService;

    @Autowired
    public ContactItemProcessor(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public Contact process(Contact contact) throws Exception {
        if (contact == null) {
            log.warn("Null contact detected, skipping...");
            return null;
        }

        // Valider l'email du contact
        if (contact.getEmail() != null && !isValidEmail(contact.getEmail())) {
            log.warn("Invalid email for contact: " + contact.getEmail());
            // Vous pouvez choisir de renvoyer null ou de corriger l'e-mail selon vos besoins.
            return null;
        }

        // Mettre à jour le prénom pour qu'il commence par une majuscule
        if (contact.getFirstName() != null) {
            contact.setFirstName(capitalizeFirstLetter(contact.getFirstName()));
        }

        // Mettre à jour le nom de famille pour qu'il commence par une majuscule
        if (contact.getLastName() != null) {
            contact.setLastName(capitalizeFirstLetter(contact.getLastName()));
        }

        // Logique supplémentaire au besoin

        // Sauvegardez le contact UNE SEULE fois
        return contactService.saveContact(contact);
    }

    boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}

