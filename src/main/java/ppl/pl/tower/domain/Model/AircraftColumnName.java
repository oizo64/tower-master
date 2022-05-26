package ppl.pl.tower.domain.Model;

public enum AircraftColumnName {
    ID("id"), MODEL_NAME("modelName"), MANUFACTURER("manufacturer"), ENGINE_TYPE("engineType"), IATA("iata"), ICAO("icao"),
    ;
    public final String label;
    AircraftColumnName(String label) {
        this.label = label;
    }
}
