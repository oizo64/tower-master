package ppl.pl.tower.domain.Listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ppl.pl.tower.domain.Model.AircraftAndCode;
import ppl.pl.tower.domain.Services.AircraftService;


@Component
public class KafkaAircraftAndCodeListener {
    private final AircraftService aircraftService;

    public KafkaAircraftAndCodeListener(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    //For more then one kafka listener groupId must be unique!
    @KafkaListener(topics = "AircraftTopic", groupId = "groupId")
    void listener(AircraftAndCode aircraftAndCode) {
        aircraftService.create(aircraftAndCode);
    }
}
