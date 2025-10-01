package com.example.kafka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api/messages")
public class KafkaProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.topics.main}")
    private String mainTopic;

    public KafkaProducerController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // send normal message
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestParam(required = false) String key,
                                       @RequestBody String payload) {
        kafkaTemplate.send(mainTopic, key, payload)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.printf("✅ Sent message=[%s] with offset=[%d], partition=[%d]%n",
                                payload,
                                result.getRecordMetadata().offset(),
                                result.getRecordMetadata().partition());
                    } else {
                        System.err.printf("❌ Unable to send message=[%s] due to : %s%n", payload, ex.getMessage());
                    }
                });
        return ResponseEntity.ok("sent");
    }

    // send a message that will cause consumer to fail (for testing DLT)
    @PostMapping("/send/fail")
    public ResponseEntity<String> sendFailing(@RequestParam(required = false) String key,
                                              @RequestBody String payload) {
        // we send to demo-retry so the retry consumer will pick it and throw
        kafkaTemplate.send("demo-retry", key, payload);
        return ResponseEntity.ok("sent-to-retry");
    }

    @PostMapping("/send/manual")
    public ResponseEntity<String> sendManual(@RequestParam(required = false) String key,
                                              @RequestBody String payload) {
        // we send to demo-retry so the retry consumer will pick it and throw
        kafkaTemplate.send("manual-demo-topic", key, payload);
        return ResponseEntity.ok("sent-to-retry");
    }
}
