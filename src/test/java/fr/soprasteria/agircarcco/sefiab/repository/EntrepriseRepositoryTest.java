//package fr.soprasteria.agircarcco.sefiab.repository;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//
//@DataJpaTest
//public class EntrepriseRepositoryTest {
//
//    @Mock
//    private EntrepriseRepository entrepriseRepository;
//
//    @BeforeEach
//    public void setUp() {
//        // Mock the behavior of the repository
//        when(entrepriseRepository.findNamesByDebtAmount(100.0)).thenReturn(Arrays.asList("Entreprise 1"));
//        when(entrepriseRepository.findNamesByDebtAmount(200.0)).thenReturn(Arrays.asList("Entreprise 1", "Entreprise 2"));
//    }
//
//    @Test
//    public void testFindNamesByDebtAmountWithThreshold100() {
//        double threshold = 100.0;
//        List<String> entrepriseNames = entrepriseRepository.findNamesByDebtAmount(threshold);
//
//        assertEquals(1, entrepriseNames.size());
//        assertEquals("Entreprise 1", entrepriseNames.get(0));
//    }
//
//    @Test
//    public void testFindNamesByDebtAmountWithThreshold200() {
//        double threshold = 200.0;
//        List<String> entrepriseNames = entrepriseRepository.findNamesByDebtAmount(threshold);
//
//        assertEquals(2, entrepriseNames.size());
//        assertEquals("Entreprise 1", entrepriseNames.get(0));
//        assertEquals("Entreprise 2", entrepriseNames.get(1));
//    }
//}
