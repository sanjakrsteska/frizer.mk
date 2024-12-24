package mk.frizer.service.impl;

import jakarta.transaction.Transactional;
import mk.frizer.domain.*;
import mk.frizer.domain.dto.ReviewAddDTO;
import mk.frizer.domain.dto.ReviewUpdateDTO;
import mk.frizer.domain.events.ReviewCreatedEvent;
import mk.frizer.domain.events.ReviewDeletedEvent;
import mk.frizer.domain.events.ReviewEditedEvent;
import mk.frizer.domain.events.SalonCreatedEvent;
import mk.frizer.domain.exceptions.ReviewNotFoundException;
import mk.frizer.domain.exceptions.UserNotFoundException;
import mk.frizer.repository.BaseUserRepository;
import mk.frizer.repository.CustomerRepository;
import mk.frizer.repository.EmployeeRepository;
import mk.frizer.repository.ReviewRepository;
import mk.frizer.service.ReviewService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final BaseUserRepository baseUserRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ReviewServiceImpl(ReviewRepository reviewRepository, BaseUserRepository baseUserRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.reviewRepository = reviewRepository;
        this.baseUserRepository = baseUserRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getReviewsBySalon(Long id) {
        return reviewRepository.findByEmployee_Salon_Id(id);
    }

    @Override
    public List<Review> getReviewsById(List<Long> ids) {
        return reviewRepository.findByIdsWithAssociations(ids);
    }

    @Override
    public Optional<Review> getReviewById(Long id) {
        Review user = reviewRepository.findById(id)
                .orElseThrow(ReviewNotFoundException::new);
        return Optional.of(user);
    }

//    @Override
//    @Transactional
//    public Optional<Review> createReviewForCustomer(ReviewAddDTO reviewAddDTO) {
//        Employee employee = employeeRepository.findById(reviewAddDTO.getEmployeeId())
//                .orElseThrow(UserNotFoundException::new);
//        Customer customer = customerRepository.findById(reviewAddDTO.getCustomerId())
//                .orElseThrow(UserNotFoundException::new);
//
//        Review user = new Review(employee.getBaseUser(), customer.getBaseUser(), reviewAddDTO.getRating(), reviewAddDTO.getComment());
//        return Optional.of(reviewRepository.save(user));
//    }

    @Override
    @Transactional
    public Optional<Review> createReviewForEmployee(ReviewAddDTO reviewAddDTO) {
        Employee employee = employeeRepository.findById(reviewAddDTO.getEmployeeId())
                .orElseThrow(UserNotFoundException::new);
        Customer customer = customerRepository.findById(reviewAddDTO.getCustomerId())
                .orElseThrow(UserNotFoundException::new);

        Review review = reviewRepository.save(new Review(customer.getBaseUser(), employee, reviewAddDTO.getRating(), reviewAddDTO.getComment()));

        applicationEventPublisher.publishEvent(new ReviewCreatedEvent(review));

        return Optional.of(review);
    }

    @Override
    @Transactional
    public Optional<Review> updateReview(Long id, ReviewUpdateDTO reviewUpdateDTO) {
        Review review = getReviewById(id).get();
        double oldRating = review.getRating();

        review.setRating(reviewUpdateDTO.getRating());
        review.setComment(reviewUpdateDTO.getComment());
        review.setDate(LocalDateTime.now());

        review = reviewRepository.save(review);

        applicationEventPublisher.publishEvent(new ReviewEditedEvent(review, oldRating));

        return Optional.of(review);
    }

    @Override
    @Transactional
    public Optional<Review> deleteReviewById(Long id) {
        Review review = getReviewById(id).get();
        reviewRepository.deleteById(id);
        applicationEventPublisher.publishEvent(new ReviewDeletedEvent(review));
        return Optional.of(review);
    }
}
