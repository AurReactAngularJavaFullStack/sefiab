package fr.soprasteria.agircarcco.sefiab.batch.config.utils;

import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class EntrepriseFieldSetMapper implements FieldSetMapper<Entreprise> {

    @Override
    public Entreprise mapFieldSet(FieldSet fieldSet) throws BindException {
        Entreprise entreprise = new Entreprise();
        entreprise.setId(fieldSet.readLong("id"));
        entreprise.setName(fieldSet.readString("name"));
        entreprise.setDebtAmount(fieldSet.readDouble("debtAmount"));
        return entreprise;
    }

}

