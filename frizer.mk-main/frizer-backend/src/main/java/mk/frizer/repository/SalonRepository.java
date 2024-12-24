package mk.frizer.repository;

import mk.frizer.domain.Salon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalonRepository extends JpaRepository<Salon, Long> {
        List<Salon> findAllByNameContaining(String name);
        List<Salon> findAllByRatingGreaterThanEqual(Float rating);
        List<Salon> findAllByLocationContaining(String location);
        List<Salon> findAllByOrderByRatingDesc(Pageable pageable);
        List<Salon> findAllByOwnerId(Long id);
}
