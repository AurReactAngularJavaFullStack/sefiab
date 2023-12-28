package fr.soprasteria.agircarcco.sefiab.batch.config.writer;

import fr.soprasteria.agircarcco.sefiab.model.Dette;
import jakarta.persistence.EntityManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DetteItemWriter implements ItemWriter<Dette> {

    private final EntityManager entityManager;

    @Autowired
    public DetteItemWriter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public void write(Chunk<? extends Dette> chunk) throws Exception {
        for (Dette dette : chunk.getItems()) {
            entityManager.merge(dette);
        }
    }
}
