package fr.soprasteria.agircarcco.sefiab.processor;

import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.repository.EntrepriseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DebtProcessor {

    private static final Logger log = LoggerFactory.getLogger(DebtProcessor.class);

    private EntrepriseRepository entrepriseRepository;

    public DebtProcessor(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    public void processDebtsForAllCompanies() {
        log.info("Processing debts for all companies...");

        // Récupérer toutes les entreprises depuis la base de données
        List<Entreprise> entreprises = entrepriseRepository.findAll();

        // Parcourir toutes les entreprises et traiter leurs dettes
        for (Entreprise entreprise : entreprises) {
            processDebtForCompany(entreprise);
        }
    }

    public void processDebtForCompany(Entreprise entreprise) {
        log.info("Processing debts for entreprise: " + entreprise.getName());

        // Récupérer les dettes de l'entreprise depuis la base de données
        List<Dette> dettes = entreprise.getDettes();

        // Logique de traitement des dettes
        double updatedDebt = calculateUpdatedDebt(entreprise, dettes);

        // Mettre à jour la dette de l'entreprise
        entreprise.setDebtAmount(updatedDebt);

        // Enregistrer les modifications de l'entreprise dans la base de données
        entrepriseRepository.save(entreprise);
    }

    private double calculateUpdatedDebt(Entreprise entreprise, List<Dette> dettes) {
        // Logique de calcul de la dette mise à jour
        double updatedDebt = 0.0;

        for (Dette dette : dettes) {
            // Appliquer la logique de calcul ici (par exemple, sommer toutes les dettes)
            updatedDebt += dette.getAmount();
        }

        // Autres calculs ou traitements de la dette

        return updatedDebt;
    }
}

