package com.kafka.config;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.lang.Nullable;
@Component
public class KafkaLoggingProducerListener implements ProducerListener<String, String> {

    @Override
    public void onSuccess(ProducerRecord<String, String> producerRecord,
                          RecordMetadata recordMetadata) {
        System.out.println("✅ ACK from broker. Topic: " + recordMetadata.topic()
                + ", Partition: " + recordMetadata.partition()
                + ", Offset: " + recordMetadata.offset()
                + ", Key: " + producerRecord.key()
                + ", Value: " + producerRecord.value());
    }

    @Override
    public void onError(ProducerRecord<String, String> producerRecord,
                        @Nullable RecordMetadata recordMetadata,
                        Exception exception) {
        System.err.println("❌ Failed to send message to topic " + producerRecord.topic()
                + " due to: " + exception.getMessage());
    }
}
