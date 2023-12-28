package fr.soprasteria.agircarcco.sefiab.repository;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("SELECT c FROM Contact c WHERE LENGTH(c.email) > ?1")
    List<Contact> findByEmailLengthGreaterThan(int length);

    @Query("SELECT c FROM Contact c WHERE c.entreprise.name = ?1")
    List<Contact> findByEntrepriseName(String entrepriseName);

    @Query("SELECT c.entreprise, COUNT(c) FROM Contact c GROUP BY c.entreprise HAVING COUNT(c) > ?1")
    List<Object[]> getEntreprisesWithMoreThanXContacts(Long count);
    Optional<Contact> findByEmail(String email);
    List<Contact> findByFirstName(String firstName);
    List<Contact> findByLastName(String lastName);
    List<Contact> findByFirstNameAndLastName(String firstName, String lastName);
    List<Contact> findByEntreprise(Entreprise entreprise);
    List<Contact> findByFirstNameIgnoreCase(String firstName);
    List<Contact> findByLastNameIgnoreCase(String lastName);
    List<Contact> findByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);
    boolean existsByEmail(String email);
    List<Contact> findByFirstNameOrLastName(String firstName, String lastName);
    List<Contact> findByFirstNameStartingWith(String prefix);
    List<Contact> findByLastNameEndingWith(String suffix);
}

