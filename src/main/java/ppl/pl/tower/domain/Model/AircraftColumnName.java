package ppl.pl.tower.domain.Model;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

public enum AircraftColumnName {
    ID, MODEL_NAME, MANUFACTURER
//    , ENGINE_TYPE, CODE_IATA, CODE_ICAO,
}
