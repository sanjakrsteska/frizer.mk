package mk.frizer.service;

import mk.frizer.domain.City;

import java.util.List;

public interface CityService {
    public List<City> getCities();
    public List<City> getTopNCitiesBySalonsCount(Integer count);
}
