//package fr.soprasteria.agircarcco.sefiab.batch.config;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.annotation.MessagingGateway;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.integration.config.EnableIntegration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.*;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//import org.springframework.messaging.Message;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableIntegration
//@EnableKafka
//public class MasterConfig {
//
//    @Value("${kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    @Value("${kafka.consumer.group-id}")
//    private String groupId;
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
//    private Map<String, Object> commonKafkaProps() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        return props;
//    }
//
//
//    @Bean
//    public ProducerFactory<String, Object> producerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    @Bean
//    public ConsumerFactory<String, Object> consumerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "master-group");
//        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringDeserializer.class);
//        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(configProps, new org.apache.kafka.common.serialization.StringDeserializer(),
//                new JsonDeserializer<>());
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
//    // Configuration de Kafka pour envoyer les partitions (messages) aux workers
//    @MessagingGateway(defaultRequestChannel = "requests")
//    public interface PartitionGateway {
//        void sendPartition(Message<?> message);
//    }
//}
//
//
