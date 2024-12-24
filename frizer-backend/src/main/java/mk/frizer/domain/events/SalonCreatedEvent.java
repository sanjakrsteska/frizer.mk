package mk.frizer.domain.events;

import lombok.Getter;
import mk.frizer.domain.Salon;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class SalonCreatedEvent extends ApplicationEvent {
    private final LocalDateTime when;

    public SalonCreatedEvent(Salon source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public SalonCreatedEvent(Salon source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}

