package fr.soprasteria.agircarcco.sefiab.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Autowired
    private MailProperties mailProperties;

    @Bean
    public MailProperties mailProperties() {
        return new MailProperties();
    }


    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());;

        Properties javaMailProperties = new Properties();
        javaMailProperties.putAll(mailProperties.getProperties());
        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }
}
