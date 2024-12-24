package mk.frizer.domain.dto.simple;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CitySimpleDTO {
    private String name;
    @Builder.Default
    private List<Long> salonsIdsInCity = new ArrayList<>();
}
