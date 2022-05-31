package ppl.pl.tower.domain.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfigurator {

    @Bean
    public NewTopic newTopic(){
        return TopicBuilder.name("AircraftTopic").build();
    }
}
