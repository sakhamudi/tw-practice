package com.example.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ManualAckConsumer {

    //ManualAckConsumer gives you explicit control over when a Kafka message is marked as processed,
    //which is very useful for exactly-once or at-least-once guarantees in real-world systems.

    //@KafkaListener(topics = "manual-demo-topic", groupId = "group-manual", containerFactory = "kafkaListenerContainerFactory")
    public void listenManual(ConsumerRecord<String, String> record, Acknowledgment ack) {
        System.out.printf("[manual] Partition: %d, Offset: %d, Key: %s, Payload: %s%n",
                record.partition(), record.offset(), record.key(), record.value());
        // simulate processing
        // ack only when processing complete
        ack.acknowledge();
    }
}
