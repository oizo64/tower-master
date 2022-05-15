package ppl.pl.tower.domain.Mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ppl.pl.tower.domain.DTO.AircraftDTO;
import ppl.pl.tower.domain.DTO.CodeDTO;
import ppl.pl.tower.domain.Model.Aircraft;
import ppl.pl.tower.domain.Model.Code;

@Mapper(componentModel = "spring")
public interface CodeMapper {
    Code mapToCode(CodeDTO codeDTO);

    @InheritInverseConfiguration(name = "mapToCode")
    CodeDTO mmapToCodeDTO(Code code);
}
