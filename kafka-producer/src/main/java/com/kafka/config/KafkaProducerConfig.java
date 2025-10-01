package com.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public NewTopic createTopic(){
        return new NewTopic("javatechie-demo", 3, (short) 1);
    }

    @Bean
    public Map<String,Object> producerConfig(){
        Map<String,Object> props=new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return props;
    }

    // build producer configs (merges properties from application.properties via KafkaProperties)
    @Bean
    public Map<String, Object> producerConfigs(KafkaProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
        // override / add batching configs
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 327_680); // 320 KB
        props.put(ProducerConfig.LINGER_MS_CONFIG, 60_000);   // 60 seconds
        return props;
    }

    // single ProducerFactory bean
    @Bean
    public ProducerFactory<String, Object> producerFactory(Map<String, Object> producerConfigs) {
        return new DefaultKafkaProducerFactory<>(producerConfigs);
    }

    // KafkaTemplate wiring
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
