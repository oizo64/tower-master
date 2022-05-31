package ppl.pl.tower.domain.DTO;

import lombok.Data;
import ppl.pl.tower.domain.Model.EngineType;
import ppl.pl.tower.domain.Model.Code;
@Data
public class AircraftDTO {
//    private Long id;
//    private Code code;
    private String modelName;
    private String manufacturer;
    private EngineType engineType;
}
