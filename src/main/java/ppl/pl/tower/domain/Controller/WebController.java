package ppl.pl.tower.domain.Controller;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ppl.pl.tower.domain.DTO.AircraftDTO;
import ppl.pl.tower.domain.Exceptions.AircraftNotFoundException;
import ppl.pl.tower.domain.Model.Aircraft;
import ppl.pl.tower.domain.Model.AircraftAndCode;
import ppl.pl.tower.domain.Model.FiledNameAndSortDirection;
import ppl.pl.tower.domain.Services.AircraftService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/aircraft/")
public class WebController {
    private final AircraftService aircraftService;

    public WebController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }


    @GetMapping(value = "/v1/{id}")
    public AircraftDTO getAircraftById(@PathVariable("id") Long id) {
        return aircraftService.getAircraftById(id);
    }

    @GetMapping(value = "/v1/sorted-by-model-name/")
    public List<AircraftDTO> getAllAircraftSortedByModelName(@RequestParam String searchString, @RequestParam(defaultValue = "model_name") String orderBy) {
        return aircraftService.getAllAircraftSortedByModelName(searchString, orderBy);
    }

    @PostMapping(value = "/v1/")
    public void addAircraft(@RequestBody AircraftAndCode aircraftAndCode) {
        aircraftService.create(aircraftAndCode);
    }

    @PutMapping(value = "/v1/{id}")
    public void updateCarById(@PathVariable("id") Long id, @RequestBody AircraftAndCode aircraftAndCode) {
        aircraftService.update(aircraftAndCode, id);
    }

    @DeleteMapping(value = "/v1/{id}")
    public void removeCarById(@PathVariable("id") Long id) {
        aircraftService.remove(id);
    }
}
