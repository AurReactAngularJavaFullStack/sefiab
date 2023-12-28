package fr.soprasteria.agircarcco.sefiab.batch.config.utils;


import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.ClassPathResource;

public class BatchUtility {

    public static <T> FlatFileItemReader<T> createDelegate(String filename, Class<T> type, String[] columnNames) {
        return new FlatFileItemReaderBuilder<T>()
                .name(filename + "ItemReader")
                .resource(new ClassPathResource(filename))
                .delimited()
                .names(columnNames)
                .targetType(type)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<T>() {{
                    setTargetType(type);
                }})
                .linesToSkip(1)
                .build();
    }
}
