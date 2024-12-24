package mk.frizer.repository;

import mk.frizer.domain.BusinessOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessOwnerRepository extends JpaRepository<BusinessOwner, Long> {
    Optional<BusinessOwner> findByBaseUserId(Long baseUserId);
}
