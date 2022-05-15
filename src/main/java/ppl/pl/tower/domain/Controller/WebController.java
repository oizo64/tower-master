package ppl.pl.tower.domain.Controller;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ppl.pl.tower.domain.Exceptions.AircraftNotFoundException;
import ppl.pl.tower.domain.Model.Aircraft;
import ppl.pl.tower.domain.Model.AircraftAndCode;
import ppl.pl.tower.domain.Model.FiledNameAndSortDirection;
import ppl.pl.tower.domain.Services.AircraftService;

import java.util.List;
import java.util.Optional;

@RestController
public class WebController {
    private final AircraftService aircraftService;

    public WebController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }


    @GetMapping(value = "/api/aircraft/{id}")
    public Optional<Aircraft> getAircraftById(@PathVariable("id") Long id) {
        return Optional.ofNullable(aircraftService.getAircraftById(id).orElseThrow(() -> new AircraftNotFoundException(id + " Aircraft not found.")));
    }
    @GetMapping(value = "/api/aircrafts/sorted-by-model-name/")
    public List<Aircraft> getAllAircraftSortedByModelName(@RequestParam String searchString, @RequestParam(defaultValue = "model_name") String orderBy ) {
        return aircraftService.getAllAircraftSortedByModelName(searchString, orderBy);
    }
    @PostMapping(value = "/api/aircraft/")
    public void addAircraft(@RequestBody AircraftAndCode aircraftAndCode) {
        aircraftService.create(aircraftAndCode);
    }

    @PutMapping(value = "/api/aircraft/{id}")
    public void updateCarById(@PathVariable("id") Long id, @RequestBody AircraftAndCode aircraftAndCode) {
        aircraftService.update(aircraftAndCode, id);
    }

    @DeleteMapping(value = "/api/aircraft/{id}")
    public void removeCarById(@PathVariable("id") Long id) {
        if (aircraftService.getAircraftById(id).isPresent()) {
            aircraftService.remove(id);
        } else {
            throw new AircraftNotFoundException(id + " is not found, it can be deleted");
        }
    }
}
