package ppl.pl.tower.domain.Model;

import lombok.Data;
import ppl.pl.tower.domain.DTO.AircraftDTO;
import ppl.pl.tower.domain.DTO.CodeDTO;

@Data
public class AircraftAndCode {
    private AircraftDTO aircraftDTO;
    private CodeDTO codeDTO;
}
