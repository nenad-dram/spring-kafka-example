package com.endyary.kafkastreams;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamsConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration streamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public Serde<CustomMessage> messageSerde() {
        JsonDeserializer<CustomMessage> jsonDeserializer = new JsonDeserializer<>();
        Map<String, Object> deserProps = new HashMap<>();
        deserProps.put(JsonDeserializer.TYPE_MAPPINGS, "customMessage:com.endyary.kafkastreams.CustomMessage");
        jsonDeserializer.configure(deserProps, false);

        return Serdes.serdeFrom(new JsonSerializer<>(), jsonDeserializer);
    }

    @Bean
    public KStream<String, CustomMessage> kStream(StreamsBuilder builder) {
        KStream<String, CustomMessage> stream = builder.stream("springkafka", Consumed.with(Serdes.String(), messageSerde()));

        KStream<String, CustomMessage> messages = stream.map((key, message) ->
                new KeyValue<>(key, transformMessage(message)));

        messages.to("springkafka-streams", Produced.with(Serdes.String(), messageSerde()));

        return stream;
    }

    private CustomMessage transformMessage(CustomMessage message) {
        message.setName(message.getName() + " updated");
        message.setDescription(message.getDescription() + " updated");
        return message;
    }
}
