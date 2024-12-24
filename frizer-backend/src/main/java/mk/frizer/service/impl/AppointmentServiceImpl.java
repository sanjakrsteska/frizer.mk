package mk.frizer.service.impl;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import mk.frizer.domain.*;
import mk.frizer.domain.dto.AppointmentAddDTO;
import mk.frizer.domain.events.AppointmentCreatedEvent;
import mk.frizer.domain.exceptions.*;
import mk.frizer.repository.*;
import mk.frizer.service.AppointmentService;
import mk.frizer.utilities.EmailGenerator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final TreatmentRepository treatmentRepository;
    private final CustomerRepository customerRepository;
    private final SalonRepository salonRepository;
    private final EmployeeRepository employeeRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final EmailService emailService;
    private final EmailGenerator emailGenerator;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, TreatmentRepository treatmentRepository, CustomerRepository customerRepository, SalonRepository salonRepository, EmployeeRepository employeeRepository, ApplicationEventPublisher applicationEventPublisher, EmailService emailService, EmailGenerator emailGenerator) {
        this.appointmentRepository = appointmentRepository;
        this.treatmentRepository = treatmentRepository;
        this.customerRepository = customerRepository;
        this.salonRepository = salonRepository;
        this.employeeRepository = employeeRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.emailService = emailService;
        this.emailGenerator = emailGenerator;
    }

    private boolean isDivisibleBy20Minutes(LocalDateTime dateTime) {
        int minutes = dateTime.getMinute();
        return minutes % 20 == 0;
    }

    @Override
    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> getAllAppointmentsForCustomer(Long id) {
        return appointmentRepository.getAllAppointmentsForCustomer(id);
    }

    @Override
    public List<Appointment> getAllAppointmentsForEmployee(Long id) {
        return appointmentRepository.getAllAppointmentsForEmployee(id);
    }

    @Override
    public Optional<Appointment> getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(AppointmentNotFoundException::new);
        return Optional.of(appointment);
    }

    @Override
    @Transactional
    public Optional<Appointment> createAppointment(AppointmentAddDTO appointmentAddDTO) throws MessagingException {
        Customer customer = customerRepository.findById(appointmentAddDTO.getCustomerId())
                .orElseThrow(CustomerNotFoundException::new);
        Salon salon = salonRepository.findById(appointmentAddDTO.getSalonId())
                .orElseThrow(SalonNotFoundException::new);
        Employee employee = salon.getEmployees().stream()
                .filter(e -> e.getId().equals(appointmentAddDTO.getEmployeeId()))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
        Treatment treatment = salon.getSalonTreatments().stream()
                .filter(t -> t.getId().equals(appointmentAddDTO.getTreatmentId()))
                .findFirst()
                .orElseThrow(TreatmentNotFoundException::new);

        // CHECK IF THE TIMES ARE DIVISIBLE BY 20 MINUTES, BECAUSE VALID APPOINTMENT MUST BE DIVISIBLE BY 20 MINUTES
        if (!isDivisibleBy20Minutes(appointmentAddDTO.getDateFrom()) || !isDivisibleBy20Minutes(appointmentAddDTO.getDateTo())) {
            throw new AppointmentNotDivisibleBy20Minutes("Appointment times must be divisible by 20 minutes.");
        }

        Appointment appointment = new Appointment(appointmentAddDTO.getDateFrom(), appointmentAddDTO.getDateTo(), treatment, salon, employee, customer);
        appointmentRepository.save(appointment);

        applicationEventPublisher.publishEvent(new AppointmentCreatedEvent(appointment));
        String body = emailGenerator.createMessage(appointment);
        emailService.sendAppointmentConfirmation(customer.getBaseUser().getEmail(),"Потврда на резервација",body);
        return Optional.of(appointment);
    }

    @Override
    @Transactional
    public Optional<Appointment> updateAppointment(Long id, LocalDateTime from, LocalDateTime to, Long treatmentId, Long salonId, Long employeeId, Long customerId) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(AppointmentNotFoundException::new);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(SalonNotFoundException::new);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(EmployeeNotFoundException::new);
        Treatment treatment = treatmentRepository.findById(treatmentId)
                .orElseThrow(TreatmentNotFoundException::new);

        appointment.setDateFrom(from);
        appointment.setDateTo(to);
        appointment.setTreatment(treatment);
        appointment.setSalon(salon);
        appointment.setEmployee(employee);
        appointment.setCustomer(customer);

        return Optional.of(appointmentRepository.save(appointment));
    }

    @Override
    @Transactional
    public Optional<Appointment> deleteAppointmentById(Long id) throws MessagingException {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(appointment.isEmpty())
            throw new AppointmentNotFoundException();
        appointmentRepository.deleteById(id);
        String body = emailGenerator.createCancellationMessage(appointment.get());
        emailService.sendAppointmentCancellation(appointment.get().getCustomer().getBaseUser().getEmail(),"Откажување резервација за третман",body);
        return appointment;
    }

    @Override
    @Transactional
    public Optional<Appointment> changeUserAttendanceAppointment(Long id) {
        Appointment appointment = getAppointmentById(id).get();
        appointment.setAttended(!appointment.isAttended());
        appointmentRepository.save(appointment);
        return Optional.of(appointment);
    }
}
