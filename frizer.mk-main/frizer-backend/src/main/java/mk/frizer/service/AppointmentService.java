package mk.frizer.service;

import jakarta.mail.MessagingException;
import mk.frizer.domain.*;
import mk.frizer.domain.dto.AppointmentAddDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<Appointment> getAppointments();;
    List<Appointment> getAllAppointmentsForCustomer(Long id);
    List<Appointment> getAllAppointmentsForEmployee(Long id);

    Optional<Appointment> getAppointmentById(Long id);
    Optional<Appointment> createAppointment(AppointmentAddDTO appointmentAddDTO) throws MessagingException;
    Optional<Appointment> updateAppointment(Long id, LocalDateTime from, LocalDateTime to, Long treatment, Long salon, Long employee, Long customer);
    Optional<Appointment> deleteAppointmentById(Long id) throws MessagingException;
    Optional<Appointment> changeUserAttendanceAppointment(Long id);
}
