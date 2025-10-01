package com.example.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleConsumer {

    @KafkaListener(topics = "demo-topic", groupId = "group-simple")
    public void listen(String message) {

        System.out.println("[simple] Received: " + message);
    }
}
