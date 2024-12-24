package mk.frizer.domain.events;

import lombok.Getter;
import mk.frizer.domain.Salon;
import mk.frizer.domain.Treatment;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class SalonUpdatedEvent extends ApplicationEvent {
    private final LocalDateTime when;

    public SalonUpdatedEvent(Salon source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public SalonUpdatedEvent(Salon source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}
