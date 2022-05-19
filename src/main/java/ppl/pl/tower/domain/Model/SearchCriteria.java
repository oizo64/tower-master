package ppl.pl.tower.domain.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchCriteria {
    private AircraftColumnName aircraftColumnName;
    private Object value;
    private SearchOperation operation;
}
