package ppl.pl.tower.domain.Mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ppl.pl.tower.domain.DTO.AircraftDTO;
import ppl.pl.tower.domain.Model.Aircraft;

@Mapper(componentModel = "spring")
public interface AircraftMapper {
    Aircraft mapToAircraft(AircraftDTO aircraftDTO);

    @InheritInverseConfiguration(name = "mapToAircraft")
    AircraftDTO mapToAircraftDTO(Aircraft aircraft);
}
