package mk.frizer.service.impl;

import mk.frizer.domain.City;
import mk.frizer.repository.CityRepository;
import mk.frizer.service.CityService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public List<City> getTopNCitiesBySalonsCount(Integer count) {
        Pageable pageable = PageRequest.of(0, count);
        return cityRepository.findTopNCitiesBySalonCount(pageable);
    }
}
