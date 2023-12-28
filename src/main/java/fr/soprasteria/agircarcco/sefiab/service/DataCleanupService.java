package fr.soprasteria.agircarcco.sefiab.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataCleanupService {

    @Autowired
    private SessionFactory sessionFactory;

    // Cette méthode vérifie une condition préalable pour l'illustration
    // Vous pouvez la remplacer par votre propre logique
    protected boolean initialConditionIsMet() {
        // Exemple de condition. À remplacer par votre propre logique.
        return true;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void performCleanup() {
        if (!initialConditionIsMet()) {
            return;
        }
        System.out.println("Performing data cleanup...");

        // Vous pouvez ajouter ou ajuster ces conditions en fonction de vos besoins
        cleanupEntity("Contact", "someConditionForContact = :conditionValue");
        cleanupEntity("Dette", "someConditionForDette = :conditionValue");
        cleanupEntity("Entreprise", "someConditionForEntreprise = :conditionValue");
    }

    private void cleanupEntity(String entityName, String condition) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String yourConditionValue = "someValue"; // Ceci est un exemple, ajustez en fonction de vos besoins

            String hql = String.format("DELETE FROM %s WHERE %s", entityName, condition);
            int deletedRows = session.createQuery(hql)
                    .setParameter("conditionValue", yourConditionValue)
                    .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback transaction if there was an error
            }
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }
}

