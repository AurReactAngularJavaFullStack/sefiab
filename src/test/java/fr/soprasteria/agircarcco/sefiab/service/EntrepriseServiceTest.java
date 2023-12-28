package fr.soprasteria.agircarcco.sefiab.service;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.repository.EntrepriseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EntrepriseServiceTest {

    @Autowired
    private EntrepriseService entrepriseService;

    @MockBean
    private EntrepriseRepository entrepriseRepository;

    @MockBean
    private DetteService detteService;

    @MockBean
    private ContactService contactService;

    @BeforeEach
    public void setup() {
        entrepriseService = new EntrepriseService(entrepriseRepository, detteService, contactService);
    }

    @Test
    public void testSaveEntreprise() {
        Entreprise mockEntreprise = new Entreprise();
        mockEntreprise.setDettes(List.of(new Dette()));
        mockEntreprise.setContacts(List.of(new Contact()));

        Entreprise savedMockEntreprise = new Entreprise();

        when(entrepriseRepository.save(any(Entreprise.class))).thenReturn(savedMockEntreprise);

        Entreprise result = entrepriseService.saveEntreprise(mockEntreprise);

        verify(entrepriseRepository).save(mockEntreprise);
        mockEntreprise.getDettes().forEach(dette -> verify(detteService).saveDette(dette));
        mockEntreprise.getContacts().forEach(contact -> verify(contactService).saveContact(contact));

        assertEquals(savedMockEntreprise, result);
    }

    // Test Unitaire
    @Test
    public void testFindByName() {
        Entreprise entreprise = new Entreprise();
        entreprise.setName("Test");
        Mockito.when(entrepriseRepository.findByName("Test")).thenReturn(Optional.of(entreprise));
        assertEquals(entreprise, entrepriseService.findByName("Test"));
    }

//    @Test
//    @Transactional  // Use @Transactional to ensure changes to the database are rolled back after the test
//    public void testIntegrationFindByName() {
//        // Step 1: Create and persist the entreprise
//
//        // Create an entreprise with the name "Test"
//        Entreprise testEntreprise = new Entreprise();
//        testEntreprise.setName("Test");
//
//        // Set dettes
//        List<Dette> dettes = new ArrayList<>();
//        dettes.add(new Dette());
//        // ... add more dettes if needed
//        testEntreprise.setDettes(dettes);
//
//        // Set contacts
//        List<Contact> contacts = new ArrayList<>();
//        contacts.add(new Contact());
//        // ... add more contacts if needed
//        testEntreprise.setContacts(contacts);
//
//        // Set debtAmount
//        testEntreprise.setDebtAmount(1000.50);
//
//        // Save the entreprise to the database
//        Entreprise savedEntreprise = entrepriseService.saveEntreprise(testEntreprise);
//
//        // Ensure the saved entreprise is not null
//        assertNotNull(savedEntreprise);
//        assertNotNull(savedEntreprise.getId());
//
//        // Step 2: Fetch the entreprise by name
//        Entreprise fetchedEntreprise = entrepriseService.findByName("Test");
//
//        // Step 3: Assert its existence and properties
//        assertNotNull(fetchedEntreprise);
//        assertEquals("Test", fetchedEntreprise.getName());
//        assertEquals(1000.50, fetchedEntreprise.getDebtAmount(), 0.001);  // Including a small delta for double comparison
//
//        // Step 4: Use existsByName to double-check existence
//        assertTrue(entrepriseService.nameExists("Test"), "The entreprise with the name 'Test' should exist in the database.");
//    }




    // Test de non-régression
    @Test
    public void testNonRegressionFindByName() {
        Entreprise entreprise = new Entreprise();
        entreprise.setName("Test");
        Mockito.when(entrepriseRepository.findByName("Test")).thenReturn(Optional.of(entreprise));
        assertEquals(entreprise, entrepriseService.findByName("Test"));
    }

    @Test
    public void testSaveEntrepriseWithoutDettesAndContacts() {
        Entreprise mockEntreprise = new Entreprise();

        Entreprise savedMockEntreprise = new Entreprise();

        when(entrepriseRepository.save(any(Entreprise.class))).thenReturn(savedMockEntreprise);

        Entreprise result = entrepriseService.saveEntreprise(mockEntreprise);

        verify(entrepriseRepository).save(mockEntreprise);
        verifyNoInteractions(detteService);
        verifyNoInteractions(contactService);

        assertEquals(savedMockEntreprise, result);
    }

    @Test
    public void testSaveEntrepriseRepositoryFailure() {
        Entreprise mockEntreprise = new Entreprise();

        when(entrepriseRepository.save(any(Entreprise.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> {
            entrepriseService.saveEntreprise(mockEntreprise);
        });

        verify(entrepriseRepository).save(mockEntreprise);
        verifyNoInteractions(detteService);
        verifyNoInteractions(contactService);
    }

    @Test
    public void testSaveEntrepriseDetteServiceFailure() {
        Entreprise mockEntreprise = new Entreprise();
        mockEntreprise.setDettes(List.of(new Dette()));

        when(entrepriseRepository.save(any(Entreprise.class))).thenReturn(mockEntreprise);
        doThrow(new RuntimeException("Dette Service Error")).when(detteService).saveDette(any(Dette.class));

        assertThrows(RuntimeException.class, () -> {
            entrepriseService.saveEntreprise(mockEntreprise);
        });

        verify(entrepriseRepository).save(mockEntreprise);
        verify(detteService).saveDette(any(Dette.class));
        verifyNoInteractions(contactService);
    }

    @Test
    public void testSaveEntrepriseContactServiceFailure() {
        Entreprise mockEntreprise = new Entreprise();
        mockEntreprise.setContacts(List.of(new Contact()));

        when(entrepriseRepository.save(any(Entreprise.class))).thenReturn(mockEntreprise);
        doThrow(new RuntimeException("Contact Service Error")).when(contactService).saveContact(any(Contact.class));

        assertThrows(RuntimeException.class, () -> {
            entrepriseService.saveEntreprise(mockEntreprise);
        });

        verify(entrepriseRepository).save(mockEntreprise);
        verifyNoInteractions(detteService);
        verify(contactService).saveContact(any(Contact.class));
    }

    // Vous pouvez ajouter davantage de tests pour couvrir des scénarios encore plus spécifiques ou d'autres méthodes de la classe EntrepriseService.
}


