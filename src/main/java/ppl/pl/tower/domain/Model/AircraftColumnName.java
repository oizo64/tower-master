package ppl.pl.tower.domain.Model;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

public enum AircraftColumnName {
    ID("id"), MODEL_NAME("modelName"), MANUFACTURER("manufacturer"), ENGINE_TYPE("engineType"), IATA("iata"), ICAO("icao"),
    ;
    public final String label;
    AircraftColumnName(String label) {
        this.label = label;
    }
}
