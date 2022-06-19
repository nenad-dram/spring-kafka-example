package com.endyary.kafkaproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class ProducerService {

    @Autowired
    public KafkaTemplate<String, CustomMessage> kafkaTemplate;

    @Value("${kafka.topic}")
    public String topicName;

    public void sendMessages() {
        for (int i = 0; i < 5; i++) {

            CustomMessage message = new CustomMessage
                    ("Name " + i, "Description " + i, "user " + i / 2);

            ListenableFuture<SendResult<String, CustomMessage>> future =
                    kafkaTemplate.send(topicName, message);

            future.addCallback(new ListenableFutureCallback<>() {

                @Override
                public void onSuccess(SendResult<String, CustomMessage> result) {
                    System.out.println("Message sent: " + message.getName() +
                            " (offset: " + result.getRecordMetadata().offset() + ")");
                }

                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("Error while sending the message: "
                            + message + " -> " + ex.getMessage());
                }
            });
        }
    }
}
