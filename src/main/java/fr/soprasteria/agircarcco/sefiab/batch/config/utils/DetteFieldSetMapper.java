package fr.soprasteria.agircarcco.sefiab.batch.config.utils;

import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import fr.soprasteria.agircarcco.sefiab.repository.EntrepriseRepository;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

@Component
public class DetteFieldSetMapper implements FieldSetMapper<Dette> {

    private final EntrepriseRepository entrepriseRepository; // Assuming you have a repository for Entreprise

    @Autowired
    public DetteFieldSetMapper(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    @Override
    public Dette mapFieldSet(FieldSet fieldSet) throws BindException {
        Dette dette = new Dette();
        dette.setId(fieldSet.readLong("dette_id"));
        dette.setAmount(fieldSet.readDouble("amount"));

        Long entrepriseId = fieldSet.readLong("entreprise_id");
        Entreprise entreprise = fetchEntrepriseById(entrepriseId);
        if (entreprise == null) {
            throw new IllegalArgumentException("No Entreprise found for id: " + entrepriseId);
        }
        dette.setEntreprise(entreprise);

        return dette;
    }

    private Entreprise fetchEntrepriseById(Long id) {
        // Fetch the Entreprise by ID from your repository
        return entrepriseRepository.findById(id).orElse(null);
    }
}


