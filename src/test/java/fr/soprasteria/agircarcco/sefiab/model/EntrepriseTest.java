package fr.soprasteria.agircarcco.sefiab.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EntrepriseTest {

    private Entreprise entreprise;

    @BeforeEach
    public void setUp() {
        entreprise = new Entreprise();
    }

    @Test
    public void testGettersAndSetters() {
        entreprise.setId(1L);
        entreprise.setName("ABC Company");
        entreprise.setDebtAmount(5000.0);

        assertEquals(1L, entreprise.getId());
        assertEquals("ABC Company", entreprise.getName());
        assertEquals(5000.0, entreprise.getDebtAmount(), 0.001); // Use a delta for double comparison

        // Ensure dettes and contacts are initially empty lists
        assertNotNull(entreprise.getDettes());
        assertNotNull(entreprise.getContacts());

        // Test setting and getting dettes
        Dette dette1 = new Dette();
        dette1.setId(1L);
        dette1.setAmount(1000.0);
        Dette dette2 = new Dette();
        dette2.setId(2L);
        dette2.setAmount(2000.0);

        entreprise.setDettes(List.of(dette1, dette2));
        assertEquals(2, entreprise.getDettes().size());

        // Test setting and getting contacts
        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setEmail("contact1@example.com");
        Contact contact2 = new Contact();
        contact2.setId(2L);
        contact2.setEmail("contact2@example.com");

        entreprise.setContacts(List.of(contact1, contact2));
        assertEquals(2, entreprise.getContacts().size());
    }

    @Test
    public void testToString() {
        entreprise.setId(1L);
        entreprise.setName("ABC Company");

        String expectedToString = "Entreprise{id=1, name='ABC Company'}";
        assertEquals(expectedToString, entreprise.toString());
    }
}

