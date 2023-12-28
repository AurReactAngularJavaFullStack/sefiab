package fr.soprasteria.agircarcco.sefiab.batch.config.process;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import org.springframework.batch.item.ItemProcessor;

public class DiagnosticItemProcessor implements ItemProcessor<Contact, Contact> {

    @Override
    public Contact process(Contact item) throws Exception {
        System.out.println(item); // or use a logger
        return item;
    }
}

