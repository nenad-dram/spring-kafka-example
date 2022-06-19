package com.endyary.kafkaconsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @KafkaListener(topics = "${kafka.topic}", groupId = "one")
    public void listenGroupOne(CustomMessage message) {
        System.out.println("Message received:" +
                " [name = " + message.getName() + ", " +
                "description = " + message.getDescription() + ", " +
                " createdBy = " + message.getCreatedBy() + "]");
    }
}
