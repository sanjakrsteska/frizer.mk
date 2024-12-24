package mk.frizer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.frizer.domain.dto.simple.AppointmentJoinDTO;
import mk.frizer.domain.dto.simple.AppointmentSimpleDTO;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime dateFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime dateTo;
    @ManyToOne
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Treatment treatment;

    @ManyToOne
    @JsonIgnore
    private Salon salon;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private boolean attended;

    public Appointment(LocalDateTime dateFrom, LocalDateTime dateTo, Treatment treatment, Salon salon, Employee employee, Customer customer) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.treatment = treatment;
        this.salon = salon;
        this.employee = employee;
        this.customer = customer;
        this.attended = false;
    }

    public String getDateFromFormatted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return dateFrom.format(formatter);
    }

    public String getDateToFormatted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return dateTo.format(formatter);
    }

    public boolean isDateNDaysFromNow(int days) {
        LocalDateTime now = LocalDateTime.now();
        return !dateFrom.isAfter(now.plusDays(days));
    }

    public boolean isDateOneDayFromNow(){
        return isDateNDaysFromNow(1);
    }

    public AppointmentSimpleDTO toDto(){
        return AppointmentSimpleDTO
                .builder()
                .id(this.id)
                .dateFrom(this.dateFrom)
                .dateTo(this.dateTo)
                .treatmentId(this.treatment.getId())
                .salonId(this.salon.getId())
                .customerId(this.customer.getId())
                .employeeId(this.employee.getId())
                .attended(this.attended)
                .build();
    }

    public AppointmentJoinDTO toJoinDto(){
        return AppointmentJoinDTO
                .builder()
                .id(this.id)
                .dateFrom(this.dateFrom)
                .dateTo(this.dateTo)
                .treatmentName(this.treatment.getName())
                .salonName(this.salon.getName())
                .customerName(this.customer.getFullName())
                .employeeName(this.employee.getFullName())
                .attended(this.attended)
                .customerBaseUserId(this.customer.getBaseUser().getId())
                .employeeBaseUserId(this.employee.getBaseUser().getId())
                .build();
    }
}