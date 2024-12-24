package mk.frizer.web.rest;

import mk.frizer.domain.AppointmentTimeSlot;
import mk.frizer.utilities.TimeSlotGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/time-slots", "/api/time-slot"})
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class TimeSlotRestController {
    private final TimeSlotGenerator timeSlotGenerator;

    public TimeSlotRestController(TimeSlotGenerator timeSlotGenerator) {
        this.timeSlotGenerator = timeSlotGenerator;
    }

    @GetMapping()
    public List<List<AppointmentTimeSlot>> getAvailableTimeSlots(@RequestParam Long salonId, @RequestParam Long employeeId, @RequestParam Integer durationMultiplier) {
        return timeSlotGenerator.generateAvailableTimeSlots(salonId, employeeId, durationMultiplier);
    }
}
