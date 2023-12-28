package fr.soprasteria.agircarcco.sefiab.batch.config.reader;

import fr.soprasteria.agircarcco.sefiab.batch.config.utils.EntrepriseFieldSetMapper;
import fr.soprasteria.agircarcco.sefiab.model.Entreprise;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EntrepriseItemReader {

    private static final int MAX_LINES_TO_READ = 50;

    @Autowired
    private ResourceLoader resourceLoader;

    private AtomicInteger currentLinesRead = new AtomicInteger(0);

    /**
     * Creates a bean for the Entreprise item reader.
     *
     * @return the reader bean.
     */
    @Bean(name = "entrepriseItemReaderBean")
    @Scope("prototype")
    public FlatFileItemReader<Entreprise> entrepriseReader() {
        Resource resource = resourceLoader.getResource("classpath:entreprise.csv");
        return createEntrepriseItemReader(resource);
    }

    /**
     * Creates the actual FlatFileItemReader for the Entreprise.
     *
     * @param resource the resource to read from.
     * @return the created reader.
     */
    private FlatFileItemReader<Entreprise> createEntrepriseItemReader(Resource resource) {
        FlatFileItemReader<Entreprise> reader = new FlatFileItemReader<>();

        reader.setResource(resource);
        reader.setLineMapper(createDefaultLineMapper());
        reader.setLinesToSkip(1); // skip header line
        reader.setSkippedLinesCallback(this::handleSkippedLines);

        return reader;
    }

    /**
     * Configures and creates a default line mapper for the Entreprise.
     *
     * @return the created line mapper.
     */
    private DefaultLineMapper<Entreprise> createDefaultLineMapper() {
        DefaultLineMapper<Entreprise> lineMapper = new DefaultLineMapper<>();

        lineMapper.setLineTokenizer(createLineTokenizer(new String[]{"id", "name", "debtAmount"}));
        lineMapper.setFieldSetMapper(new EntrepriseFieldSetMapper());

        return lineMapper;
    }

    /**
     * Creates a tokenizer for the given column names.
     *
     * @param columnNames the column names to tokenize.
     * @return the created tokenizer.
     */
    private DelimitedLineTokenizer createLineTokenizer(String[] columnNames) {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(","); // set this if you need a specific delimiter
        tokenizer.setNames(columnNames);
        return tokenizer;
    }

    /**
     * Handles any skipped lines during the reading process.
     *
     * @param line the skipped line.
     */
    void handleSkippedLines(String line) {
        if (currentLinesRead.incrementAndGet() >= MAX_LINES_TO_READ) {
            throw new RuntimeException("Limit reached: Only processing first 50 lines.");
        }
    }
}



//@Bean(name = "entrepriseItemReaderBean")
    //@Scope("prototype")
    //public FlatFileItemReader<Entreprise> entrepriseReader() {
        //return createDelegate("entreprise.csv", Entreprise.class, new String[]{"id", "name", "debtAmount"});
    //}
