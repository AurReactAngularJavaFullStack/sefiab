package fr.soprasteria.agircarcco.sefiab.batch.config.process;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import fr.soprasteria.agircarcco.sefiab.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class ContactItemProcessorTest {

    @InjectMocks
    private ContactItemProcessor contactItemProcessor;

    @Mock
    private ContactService contactService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcessValidContact() throws Exception {
        Contact contact = new Contact();
        contact.setEmail("test@example.com");
        contact.setFirstName("john");
        contact.setLastName("doe");

        when(contactService.saveContact(any(Contact.class))).thenReturn(contact);

        Contact processedContact = contactItemProcessor.process(contact);

        assertThat(processedContact.getFirstName()).isEqualTo("John");
        assertThat(processedContact.getLastName()).isEqualTo("Doe");
    }

    @Test
    public void testProcessInvalidEmail() throws Exception {
        Contact contact = new Contact();
        contact.setEmail("invalid-email");

        Contact processedContact = contactItemProcessor.process(contact);

        assertThat(processedContact).isNull();
    }

    @Test
    public void testProcessNullContact() throws Exception {
        Contact processedContact = contactItemProcessor.process(null);
        assertThat(processedContact).isNull();
    }

    @Test
    public void testIsValidEmail() throws Exception {
        boolean result = contactItemProcessor.isValidEmail("test@example.com");
        assertThat(result).isTrue();

        result = contactItemProcessor.isValidEmail("invalid-email");
        assertThat(result).isFalse();
    }

    @Test
    public void testCapitalizeFirstLetter() throws Exception {
        String result = contactItemProcessor.capitalizeFirstLetter("test");
        assertThat(result).isEqualTo("Test");
    }
}
