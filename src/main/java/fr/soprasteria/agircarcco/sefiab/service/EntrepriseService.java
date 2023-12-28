package fr.soprasteria.agircarcco.sefiab.service;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.repository.EntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EntrepriseService {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private DetteService detteService;

    @Autowired
    private ContactService contactService;

    public EntrepriseService(EntrepriseRepository entrepriseRepository, DetteService detteService, ContactService contactService) {
        this.entrepriseRepository = entrepriseRepository;
        this.detteService = detteService;
        this.contactService = contactService;
    }


    public Optional<Entreprise> findWithHighestDebt() {
        return entrepriseRepository.findWithHighestDebt();
    }

    public List<Entreprise> findAllOrderByDebtDesc() {
        return entrepriseRepository.findAllOrderByDebtDesc();
    }

    public Entreprise findByName(String name) {
        return entrepriseRepository.findByName(name).orElse(null);
    }

    public Entreprise findById(Long id) {
        return entrepriseRepository.findById(id).orElse(null);
    }


    @Transactional
    public Entreprise saveEntreprise(Entreprise entreprise) {
        // Enregistrez l'entreprise dans la table "entreprise"
        Entreprise savedEntreprise = entrepriseRepository.save(entreprise);

        // Enregistrez les dettes de l'entreprise
        for (Dette dette : entreprise.getDettes()) {
            dette.setEntreprise(savedEntreprise);
            detteService.saveDette(dette);
        }

        // Enregistrez les contacts de l'entreprise
        for (Contact contact : entreprise.getContacts()) {
            contact.setEntreprise(savedEntreprise);
            contactService.saveContact(contact);
        }

        return savedEntreprise;
    }

    public List<String> getNamesByDebtAmount(Double debtAmount) {
        return entrepriseRepository.findNamesByDebtAmount(debtAmount);
    }

    public List<Entreprise> findByDebtAmountGreaterThan(Double debtAmount) {
        return entrepriseRepository.findByDebtAmountGreaterThan(debtAmount);
    }

    public List<Entreprise> findByDebtAmountLessThan(Double debtAmount) {
        return entrepriseRepository.findByDebtAmountLessThan(debtAmount);
    }

    public List<Entreprise> findByDebtAmountBetween(Double startAmount, Double endAmount) {
        return entrepriseRepository.findByDebtAmountBetween(startAmount, endAmount);
    }

    public List<Entreprise> findByNameContaining(String namePart) {
        return entrepriseRepository.findByNameContaining(namePart);
    }

    public List<Entreprise> findByNameStartingWith(String namePrefix) {
        return entrepriseRepository.findByNameStartingWith(namePrefix);
    }

    public List<Entreprise> findByNameEndingWith(String nameSuffix) {
        return entrepriseRepository.findByNameEndingWith(nameSuffix);
    }

    public List<Entreprise> findByDettesIn(List<Dette> dettes) {
        return entrepriseRepository.findByDettesIn(dettes);
    }

    public boolean nameExists(String name) {
        return entrepriseRepository.existsByName(name);
    }
}
