package mk.frizer.domain.events;

import lombok.Getter;
import mk.frizer.domain.Review;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class ReviewCreatedEvent extends ApplicationEvent {
    private final LocalDateTime when;

    public ReviewCreatedEvent(Review source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public ReviewCreatedEvent(Review source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}