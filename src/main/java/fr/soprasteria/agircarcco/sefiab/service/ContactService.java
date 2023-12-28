package fr.soprasteria.agircarcco.sefiab.service;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    private ContactService contactService;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public List<Contact> findByEmailLengthGreaterThan(int length) {
        return contactRepository.findByEmailLengthGreaterThan(length);
    }

    public List<Contact> findByEntrepriseName(String entrepriseName) {
        return contactRepository.findByEntrepriseName(entrepriseName);
    }

    public List<Object[]> getEntreprisesWithMoreThanXContacts(Long count) {
        return contactRepository.getEntreprisesWithMoreThanXContacts(count);
    }


    public Contact findByEmail(String email) {
        return contactRepository.findByEmail(email).orElse(null);
    }

    public List<Contact> findByFirstName(String firstName) {
        return contactRepository.findByFirstName(firstName);
    }

    public List<Contact> findByLastName(String lastName) {
        return contactRepository.findByLastName(lastName);
    }

    public List<Contact> findByFullName(String firstName, String lastName) {
        return contactRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public List<Contact> findByFullNameIgnoreCase(String firstName, String lastName) {
        return contactRepository.findByFirstNameAndLastNameAllIgnoreCase(firstName, lastName);
    }

    public List<Contact> findContactsByEntreprise(Entreprise entreprise) {
        return contactRepository.findByEntreprise(entreprise);
    }

    public boolean emailExists(String email) {
        return contactRepository.existsByEmail(email);
    }

    public List<Contact> findContactsByFirstNameOrLastName(String firstName, String lastName) {
        return contactRepository.findByFirstNameOrLastName(firstName, lastName);
    }

    public List<Contact> findContactsByFirstNameStartingWith(String prefix) {
        return contactRepository.findByFirstNameStartingWith(prefix);
    }

    public List<Contact> findContactsByLastNameEndingWith(String suffix) {
        return contactRepository.findByLastNameEndingWith(suffix);
    }
}
