package mk.frizer.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.frizer.domain.dto.simple.CustomerSimpleDTO;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "appointment_customer_active_id")
    private List<Appointment> appointmentsActive;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "appointment_customer_history_id")
    private List<Appointment> appointmentsHistory;
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "base_user_id")
    private BaseUser baseUser;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Salon> favouriteSalons;

    public Customer(BaseUser baseUser) {
        this.baseUser = baseUser;
        this.appointmentsActive = new ArrayList<>();
        this.appointmentsHistory = new ArrayList<>();
        this.favouriteSalons = new ArrayList<>();
    }

    public String getFullName(){
        return String.format("%s %s", baseUser.getFirstName(), baseUser.getLastName());
    }

    public CustomerSimpleDTO toDto(){
        return CustomerSimpleDTO.builder()
                .id(this.id)
                .appointmentsActiveIds(appointmentsActive.stream().map(Appointment::getId).toList())
                .appointmentsHistoryIds(appointmentsHistory.stream().map(Appointment::getId).toList())
                .baseUserId(this.baseUser.getId())
                .email(this.baseUser.getEmail())
                .firstName(this.baseUser.getFirstName())
                .lastName(this.baseUser.getLastName())
                .phoneNumber(this.baseUser.getPhoneNumber())
                .roles(this.baseUser.getRoles().stream().map(Enum::name).toList())
                .favouriteSalonsIds(favouriteSalons.stream().map(Salon::getId).toList())
                .build();
    }
}