package ppl.pl.tower.domain.Services;

import org.springframework.stereotype.Service;
import ppl.pl.tower.domain.Exceptions.AircraftNotFoundException;
import ppl.pl.tower.domain.Exceptions.StringToLongException;
import ppl.pl.tower.domain.Mapper.AircraftMapper;
import ppl.pl.tower.domain.Mapper.CodeMapper;
import ppl.pl.tower.domain.Model.Aircraft;
import ppl.pl.tower.domain.Model.AircraftAndCode;
import ppl.pl.tower.domain.Model.Code;
import ppl.pl.tower.domain.Model.EngineType;
import ppl.pl.tower.domain.Repository.AircraftRepo;
import ppl.pl.tower.domain.Repository.CodeRepo;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Optional;

@Service
public class AircraftService {
    private final AircraftRepo aircraftRepo;
    private final AircraftMapper aircraftMapper;
    private final CodeRepo codeRepo;
    private final CodeMapper codeMapper;

    public AircraftService(AircraftRepo aircraftRepo, AircraftMapper aircraftMapper, CodeRepo codeRepo, CodeMapper codeMapper) {
        this.aircraftRepo = aircraftRepo;
        this.aircraftMapper = aircraftMapper;
        this.codeRepo = codeRepo;
        this.codeMapper = codeMapper;
    }

    public Optional<Aircraft> getAircraftById(Long id) {
        return aircraftRepo.findById(id);
    }

    public void create(AircraftAndCode aircraftAndCode) {
        if (checkLengthOfString(aircraftAndCode)) {
            Aircraft aircraft = aircraftMapper.mapToAircraft(aircraftAndCode.getAircraftDTO());
            aircraftRepo.save(aircraft);
        }

    }

    private boolean checkLengthOfString(AircraftAndCode aircraftAndCode) {
        Integer columnLength = 255;
        if (aircraftAndCode.getAircraftDTO().getModelName().length() > columnLength ||
                aircraftAndCode.getAircraftDTO().getManufacturer().length() > columnLength ||
                aircraftAndCode.getAircraftDTO().getEngineType().toString().length() > columnLength ||
                aircraftAndCode.getCodeDTO().getIcao().length() > columnLength ||
                aircraftAndCode.getCodeDTO().getIata().length() > columnLength) {
            throw new StringToLongException("Input string to long");
        } else {
            return true;
        }

    }

    public void update(AircraftAndCode aircraftAndCode, Long id) {
        if (checkLengthOfString(aircraftAndCode)) {
            Optional<Aircraft> aircraft = aircraftRepo.findById(id);
            if (aircraft.isPresent()) {
                if (aircraftAndCode.getAircraftDTO().getModelName() != null) {
                    aircraft.get().setModelName(aircraftAndCode.getAircraftDTO().getModelName());
                }
                if (aircraftAndCode.getAircraftDTO().getManufacturer() != null) {
                    aircraft.get().setManufacturer(aircraftAndCode.getAircraftDTO().getManufacturer());
                }
                if (aircraftAndCode.getAircraftDTO().getEngineType() != null) {
                    aircraft.get().setEngineType(aircraftAndCode.getAircraftDTO().getEngineType());
                }
                if (aircraftAndCode.getCodeDTO().getIata() != null) {
                    aircraft.get().getCode().setIata(aircraftAndCode.getCodeDTO().getIata());
                }
                if (aircraftAndCode.getCodeDTO().getIcao() != null) {
                    aircraft.get().getCode().setIcao(aircraftAndCode.getCodeDTO().getIcao());
                }
                aircraftRepo.save(aircraft.get());
            } else {
                throw new AircraftNotFoundException("Using update " + id + " Aircraft not Found");
            }

        }
    }

    public void remove(Long id) {
        aircraftRepo.deleteById(id);
    }
}
