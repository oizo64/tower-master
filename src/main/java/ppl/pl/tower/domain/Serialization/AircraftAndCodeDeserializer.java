package ppl.pl.tower.domain.Serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import ppl.pl.tower.domain.Model.AircraftAndCode;


public class AircraftAndCodeDeserializer implements Deserializer<AircraftAndCode> {
    @Override
    public AircraftAndCode deserialize(String s, byte[] bytes) {
        ObjectMapper objectMapper = new ObjectMapper();
        AircraftAndCode aircraftAndCode = null;
        try{
            aircraftAndCode = objectMapper.readValue(bytes,AircraftAndCode.class);

        } catch (Exception e){e.printStackTrace();}
        return aircraftAndCode;
    }

}
