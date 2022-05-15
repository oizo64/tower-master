package ppl.pl.tower.domain.Services;

import org.springframework.stereotype.Service;
import ppl.pl.tower.domain.DTO.AircraftDTO;
import ppl.pl.tower.domain.Exceptions.AircraftNotFoundException;
import ppl.pl.tower.domain.Exceptions.StringToLongException;
import ppl.pl.tower.domain.Mapper.AircraftMapper;
import ppl.pl.tower.domain.Mapper.CodeMapper;
import ppl.pl.tower.domain.Model.*;
import ppl.pl.tower.domain.Repository.AircraftRepo;
import ppl.pl.tower.domain.Repository.CodeRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public AircraftDTO getAircraftById(Long id) {
        Optional<Aircraft> aircraft = aircraftRepo.findById(id);

        if (aircraft.isPresent()) {
            return aircraftMapper.mapToAircraftDTO(aircraft.get());
        } else {
            throw new AircraftNotFoundException("Aircraft: " + id + " not found");
        }
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
        Optional<Aircraft> aircraft = aircraftRepo.findById(id);
        if (aircraft.isPresent()) {
            aircraftRepo.deleteById(id);
        } else {
            throw new AircraftNotFoundException("Using remove " + id + " Aircraft not Found");
        }
    }

    public List<AircraftDTO> getAllAircraftSortedByModelName(String searchString, String orderBy) {
        return  aircraftRepo.searchAircraftByModelName(searchString, orderBy).stream().map(aircraft -> aircraftMapper.mapToAircraftDTO(aircraft)).collect(Collectors.toList());
    }
}
