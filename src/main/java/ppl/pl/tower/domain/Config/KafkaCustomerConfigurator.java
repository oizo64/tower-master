package ppl.pl.tower.domain.Config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ppl.pl.tower.domain.Model.AircraftAndCode;
import ppl.pl.tower.domain.Serialization.AircraftAndCodeDeserializer;
import ppl.pl.tower.domain.Serialization.AircraftAndCodeSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Configuration
public class KafkaCustomerConfigurator {
    @Value("${kafka.bootstrap.servers}")
    private String bootstrapString;

    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapString);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AircraftAndCodeDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, AircraftAndCode> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

//    @Bean
//    public KafkaListenerContainerFactory<
//            ConcurrentMessageListenerContainer<String, AircraftAndCode>> factory() {
//        ConcurrentKafkaListenerContainerFactory<String, AircraftAndCode> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AircraftAndCode> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AircraftAndCode> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
