package fr.soprasteria.agircarcco.sefiab.service;

import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.repository.DetteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DetteServiceTest {

    @Mock
    private DetteRepository detteRepository;

    @InjectMocks
    private DetteService detteService;

    @Autowired
    private EntrepriseService entrepriseService;


    private Dette sampleDette;

    @BeforeEach
    public void setUp() {
        // Initialisation d'une Dette de test
        sampleDette = new Dette();
        sampleDette.setId(1L);
        sampleDette.setAmount(1000.0);
    }

    @Test
    public void testSaveDette() {
        // Mock du comportement de la méthode save du repository
        when(detteRepository.save(any(Dette.class))).thenReturn(sampleDette);

        Dette result = detteService.saveDette(sampleDette);

        // Vérifications
        assertEquals(sampleDette, result, "La dette retournée devrait être la même que la dette échantillon");
        verify(detteRepository, times(1)).save(sampleDette);
    }

    // Test Unitaire
    @Test
    public void testFindByAmountGreaterThan() {
        Dette dette = new Dette();
        dette.setAmount(1000);
        Mockito.when(detteRepository.findByAmountGreaterThan(500.0)).thenReturn(Arrays.asList(dette));
        List<Dette> dettes = detteService.findByAmountGreaterThan(500.0);
        assertFalse(dettes.isEmpty());
        assertEquals(1000, dettes.get(0).getAmount(), 0.001);
    }


//    @Test
//    public void testIntegrationFindByAmountGreaterThan() {
//        // Step 1: Clean up the database.
//        cleanupDatabase();
//
//        // Step 1: Create and save an Entreprise object, or fetch an existing one.
//        Entreprise testEntreprise = new Entreprise();
//        // ... set any other necessary fields on the Entreprise
//        // ... assuming you have an EntrepriseService and corresponding repository:
//        testEntreprise = entrepriseService.saveEntreprise(testEntreprise);
//
//        // Step 2: Insert a known record for testing.
//        Dette testDette = new Dette();
//        testDette.setAmount(600.0);
//        testDette.setEntreprise(testEntreprise);  // Associate with the Entreprise object
//        Dette savedDette = detteService.saveDette(testDette);
//
//
//        // Assert that savedDette is not null to ensure that it was saved successfully.
//        assertNotNull(savedDette, "Saved dette should not be null");
//        assertNotNull(savedDette.getId(), "Saved dette should have an ID");  // assuming there's an ID field.
//
//        // Step 3: Fetch records with amount > 500.0
//        List<Dette> dettes = detteService.findByAmountGreaterThan(500.0);
//
//        // For debugging purposes, print out the list.
//        dettes.forEach(dette -> System.out.println(dette.getAmount()));
//
//        // Step 4: Check that our saved record is in the returned list based on the amount.
//        assertTrue(dettes.stream().anyMatch(dette -> Objects.equals(dette.getAmount(), savedDette.getAmount())),
//                "Saved dette amount should be found in the list of dettes with amount greater than 500");
//    }
//
//    // Method to cleanup database.
//    public void cleanupDatabase() {
//        detteService.deleteAll();  // assuming you have a deleteAll method in your service that calls the repository's deleteAll.
//    }







    // Test de non-régression
    @Test
    public void testNonRegressionFindByAmountGreaterThan() {
        Dette dette = new Dette();
        dette.setAmount(1000);
        Mockito.when(detteRepository.findByAmountGreaterThan(500.0)).thenReturn(Arrays.asList(dette));
        List<Dette> dettes = detteService.findByAmountGreaterThan(500.0);
        assertFalse(dettes.isEmpty());
        assertEquals(1000, dettes.get(0).getAmount(), 0.001);
    }


    @Test
    public void testSaveDetteWithNull() {

        // We expect a NullPointerException to be thrown from the service
        assertThrows(IllegalArgumentException.class, () -> {
            detteService.saveDette(null);
        });

    }


    // ... Autres tests
    @Test
    public void testSaveDetteReturnsNull() {
        when(detteRepository.save(any(Dette.class))).thenReturn(null);

        Dette result = detteService.saveDette(sampleDette);

        assertNull(result, "La dette retournée devrait être null");
        verify(detteRepository, times(1)).save(sampleDette);
    }

    @Test
    public void testSaveDetteThrowsException() {
        when(detteRepository.save(any(Dette.class))).thenThrow(new RuntimeException("Erreur inattendue"));

        // Attendez-vous à ce qu'une exception soit levée
        assertThrows(RuntimeException.class, () -> {
            detteService.saveDette(sampleDette);
        });

        verify(detteRepository, times(1)).save(sampleDette);
    }

    @Test
    public void testSaveDetteWithNegativeAmount() {
        sampleDette.setAmount(-1000.0);

        // Ici, on suppose que le montant de la dette ne peut pas être négatif.
        // Si cette règle métier n'existe pas, retirez ce test.
        assertThrows(IllegalArgumentException.class, () -> {
            detteService.saveDette(sampleDette);
        });

        verify(detteRepository, never()).save(sampleDette); // Nous vérifions que save() n'est jamais appelé dans ce cas.
    }

}

