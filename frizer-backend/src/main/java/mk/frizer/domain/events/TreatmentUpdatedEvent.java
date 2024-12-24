package mk.frizer.domain.events;

import lombok.Getter;
import mk.frizer.domain.Treatment;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class TreatmentUpdatedEvent extends ApplicationEvent {
    private final LocalDateTime when;

    public TreatmentUpdatedEvent(Treatment source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public TreatmentUpdatedEvent(Treatment source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}
