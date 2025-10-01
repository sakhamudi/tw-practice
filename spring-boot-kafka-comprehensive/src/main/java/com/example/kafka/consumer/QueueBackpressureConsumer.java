package com.example.kafka.consumer;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class QueueBackpressureConsumer {

    private final BlockingQueue<RecordWithAck> queue =
            new LinkedBlockingQueue<>(10); // max 10 messages in queue

    @KafkaListener(
            topics = "manual-demo-topic",
            groupId = "group-manual",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(ConsumerRecord<String, String> record, Acknowledgment ack) throws InterruptedException {
        // wrap record + ack and put into queue
        queue.put(new RecordWithAck(record, ack));
    }

    @PostConstruct
    public void startWorker() {
        Thread worker = new Thread(() -> {
            while (true) {
                try {
                    RecordWithAck wrapper = queue.take();
                    process(wrapper);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        worker.setDaemon(true);
        worker.start();
    }

    private void process(RecordWithAck wrapper) {
        try {
            ConsumerRecord<String, String> record = wrapper.getRecord();
            Acknowledgment ack = wrapper.getAck();

            System.out.printf("[queue] Partition=%d, Offset=%d, Payload=%s%n",
                    record.partition(), record.offset(), record.value());

            // simulate processing delay
            Thread.sleep(2000);

            // manual ack after processing
            if (ack != null) {
                ack.acknowledge();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
