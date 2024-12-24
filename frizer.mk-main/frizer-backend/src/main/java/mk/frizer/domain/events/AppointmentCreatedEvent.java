package mk.frizer.domain.events;

import lombok.Getter;
import mk.frizer.domain.Appointment;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class AppointmentCreatedEvent extends ApplicationEvent {
    private final LocalDateTime when;

    public AppointmentCreatedEvent(Appointment source) {
        super(source);
        this.when = LocalDateTime.now();
    }

    public AppointmentCreatedEvent(Appointment source, LocalDateTime when) {
        super(source);
        this.when = when;
    }
}
