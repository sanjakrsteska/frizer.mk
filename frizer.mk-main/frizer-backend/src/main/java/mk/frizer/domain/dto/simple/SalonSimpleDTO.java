package mk.frizer.domain.dto.simple;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import mk.frizer.domain.enums.ImagePosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class SalonSimpleDTO {
    private Long id;
    private String name;
    private String description;
    private String location;
    private String city;
    private String phoneNumber;

    @Builder.Default
    private List<Long> employeesIds = new ArrayList<>();
    @Builder.Default
    private List<Long> salonTreatmentsIds = new ArrayList<>();
    @Builder.Default
    private List<Long> tagsIds = new ArrayList<>();
    private Long ownerId;

    @Builder.Default
    private Long backgroundImage = null;
    @Builder.Default
    Map<Long, ImagePosition> images = new HashMap<>();

    private Double rating;
    private Integer numberOfReviews;

    private Float latitude;
    private Float longitude;
}
