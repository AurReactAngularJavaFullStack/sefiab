//package fr.soprasteria.agircarcco.sefiab.repository;
//
//import fr.soprasteria.agircarcco.sefiab.model.Contact;
//import org.junit.jupiter.api.Test;
//import org.springframework.batch.core.BatchStatus;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Import;
//import org.mockito.Mockito;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Import(ContactRepositoryTest.TestConfig.class)
//public class ContactRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private ContactRepository contactRepository;
//
//    @Test
//    public void testSaveContact() {
//        Contact contact = new Contact();
//        contact.setEmail("test@example.com");
//        contact.setFirstName("John");
//        contact.setLastName("Doe");
//        Contact savedContact = entityManager.persistAndFlush(contact);
//        Contact retrievedContact = contactRepository.findById(savedContact.getId()).orElse(null);
//        assertNotNull(retrievedContact);
//        assertEquals(contact.getEmail(), retrievedContact.getEmail());
//        assertEquals(contact.getFirstName(), retrievedContact.getFirstName());
//        assertEquals(contact.getLastName(), retrievedContact.getLastName());
//    }
//
//    @Test
//    public void testFindByEmail() {
//        Contact contact = new Contact();
//        contact.setEmail("test@example.com");
//        entityManager.persistAndFlush(contact);
//        Contact retrievedContact = contactRepository.findByEmail("test@example.com").orElse(null);
//        assertNotNull(retrievedContact);
//        assertEquals(contact.getEmail(), retrievedContact.getEmail());
//    }
//
//    // Static nested configuration class to mock the JobLauncher, Job, and JobExecution
//    @TestConfiguration
//    static class TestConfig {
//
//        @Bean
//        public JobLauncher jobLauncher() {
//            JobLauncher mockJobLauncher = Mockito.mock(JobLauncher.class);
//            try {
//                Mockito.when(mockJobLauncher.run(Mockito.any(Job.class), Mockito.any(JobParameters.class)))
//                        .thenReturn(jobExecution());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return mockJobLauncher;
//        }
//
//        @Bean
//        public Job job() {
//            return Mockito.mock(Job.class);
//        }
//
//        @Bean
//        public JobExecution jobExecution() {
//            JobExecution mockExecution = Mockito.mock(JobExecution.class);
//            Mockito.when(mockExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
//            return mockExecution;
//        }
//    }
//}
//
//
