# spring-kafka-example

The project demonstrates the usage of Apache Kafka with Spring i.e. Spring Kafka project

## Tech Stack

Spring Kafka, Spring Boot, Maven, Docker

## Description

The project contains two simple microservices used as a Kafka producer and a Kafka consumer, and they are named
accordingly. The producer produces and sends a few object messages (class CustomMessage) and the consumer
prints them. Kafka broker has one topic called "springkafka" and one partition.  
A dockerized Apache Kafka environment can be started via docker-compose.yml.
