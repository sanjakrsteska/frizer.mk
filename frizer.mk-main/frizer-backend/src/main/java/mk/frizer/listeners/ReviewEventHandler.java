package mk.frizer.listeners;

import mk.frizer.domain.Review;
import mk.frizer.domain.Salon;
import mk.frizer.domain.events.ReviewCreatedEvent;
import mk.frizer.domain.events.ReviewDeletedEvent;
import mk.frizer.domain.events.ReviewEditedEvent;
import mk.frizer.service.EmployeeService;
import mk.frizer.service.SalonService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ReviewEventHandler {
    private final SalonService salonService;
    private final EmployeeService employeeService;

    public ReviewEventHandler(SalonService salonService, EmployeeService employeeService) {
        this.salonService = salonService;
        this.employeeService = employeeService;
    }

    @EventListener
    public void onReviewCreated(ReviewCreatedEvent event) {
        Review review = (Review) event.getSource();
        salonService.addReview(review);
        employeeService.addReview(review);
    }

    @EventListener
    public void onReviewDeleted(ReviewDeletedEvent event) {
        Review review = (Review) event.getSource();
        salonService.deleteReview(review);
        employeeService.deleteReview(review);

    }

    @EventListener
    public void onReviewEdited(ReviewEditedEvent event){
        Review review = (Review) event.getSource();
        salonService.updateReview(review, event.getOldRating());
        employeeService.updateReview(review, event.getOldRating());
    }
}
