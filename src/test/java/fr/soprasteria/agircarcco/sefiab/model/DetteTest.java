package fr.soprasteria.agircarcco.sefiab.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DetteTest {

    private Dette dette;

    @BeforeEach
    public void setUp() {
        dette = new Dette();
    }

    @Test
    public void testGettersAndSetters() {
        dette.setId(1L);
        dette.setAmount(1000.0);

        assertEquals(1L, dette.getId());
        assertEquals(1000.0, dette.getAmount(), 0.001); // Use a delta for double comparison

        // Ensure entreprise is initially null
        assertNull(dette.getEntreprise());

        // Test setting and getting entreprise
        Entreprise entreprise = new Entreprise();
        dette.setEntreprise(entreprise);
        assertEquals(entreprise, dette.getEntreprise());
    }

    @Test
    public void testToString() {
        dette.setId(1L);
        dette.setAmount(1000.0);

        String expectedToString = "Dette{id=1, amount=1000.0}";
        assertEquals(expectedToString, dette.toString());
    }
}

