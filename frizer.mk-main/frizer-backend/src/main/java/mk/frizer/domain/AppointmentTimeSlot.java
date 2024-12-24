package mk.frizer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
public class AppointmentTimeSlot {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    public LocalDateTime from;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    public LocalDateTime to;

    public AppointmentTimeSlot(LocalDateTime from, LocalDateTime to) {
        if (!isDivisibleBy20Minutes(from) || !isDivisibleBy20Minutes(to)) {
            throw new IllegalArgumentException("Appointment times must be divisible by 20 minutes.");
        }
        this.from = from.truncatedTo(ChronoUnit.CENTURIES.MINUTES);
        this.to = to.truncatedTo(ChronoUnit.MINUTES);
    }

    private boolean isDivisibleBy20Minutes(LocalDateTime dateTime) {
        int minutes = dateTime.getMinute();
        return minutes % 20 == 0;
    }
}
