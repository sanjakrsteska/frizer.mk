package mk.frizer.repository;

import mk.frizer.domain.City;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, String> {
    Optional<City> findByName(String name);
    Optional<City> findByNameEqualsIgnoreCase(String name);
    @Query("SELECT c FROM City c JOIN c.salonsInCity s GROUP BY c.name ORDER BY COUNT(s) DESC")
    List<City> findTopNCitiesBySalonCount(Pageable pageable);
}
