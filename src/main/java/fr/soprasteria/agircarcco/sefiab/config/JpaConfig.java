//package fr.soprasteria.agircarcco.sefiab.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//
//import javax.sql.DataSource;
//
//
//@Configuration
//public class JpaConfig {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
//        return builder
//                .dataSource(dataSource)
//                .packages("fr.soprasteria.agircarcco.sefiab.batch.config") // Base package for scanning JPA entities
//                .persistenceUnit("myJpaUnit")
//                .build();
//    }
//}
//
