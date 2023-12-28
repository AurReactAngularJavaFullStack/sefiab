package fr.soprasteria.agircarcco.sefiab.service;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import fr.soprasteria.agircarcco.sefiab.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;


    private ContactService contactService;

    @BeforeEach
    public void setUp() {
        contactService = new ContactService(contactRepository);
    }

    @Test
    public void testSaveContact() {
        // Create a Contact object to save
        Contact contactToSave = new Contact();
        contactToSave.setEmail("test@example.com");
        contactToSave.setFirstName("John");
        contactToSave.setLastName("Doe");
        contactToSave.setName("John Doe");

        // Configure the mock behavior for ContactRepository
        when(contactRepository.save(contactToSave)).thenReturn(contactToSave);

        // Call the method to test
        Contact savedContact = contactService.saveContact(contactToSave);

        // Verify that the save method of the repository was called with the right object
        verify(contactRepository).save(contactToSave);

        // Check that the object returned by the method is the same as the one saved
        assertEquals(contactToSave, savedContact);
    }

    @Test
    public void testFindByEmail() {
        Contact contact = new Contact();
        contact.setEmail("test@test.com");
        when(contactRepository.findByEmail("test@test.com")).thenReturn(Optional.of(contact));
        assertEquals(contact, contactService.findByEmail("test@test.com"));
    }

    // Integration Test - In a real-world scenario, this should be separate and use actual database setup
    @Test
    public void testIntegrationFindByEmail() {
        // This test currently just calls the service method, which isn't a real "integration" test
        // as it doesn't test integration with actual database or other components.
        // So, for now, ensure the repository returns some mock data
        Contact mockContact = new Contact();
        mockContact.setEmail("test@test.com");
        when(contactRepository.findByEmail("test@test.com")).thenReturn(Optional.of(mockContact));

        Contact contact = contactService.findByEmail("test@test.com");
        assertNotNull(contact);
        assertEquals("test@test.com", contact.getEmail());
    }

    // Regression Test
    @Test
    public void testNonRegressionFindByEmail() {
        Contact contact = new Contact();
        contact.setEmail("test@test.com");
        when(contactRepository.findByEmail("test@test.com")).thenReturn(Optional.of(contact));
        assertEquals(contact, contactService.findByEmail("test@test.com"));
    }
}



