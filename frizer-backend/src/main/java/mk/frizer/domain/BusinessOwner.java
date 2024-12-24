package mk.frizer.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.frizer.domain.dto.simple.BusinessOwnerSimpleDTO;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class BusinessOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    private List<Salon> salonList;
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "base_user_id")
    private BaseUser baseUser;

    public BusinessOwner(BaseUser baseUser) {
        this.baseUser = baseUser;
        this.salonList = new ArrayList<>();
    }

    public String getFullName() {
        return String.format("%s %s", baseUser.getFirstName(), baseUser.getLastName());
    }

    public BusinessOwnerSimpleDTO toDto(){
        return BusinessOwnerSimpleDTO.builder()
                .id(this.id)
                .salonListIds(salonList.stream().map(Salon::getId).toList())
                .baseUserId(this.baseUser.getId())
                .email(this.baseUser.getEmail())
                .firstName(this.baseUser.getFirstName())
                .lastName(this.baseUser.getLastName())
                .phoneNumber(this.baseUser.getPhoneNumber())
                .roles(this.baseUser.getRoles().stream().map(Enum::name).toList())
                .build();
    }
}
