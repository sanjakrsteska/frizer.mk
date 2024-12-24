package mk.frizer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SalonUpdateDTO {
    private String name;
    private String description;
    private String location;
    private String phoneNumber;
}
