package fr.soprasteria.agircarcco.sefiab.batch.config.writer;

import fr.soprasteria.agircarcco.sefiab.model.Contact;
import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import jakarta.persistence.EntityManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/*La première classe EntrepriseItemWriter est une version simplifiée du CustomItemWriter.
Elle se concentre uniquement sur la mise à jour (ou fusion) des objets Entreprise dans la base de données via
entityManager.merge(entreprise);.
En comparaison, la classe CustomItemWriter non seulement met à jour les objets Entreprise, mais s'assure également que les
objets associés Contact et Dette sont également mis à jour et que leurs relations avec l'objet Entreprise sont correctement
configurées.
Si votre intention est d'avoir un ItemWriter séparé pour Entreprise, Contact, et Dette, alors EntrepriseItemWriter pourrait
avoir besoin d'une logique supplémentaire pour s'assurer que tous les éléments associés sont correctement gérés.
Cependant, si vous voulez juste un ItemWriter pour Entreprise qui se concentre uniquement sur les objets Entreprise sans
se préoccuper des objets associés, alors la première classe est correcte telle quelle.*/
@Component
public class EntrepriseItemWriter implements ItemWriter<Entreprise> {

    private final EntityManager entityManager;

    @Autowired
    public EntrepriseItemWriter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public void write(Chunk<? extends Entreprise> chunk) throws Exception {
        for (Entreprise entreprise : chunk.getItems()) {
            entityManager.merge(entreprise);

            if (entreprise.getContacts() != null) {
                for (Contact contact : entreprise.getContacts()) {
                    if (contact.getEntreprise() == null || !contact.getEntreprise().equals(entreprise)) {
                        contact.setEntreprise(entreprise);
                    }
                    entityManager.merge(contact);
                }
            }

            if (entreprise.getDettes() != null) {
                for (Dette dette : entreprise.getDettes()) {
                    if (dette.getEntreprise() == null || !dette.getEntreprise().equals(entreprise)) {
                        dette.setEntreprise(entreprise);
                    }
                    entityManager.merge(dette);
                }
            }
        }
    }

}

