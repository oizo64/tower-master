package ppl.pl.tower.domain.Serialization;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import ppl.pl.tower.domain.Model.AircraftAndCode;
import java.util.Map;

public class AircraftAndCodeSerializer implements Serializer<AircraftAndCode> {
    @Override
    public byte[] serialize(String s, AircraftAndCode aircraftAndCode) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            retVal = objectMapper.writeValueAsString(aircraftAndCode).getBytes();
        }
        catch (Exception e){ e.printStackTrace();}
        return retVal;
    }
}
