package mk.frizer.web.rest;

import jakarta.mail.MessagingException;
import mk.frizer.domain.Appointment;
import mk.frizer.domain.dto.simple.AppointmentJoinDTO;
import mk.frizer.domain.dto.simple.AppointmentSimpleDTO;
import mk.frizer.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mk.frizer.domain.dto.*;

import java.util.List;

@RestController
@RequestMapping({ "/api/appointments", "/api/appointment" })
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class AppointmentRestController {
    private final AppointmentService appointmentService;

    public AppointmentRestController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping()
    public List<AppointmentSimpleDTO> getAllAppointments() {
        return appointmentService.getAppointments().stream().map(Appointment::toDto).toList();
    }
    @GetMapping("/for-customer/{id}")
    public List<AppointmentJoinDTO> getAllAppointmentsForCustomer(@PathVariable Long id) {
        return appointmentService.getAllAppointmentsForCustomer(id)
                .stream().map(Appointment::toJoinDto).toList();
    }
    @GetMapping("/for-employee/{id}")
    public List<AppointmentJoinDTO> getAllAppointmentsForEmployee(@PathVariable Long id) {
        return appointmentService.getAllAppointmentsForEmployee(id)
                .stream().map(Appointment::toJoinDto).toList();
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<AppointmentSimpleDTO> getAppointmentById(@PathVariable Long id) {
//        return this.appointmentService.getAppointmentById(id)
//                .map(appointment -> ResponseEntity.ok().body(appointment.toDto()))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @PostMapping("/add")
    public ResponseEntity<AppointmentSimpleDTO> createAppointment(@RequestBody AppointmentAddDTO appointmentAddDTO) {
        try {
            return this.appointmentService.createAppointment(appointmentAddDTO)
                    .map(appointment -> ResponseEntity.ok().body(appointment.toDto()))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AppointmentSimpleDTO> deleteAppointmentById(@PathVariable Long id) {
        try {
            return this.appointmentService.deleteAppointmentById(id)
                    .map(appointment -> ResponseEntity.ok().body(appointment.toDto()))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/attended/{id}")
    public ResponseEntity<AppointmentSimpleDTO> changeAttendanceForAppointment(@PathVariable Long id) {
        return this.appointmentService.changeUserAttendanceAppointment(id)
                .map(appointment -> ResponseEntity.ok().body(appointment.toDto()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
