package mk.frizer.domain.dto.simple;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class TagSimpleDTO {
    private Long id;
    private String name;
    @Builder.Default
    private List<Long> salonsWithTagIds = new ArrayList<>();
}
