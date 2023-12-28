package fr.soprasteria.agircarcco.sefiab.batch.config.utils;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class ContactFieldSetMapper implements FieldSetMapper<Contact> {

    @Override
    public Contact mapFieldSet(FieldSet fieldSet) throws BindException {
        Contact contact = new Contact();
        contact.setId(fieldSet.readLong("contact_id"));
        contact.setName(fieldSet.readString("name"));
        contact.setFirstName(fieldSet.readString("first_name"));
        contact.setLastName(fieldSet.readString("last_name"));

        Long entrepriseId = fieldSet.readLong("entreprise_id");
        // TODO: Fetch or convert to Entreprise instance
        Entreprise entreprise = fetchEntrepriseById(entrepriseId);
        contact.setEntreprise(entreprise);

        return contact;
    }

    private Entreprise fetchEntrepriseById(Long id) {
        // Fetch the Entreprise by ID from your repository or service
        // For the sake of this example, I'm just creating a dummy instance
        Entreprise entreprise = new Entreprise();
        entreprise.setId(id);
        return entreprise;
    }
}
