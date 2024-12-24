package mk.frizer.domain.dto.simple;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TreatmentSimpleDTO {
    private Long id;
    private String name;
    private Integer durationMultiplier;
    private Long salonId;
    private Double price;
}
