package ppl.pl.tower.domain.Config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ppl.pl.tower.domain.DTO.AircraftDTO;
import ppl.pl.tower.domain.Model.AircraftAndCode;
import ppl.pl.tower.domain.Serialization.AircraftAndCodeSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfigurator {
    @Value("${kafka.bootstrap.servers}")
    private String bootstrapString;

    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapString);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AircraftAndCodeSerializer.class);
        return props;
    }
    @Bean
    public ProducerFactory<String, AircraftAndCode> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }
    @Bean
    public KafkaTemplate<String, AircraftAndCode> aircraftAndCodeKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
