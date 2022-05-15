package ppl.pl.tower.domain.Services;

import lombok.Data;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ppl.pl.tower.domain.Model.Aircraft;
import ppl.pl.tower.domain.Model.Code;
import ppl.pl.tower.domain.Model.EngineType;
import ppl.pl.tower.domain.Repository.AircraftRepo;
import org.junit.jupiter.api.*;
import ppl.pl.tower.domain.Repository.CodeRepo;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AircraftServiceTest {

    @Autowired
    private AircraftRepo aircraftRepo;

    @Autowired
    private CodeRepo codeRepo;

    private Long airId;
    @Test
    @Order(1)
    void createUpdateRemove() {
        Aircraft aircraft = new Aircraft();
        Code code = new Code();
        code.setIcao("testIcao");
        code.setIata("testIata");
        aircraft.setManufacturer("testManufacturer");
        aircraft.setModelName("testModelName");
        aircraft.setEngineType(EngineType.JET);
        aircraft.setCode(code);
        aircraftRepo.save(aircraft);
        airId = aircraft.getId();
        Optional<Aircraft> aircraftGet = aircraftRepo.findById(airId);
        assertThat(aircraftGet).isNotNull();
        assertThat(aircraftGet.get().getEngineType()).isEqualByComparingTo(EngineType.JET);
        assertThat(aircraftGet.get().getManufacturer()).isEqualTo("testManufacturer");
        assertThat(aircraftGet.get().getModelName()).isEqualTo("testModelName");
        assertThat(aircraftGet.get().getCode().getIata()).isEqualTo("testIata");
        assertThat(aircraftGet.get().getCode().getIcao()).isEqualTo("testIcao");

        //Update

        Optional<Aircraft> aircraftUpdateFirst = aircraftRepo.findById(airId);
        Code codeUpdate = new Code();
        codeUpdate.setIcao("Icao");
        codeUpdate.setIata("Iata");
        codeRepo.save(code);
        aircraftUpdateFirst.get().setManufacturer("Manufacturer");
        aircraftUpdateFirst.get().setModelName("ModelName");
        aircraftUpdateFirst.get().setEngineType(EngineType.TURBOPROP);
        aircraftUpdateFirst.get().setCode(codeUpdate);
        aircraftRepo.save(aircraftUpdateFirst.get());
        Optional<Aircraft>aircraftUpdateSecond = aircraftRepo.findById(airId);
        assertThat(aircraftUpdateSecond).isNotNull();
        assertThat(aircraftUpdateSecond.get().getEngineType()).isEqualByComparingTo(EngineType.TURBOPROP);
        assertThat(aircraftUpdateSecond.get().getManufacturer()).isEqualTo("Manufacturer");
        assertThat(aircraftUpdateSecond.get().getModelName()).isEqualTo("ModelName");
        assertThat(aircraftUpdateSecond.get().getCode().getIata()).isEqualTo("Iata");
        assertThat(aircraftUpdateSecond.get().getCode().getIcao()).isEqualTo("Icao");

        // Remove
        Optional<Aircraft> aircraftRemoveFirst = aircraftRepo.findById(airId);
        aircraftRepo.deleteById(aircraftRemoveFirst.get().getId());
        Optional<Aircraft> aircraftRemoveSecond = aircraftRepo.findByManufacturer("testManufacturer");
        assertThat(aircraftRemoveSecond.isEmpty()).isTrue();
        assertThat(aircraftRemoveSecond.isPresent()).isFalse();
    }
}