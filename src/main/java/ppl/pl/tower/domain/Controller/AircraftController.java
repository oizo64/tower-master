package ppl.pl.tower.domain.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ppl.pl.tower.domain.DTO.AircraftDTO;
import ppl.pl.tower.domain.Model.Aircraft;
import ppl.pl.tower.domain.Model.AircraftAndCode;
import ppl.pl.tower.domain.Model.AircraftColumnName;
import ppl.pl.tower.domain.Model.SearchOperation;
import ppl.pl.tower.domain.Services.AircraftService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/aircraft/")
public class AircraftController {
    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }


    @GetMapping(value = "/{id}")
    public AircraftDTO getAircraftById(@PathVariable("id") Long id) {
        return aircraftService.getAircraftById(id);
    }

    // dodac pagowanie
    @GetMapping(value = "/sorted-by-model-name/")
    public List<AircraftDTO> getAllAircraftSortedByModelName(@RequestParam AircraftColumnName aircraftColumnName,
                                                             @RequestParam String searchString,
                                                             @RequestParam SearchOperation searchOperation,
                                                             @RequestParam Sort.Direction direction,
                                                             @RequestParam AircraftColumnName sortBy,
                                                             @RequestParam("page") int page,
                                                             @RequestParam("size") int size) {
        return aircraftService.getAllAircraftSorted(aircraftColumnName, searchString, searchOperation, direction, sortBy, page, size);
    }

    @PostMapping(value = "/")
    public void addAircraft(@RequestBody AircraftAndCode aircraftAndCode) {
        aircraftService.create(aircraftAndCode);
    }

    @PutMapping(value = "/{id}")
    public void updateCarById(@PathVariable("id") Long id, @RequestBody AircraftAndCode aircraftAndCode) {
        aircraftService.update(aircraftAndCode, id);
    }

    @DeleteMapping(value = "/{id}")
    public void removeCarById(@PathVariable("id") Long id) {
        aircraftService.remove(id);
    }

    @PostMapping(value = "/kafka")
    public void addAircraftByKafka(@RequestBody AircraftAndCode aircraftAndCode) {
        aircraftService.createByKafka(aircraftAndCode);
    }


}