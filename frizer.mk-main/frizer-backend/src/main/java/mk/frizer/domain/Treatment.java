package mk.frizer.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.frizer.domain.dto.simple.TreatmentSimpleDTO;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@NoArgsConstructor
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer durationMultiplier;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Salon salon;
    private Double price;

    public Treatment(String name, Salon salon, Double price, Integer durationMultiplier) {
        this.name = name;
        this.salon = salon;
        this.price = price;
        this.durationMultiplier = durationMultiplier;
    }

    public TreatmentSimpleDTO toDto(){
        return TreatmentSimpleDTO.builder()
                .id(this.id)
                .name(this.name)
                .durationMultiplier(this.durationMultiplier)
                .salonId(this.salon.getId())
                .price(this.price)
                .build();
    }
}

