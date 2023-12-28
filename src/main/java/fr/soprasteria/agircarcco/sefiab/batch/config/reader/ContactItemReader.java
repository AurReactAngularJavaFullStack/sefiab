package fr.soprasteria.agircarcco.sefiab.batch.config.reader;

import fr.soprasteria.agircarcco.sefiab.batch.config.utils.ContactFieldSetMapper;
import fr.soprasteria.agircarcco.sefiab.model.Contact;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class ContactItemReader {

    @Bean(name = "contactItemReaderBean")
    public FlatFileItemReader<Contact> contactReader() {
        FlatFileItemReader<Contact> reader = new FlatFileItemReader<>();

        reader.setResource(new ClassPathResource("contact.csv"));
        reader.setLinesToSkip(1); // skip the header

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"contact_id", "name", "first_name", "last_name", "entreprise_id"});

        DefaultLineMapper<Contact> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new ContactFieldSetMapper());

        reader.setLineMapper(lineMapper);

        return reader;
    }

}