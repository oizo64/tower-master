package ppl.pl.tower.domain.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ppl.pl.tower.domain.DTO.AircraftDTO;
import ppl.pl.tower.domain.Exceptions.AircraftNotFoundException;
import ppl.pl.tower.domain.Exceptions.StringToLongException;
import ppl.pl.tower.domain.Mapper.AircraftMapper;
import ppl.pl.tower.domain.Model.*;
import ppl.pl.tower.domain.Mapper.CodeMapper;
import ppl.pl.tower.domain.Repository.AircraftRepo;
import ppl.pl.tower.domain.Repository.CodeRepo;
import ppl.pl.tower.domain.Specification.AircraftSpecification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AircraftService {
    private final AircraftRepo aircraftRepo;
    private final AircraftMapper aircraftMapper;
    private KafkaTemplate<String, AircraftAndCode> kafkaTemplate;
    private final CodeRepo codeRepo;
    private final CodeMapper codeMapper;


    public AircraftService(AircraftRepo aircraftRepo, AircraftMapper aircraftMapper, KafkaTemplate<String, AircraftAndCode> kafkaTemplate, CodeRepo codeRepo, CodeMapper codeMapper) {
        this.aircraftRepo = aircraftRepo;
        this.aircraftMapper = aircraftMapper;
        this.kafkaTemplate = kafkaTemplate;
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
            Code code = codeMapper.mapToCode(aircraftAndCode.getCodeDTO());
            Aircraft aircraft = aircraftMapper.mapToAircraft(aircraftAndCode.getAircraftDTO());
            aircraft.setCode(code);
            aircraftRepo.save(aircraft);
        }
    }

    private boolean checkLengthOfString(AircraftAndCode aircraftAndCode) {
        int columnLength = 255;
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
        Aircraft aircraft = aircraftRepo.findById(id).orElseThrow(() -> new AircraftNotFoundException("Using remove " + id + " Aircraft not Found"));
        if (aircraft.getId() != null && checkLengthOfString(aircraftAndCode)) {
            if (aircraftAndCode.getAircraftDTO().getModelName() != null) {
                aircraft.setModelName(aircraftAndCode.getAircraftDTO().getModelName());
            }
            if (aircraftAndCode.getAircraftDTO().getManufacturer() != null) {
                aircraft.setManufacturer(aircraftAndCode.getAircraftDTO().getManufacturer());
            }
            if (aircraftAndCode.getAircraftDTO().getEngineType() != null) {
                aircraft.setEngineType(aircraftAndCode.getAircraftDTO().getEngineType());
            }
            if (aircraftAndCode.getCodeDTO().getIata() != null) {
                aircraft.getCode().setIata(aircraftAndCode.getCodeDTO().getIata());
            }
            if (aircraftAndCode.getCodeDTO().getIcao() != null) {
                aircraft.getCode().setIcao(aircraftAndCode.getCodeDTO().getIcao());
            }
            aircraftRepo.save(aircraft);
        }
    }

    public void remove(Long id) {
        aircraftRepo.findById(id).orElseThrow(() -> new AircraftNotFoundException("Using remove " + id + " Aircraft not Found"));
        aircraftRepo.deleteById(id);
    }

    public List<AircraftDTO> getAllAircraftSorted(AircraftColumnName aircraftColumnName, String searchString, SearchOperation searchOperation, Sort.Direction direction, AircraftColumnName sortBy, int page, int size) {
        AircraftSpecification aircraftSpecification = new AircraftSpecification();
        Pageable pageable = PageRequest.of(page,size);
        aircraftSpecification.add(new SearchCriteria(aircraftColumnName, searchString, searchOperation));
        return aircraftRepo.findAll(aircraftSpecification, Sort.by(direction, sortBy.label))
                .stream().map(aircraft -> aircraftMapper.mapToAircraftDTO(aircraft))
                .collect(Collectors.toList());
    }

    public void createByKafka(AircraftAndCode aircraftAndCode) {
        kafkaTemplate.send("AircraftTopic",aircraftAndCode);
    }

    public void writeFromKafka(String data) {
        ObjectMapper mapper = new ObjectMapper();


    }
}
