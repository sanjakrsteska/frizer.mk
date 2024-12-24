package mk.frizer.domain.events;

import lombok.Getter;
import mk.frizer.domain.Review;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class ReviewDeletedEvent extends ApplicationEvent {
    private final LocalDateTime when;

    public ReviewDeletedEvent(Review source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public ReviewDeletedEvent(Review source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}