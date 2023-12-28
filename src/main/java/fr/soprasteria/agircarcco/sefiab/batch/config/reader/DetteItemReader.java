package fr.soprasteria.agircarcco.sefiab.batch.config.reader;

import fr.soprasteria.agircarcco.sefiab.batch.config.utils.DetteFieldSetMapper;
import fr.soprasteria.agircarcco.sefiab.model.Dette;
import fr.soprasteria.agircarcco.sefiab.repository.EntrepriseRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class DetteItemReader {

    private final EntrepriseRepository entrepriseRepository;

    @Autowired
    public DetteItemReader(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    @Bean(name = "detteItemReaderBean")
    public FlatFileItemReader<Dette> detteReader() {
        FlatFileItemReader<Dette> reader = new FlatFileItemReader<>();

        reader.setResource(new ClassPathResource("dette.csv"));
        reader.setLinesToSkip(1);

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"dette_id", "amount", "entreprise_id"});
        tokenizer.setDelimiter(",");

        DefaultLineMapper<Dette> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new DetteFieldSetMapper(entrepriseRepository)); // Passing the repository here

        reader.setLineMapper(lineMapper);

        return reader;
    }

}

