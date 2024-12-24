package mk.frizer.domain.dto;

import lombok.Data;

@Data
public class TreatmentUpdateDTO {
    private String name;
    private Double price;
    private Integer duration;
}
