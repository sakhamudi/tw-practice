package com.kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;


@Component
public class KafkaShutdownHandler {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaShutdownHandler(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("Flushing Kafka messages before shutdown...");
        kafkaTemplate.flush(); // Blocks until all messages are sent
        System.out.println("Kafka producer shutdown complete.");
    }
}