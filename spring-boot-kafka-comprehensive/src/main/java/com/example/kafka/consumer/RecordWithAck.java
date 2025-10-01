package com.example.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

public class RecordWithAck {
    private final ConsumerRecord<String, String> record;
    private final Acknowledgment ack;

    public RecordWithAck(ConsumerRecord<String, String> record, Acknowledgment ack) {
        this.record = record;
        this.ack = ack;
    }

    public ConsumerRecord<String, String> getRecord() {
        return record;
    }

    public Acknowledgment getAck() {
        return ack;
    }
}
