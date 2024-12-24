package mk.frizer.repository;

import mk.frizer.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.author LEFT JOIN FETCH r.employee WHERE r.id IN :ids")
    List<Review> findByIdsWithAssociations(@Param("ids") List<Long> ids);
    List<Review> findByEmployee_Salon_Id(Long id);
}
