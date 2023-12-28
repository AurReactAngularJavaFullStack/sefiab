//package fr.soprasteria.agircarcco.sefiab.batch.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.integration.config.EnableIntegration;
//import org.springframework.integration.support.MessageBuilder;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.messaging.Message;
//
//@Configuration
//@EnableIntegration
//@EnableKafka
//public class WorkerConfig {
//
//    @Value("${kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    @Bean
//    public DirectChannel requests() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public DirectChannel replies() {
//        return new DirectChannel();
//    }
//
//    // Configuration de Kafka pour consommer les partitions (messages) envoyées par le master
//    @ServiceActivator(inputChannel = "requests", outputChannel = "replies")
//    public Message<?> handlePartition(Message<?> request) {
//        // Votre logique de traitement ici
//        return MessageBuilder.withPayload("Réponse").build(); // Une réponse après le traitement
//    }
//
//    // Autres beans pour la configuration de Kafka et la réception de messages
//}
//
