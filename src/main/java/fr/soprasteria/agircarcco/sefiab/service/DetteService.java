package fr.soprasteria.agircarcco.sefiab.service;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.repository.DetteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class DetteService {

    @Autowired
    private final DetteRepository detteRepository;
    private final EntrepriseService entrepriseService;
    private final ContactService contactService;


    @Autowired
    public DetteService(DetteRepository detteRepository,
                        @Lazy EntrepriseService entrepriseService,
                        ContactService contactService) {
        this.detteRepository = detteRepository;
        this.entrepriseService = entrepriseService;
        this.contactService = contactService;
    }

    @Transactional
    public Dette saveDette(Dette dette) {
        if (dette == null) {
            throw new IllegalArgumentException("Dette cannot be null");
        }
        if (dette.getAmount() < 0) {
            throw new IllegalArgumentException("Dette amount cannot be negative");
        }
        Dette saved = detteRepository.save(dette);
        // Add logging to help debug
        System.out.println("Saved dette: " + saved);
        return saved;
    }


    @Transactional
    public void deleteAll() {
        detteRepository.deleteAll();
    }

    public List<Dette> findByEntrepriseName(String entrepriseName) {
        return detteRepository.findByEntrepriseName(entrepriseName);
    }

    public List<Dette> findByEntrepriseNameWithFetch(String entrepriseName) {
        return detteRepository.findByEntrepriseNameWithFetch(entrepriseName);
    }

    public List<Dette> findByAmountGreaterThan(Double amount) {
        return detteRepository.findByAmountGreaterThan(amount);
    }

    public List<Object[]> getTotalDebtByEntreprise() {
        return detteRepository.getTotalDebtByEntreprise();
    }

    public List<Dette> getDebtsByContactEmail(String email) {
        Contact contact = contactService.findByEmail(email);
        if (contact != null && contact.getEntreprise() != null) {
            // Supposez qu'il existe une méthode `findByEntreprise` dans `DetteRepository`.
            return detteRepository.findByEntreprise(contact.getEntreprise());
        }
        return new ArrayList<>();
    }

    public List<String> getNamesByDebtAmount(Double debtAmount) {
        return detteRepository.findNamesByDebtAmount(debtAmount);
    }

    public List<Dette> getDebtByEntreprise(String entrepriseName) {
        Entreprise entreprise = entrepriseService.findByName(entrepriseName);
        return detteRepository.findByEntreprise(entreprise);
    }

    public List<Dette> getDebtByEntrepriseId(Long entrepriseId) {
        Entreprise entreprise = entrepriseService.findById(entrepriseId); // Supposons que vous avez une méthode findById
        return detteRepository.findByEntreprise(entreprise);
    }


    public List<Dette> getDebtsAboveAmount(Double amount) {
        return detteRepository.findByAmountGreaterThan(amount);
    }

    public List<Dette> getDebtsBelowAmount(Double amount) {
        return detteRepository.findByAmountLessThan(amount);
    }

    public Dette getHighestDebt() {
        return detteRepository.findTopByOrderByAmountDesc().orElse(null);
    }

    public Dette getLowestDebt() {
        return detteRepository.findTopByOrderByAmountAsc().orElse(null);
    }

    public Double getTotalDebtForEntreprise(String entrepriseName) {
        Entreprise entreprise = entrepriseService.findByName(entrepriseName);
        return detteRepository.sumAmountByEntreprise(entreprise);
    }

    public Long countDebtsForEntreprise(String entrepriseName) {
        Entreprise entreprise = entrepriseService.findByName(entrepriseName);
        return detteRepository.countByEntreprise(entreprise);
    }

    public Double getAverageDebt() {
        return detteRepository.findAverageDebt();
    }

    public List<Dette> getDebtsWithinRange(Double start, Double end) {
        return detteRepository.findByAmountBetween(start, end);
    }

    public List<Entreprise> getEntreprisesWithExactDebt(Double amount) {
        return detteRepository.findEntreprisesWithExactDebtAmount(amount);
    }

    public Boolean checkDebtExists(Double amount) {
        return detteRepository.existsByAmount(amount);
    }
}
