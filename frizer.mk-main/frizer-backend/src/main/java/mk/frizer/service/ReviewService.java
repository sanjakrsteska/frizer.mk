package mk.frizer.service;

import mk.frizer.domain.*;
import mk.frizer.domain.dto.ReviewAddDTO;
import mk.frizer.domain.dto.ReviewUpdateDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReviewService {
    List<Review> getReviews();
    List<Review> getReviewsBySalon(Long id);
    List<Review> getReviewsById(List<Long> ids);

    Optional<Review> getReviewById(Long id);

    Optional<Review> createReviewForEmployee(ReviewAddDTO reviewAddDTO);

//    Optional<Review> createReviewForCustomer(ReviewAddDTO reviewAddDTO);

    Optional<Review> updateReview(Long id, ReviewUpdateDTO reviewUpdateDTO);

    Optional<Review> deleteReviewById(Long id);
}
