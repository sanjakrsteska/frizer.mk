package mk.frizer.web.rest;

import mk.frizer.domain.City;
import mk.frizer.domain.dto.simple.CitySimpleDTO;
import mk.frizer.service.CityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({ "/api/cities", "/api/city" })
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class CityRestController {
    private final CityService cityService;

    public CityRestController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/top")
    public List<CitySimpleDTO> topNCity(@RequestParam Integer count) {
        return cityService.getTopNCitiesBySalonsCount(count).stream().map(City::toDto).toList();
    }
}
