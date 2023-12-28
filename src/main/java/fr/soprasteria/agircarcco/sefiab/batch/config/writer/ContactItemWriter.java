package fr.soprasteria.agircarcco.sefiab.batch.config.writer;


import fr.soprasteria.agircarcco.sefiab.model.Contact;
import jakarta.persistence.EntityManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.WriteFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ContactItemWriter implements ItemWriter<Contact> {

    private final EntityManager entityManager;

    @Autowired
    public ContactItemWriter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public void write(Chunk<? extends Contact> chunk) throws Exception {
        if(chunk == null) {
            System.out.println("Chunk is null!");
        }

        for (Contact contact : chunk) {
            if(contact == null) {
                System.out.println("Contact is null!");
            }
            try {
                entityManager.merge(contact);
            } catch (Exception e) {
                throw new WriteFailedException("Error writing contact", e);
            }
        }
    }


}

