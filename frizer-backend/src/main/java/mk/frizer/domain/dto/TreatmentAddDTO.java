package mk.frizer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TreatmentAddDTO {
    private String name;
    private Long salonId;
    private Double price;
    private Integer duration;
}
