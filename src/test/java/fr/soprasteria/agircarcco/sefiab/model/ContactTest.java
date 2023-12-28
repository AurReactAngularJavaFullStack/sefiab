package fr.soprasteria.agircarcco.sefiab.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ContactTest {

    private Contact contact;

    @BeforeEach
    public void setUp() {
        contact = new Contact();
    }

    @Test
    public void testGettersAndSetters() {
        contact.setId(1L);
        contact.setEmail("test@example.com");
        contact.setFirstName("John");
        contact.setLastName("Doe");
        contact.setName("John Doe");

        assertEquals(1L, contact.getId());
        assertEquals("test@example.com", contact.getEmail());
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("John Doe", contact.getName());

        // Ensure entreprise is initially null
        assertNull(contact.getEntreprise());

        // Test setting and getting entreprise
        Entreprise entreprise = new Entreprise();
        contact.setEntreprise(entreprise);
        assertEquals(entreprise, contact.getEntreprise());
    }

    @Test
    public void testToString() {
        contact.setId(1L);
        contact.setEmail("test@example.com");
        contact.setFirstName("John");
        contact.setLastName("Doe");
        contact.setName("John Doe");

        String expectedToString = "Contact{id=1, email='test@example.com', firstName='John', lastName='Doe', name='John Doe'}";
        assertEquals(expectedToString, contact.toString());
    }
}
