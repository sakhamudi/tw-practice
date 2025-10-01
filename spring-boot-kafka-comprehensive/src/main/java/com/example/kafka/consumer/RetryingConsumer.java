package com.example.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RetryingConsumer {

    @KafkaListener(topics = "demo-retry", groupId = "group-retry", containerFactory = "kafkaListenerContainerFactory")
    public void listenRetry(String message) {
        System.out.println("[retry] Received: " + message);
        // simulate a processing failure for messages that contain "fail"
        if (message != null && message.toLowerCase().contains("fail")) {
            System.out.println("[retry] Simulating failure for message: " + message);
            throw new RuntimeException("simulated failure");
        }
        System.out.println("[retry] processed successfully: " + message);
    }
}
