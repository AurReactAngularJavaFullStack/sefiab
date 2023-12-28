//package fr.soprasteria.agircarcco.sefiab.repository;
//
//import fr.soprasteria.agircarcco.sefiab.model.Dette;
//import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.TestPropertySource;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.mock;
//
//@DataJpaTest
//@Import(DetteRepositoryTest.TestConfig.class)
//@TestPropertySource(locations = "classpath:application-test.properties") // Explicitly specify the properties file to use
//public class DetteRepositoryTest {
//
//    @Autowired
//    private DetteRepository detteRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private EntrepriseRepository entrepriseRepository;
//
//    @BeforeEach
//    public void setUp() {
//        Entreprise entreprise1 = new Entreprise("Entreprise 1", 150.0);
//        Entreprise entreprise2 = new Entreprise("Entreprise 2", 80.0);
//
//        entrepriseRepository.save(entreprise1);
//        entrepriseRepository.save(entreprise2);
//
//        Dette dette1 = new Dette(50.0, entreprise1);
//        Dette dette2 = new Dette(30.0, entreprise1);
//        Dette dette3 = new Dette(40.0, entreprise2);
//
//        detteRepository.save(dette1);
//        detteRepository.save(dette2);
//        detteRepository.save(dette3);
//
////        entityManager.persist(entreprise1);
////        entityManager.persist(entreprise2);
////        entityManager.persist(dette1);
////        entityManager.persist(dette2);
////        entityManager.persist(dette3);
////        entityManager.flush();
//    }
//
//    @Test
//    @Rollback(false)
//    public void testFindNamesByDebtAmount() {
//        List<String> entrepriseNames = detteRepository.findNamesByDebtAmount(100.0);
//        assertEquals(1, entrepriseNames.size());
//        assertTrue(entrepriseNames.contains("Entreprise 1"));
//    }
//
//    @TestConfiguration
//    static class TestConfig {
//
//        @Bean
//        public JobLauncher jobLauncher() {
//            return mock(JobLauncher.class);
//        }
//
//        @Bean
//        public Job fiabilizationJob() {
//            return mock(Job.class);
//        }
//    }
//}
//
//
//
//
////package fr.soprasteria.agircarcco.sefiab.repository;
////
////import fr.soprasteria.agircarcco.sefiab.model.Dette;
////import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////import org.mockito.Mockito;
////import org.springframework.batch.core.Job;
////import org.springframework.batch.core.launch.JobLauncher;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
////import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
////import org.springframework.boot.test.context.TestConfiguration;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Import;
////import org.springframework.test.annotation.Rollback;
////
////import java.util.List;
////
////import static org.junit.jupiter.api.Assertions.assertEquals;
////import static org.junit.jupiter.api.Assertions.assertTrue;
////
////@DataJpaTest
////@Import(DetteRepositoryTest.TestConfig.class)
////public class DetteRepositoryTest {
////
////    @Autowired
////    private DetteRepository detteRepository;
////
////    @Autowired
////    private TestEntityManager entityManager;
////
////    @BeforeEach
////    public void setUp() {
////        // Préparation des données de test
////        Entreprise entreprise1 = new Entreprise();
////        entreprise1.setName("Entreprise 1");
////        entreprise1.setDebtAmount(150.0);
////
////        Entreprise entreprise2 = new Entreprise();
////        entreprise2.setName("Entreprise 2");
////        entreprise2.setDebtAmount(80.0);
////
////        Dette dette1 = new Dette();
////        dette1.setAmount(50.0);
////        dette1.setEntreprise(entreprise1);
////
////        Dette dette2 = new Dette();
////        dette2.setAmount(30.0);
////        dette2.setEntreprise(entreprise1);
////
////        Dette dette3 = new Dette();
////        dette3.setAmount(40.0);
////        dette3.setEntreprise(entreprise2);
////
////        entityManager.persist(entreprise1);
////        entityManager.persist(entreprise2);
////        entityManager.persist(dette1);
////        entityManager.persist(dette2);
////        entityManager.persist(dette3);
////        entityManager.flush();
////    }
////
////    @Test
////    @Rollback(false) // Pour éviter un rollback automatique après le test
////    public void testFindNamesByDebtAmount() {
////        // Exécutez la méthode que vous voulez tester
////        List<String> entrepriseNames = detteRepository.findNamesByDebtAmount(100.0);
////
////        // Assurez-vous que le résultat est correct
////        assertEquals(1, entrepriseNames.size());
////        assertTrue(entrepriseNames.contains("Entreprise 1"));
////    }
////    @TestConfiguration
////    static class TestConfig {
////
////        @Bean
////        public JobLauncher jobLauncher() {
////            return Mockito.mock(JobLauncher.class);
////        }
////
////        @Bean
////        public Job fiabilizationJob() {
////            return Mockito.mock(Job.class);
////        }
////
////    }
////}
