package fr.soprasteria.agircarcco.sefiab.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "fr.soprasteria.agircarcco.sefiab.repository") // Enable JPA repositories
@EnableTransactionManagement // Enable transactions
public class HibernateConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("fr.soprasteria.agircarcco.sefiab.model"); // Scan JPA entities
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        // Add any Hibernate-specific properties here if needed
        // For example:
        // properties.setProperty("hibernate.hbm2ddl.auto", "update");
        // properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        em.setJpaProperties(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
}




//package fr.soprasteria.agircarcco.sefiab.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class HibernateConfig {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource);
//        sessionFactory.setPackagesToScan("fr.soprasteria.agircarcco.sefiab.model"); // Remplacez par le package de vos entités
//        return sessionFactory;
//    }
//}
