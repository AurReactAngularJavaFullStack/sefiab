package fr.soprasteria.agircarcco.sefiab.batch.config.process;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiagnosticItemProcessorTest {

    private DiagnosticItemProcessor diagnosticItemProcessor;

    @BeforeEach
    public void setUp() {
        diagnosticItemProcessor = new DiagnosticItemProcessor();
    }

    @Test
    public void testProcessValidContact() throws Exception {
        Contact contact = createSampleContact();

        Contact processedContact = diagnosticItemProcessor.process(contact);

        assertThat(processedContact).isEqualTo(contact);
    }

    private Contact createSampleContact() {
        Contact contact = new Contact();
        contact.setFirstName("John");
        contact.setLastName("Doe");
        contact.setEmail("john.doe@example.com");
        // Ajoutez d'autres propriétés si nécessaire
        return contact;
    }
}
