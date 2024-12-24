package mk.frizer.domain.events;

import lombok.Getter;
import mk.frizer.domain.Review;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class ReviewEditedEvent extends ApplicationEvent {
    private final LocalDateTime when;
    private final Double oldRating;

    public ReviewEditedEvent(Review source, Double oldRating) {
        super(source);
        this.when = LocalDateTime.now();
        this.oldRating = oldRating;
    }

    public ReviewEditedEvent(Review source, Double oldRating, LocalDateTime when) {
        super(source);
        this.when = when;
        this.oldRating = oldRating;
    }
}
