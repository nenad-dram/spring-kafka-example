# spring-kafka-example

The project demonstrates the usage of Apache Kafka with Spring i.e. Spring Kafka project

## Tech Stack

Spring Kafka, Spring Boot, Kafka Streams, Maven, Docker

## Description

The project contains three simple microservices used as a Kafka producer, a Kafka consumer, and a Kafka streams, and they are named
accordingly. The producer produces and sends a few object messages (class CustomMessage) and the consumer
prints them. The streams also consumes those messages, performs a small update and saves new messages within a new topic.  
Kafka broker has two topics, "springkafka" and "springkafka-streams", and one partition for each of them.  
A dockerized Apache Kafka environment can be started via docker-compose.yml.
