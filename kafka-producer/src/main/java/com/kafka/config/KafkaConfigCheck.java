package com.kafka.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

@Configuration
public class KafkaConfigCheck {

    @Autowired
    private KafkaProperties kafkaProperties;

    @PostConstruct
    public void printProducerProps() {
        Map<String, Object> props = kafkaProperties.buildProducerProperties();
        System.out.println(">>> buildProducerProperties(): " + props);
    }
}
