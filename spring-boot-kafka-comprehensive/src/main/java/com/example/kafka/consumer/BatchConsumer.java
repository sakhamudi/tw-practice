package com.example.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchConsumer {

    @KafkaListener(topics = "demo-topic", containerFactory = "batchFactory", groupId = "group-batch")
    public void listenBatch(List<String> messages) {
        System.out.println("[batch] Received batch size: " + messages.size());
        messages.forEach(m -> System.out.println("  -> " + m));
    }
}
