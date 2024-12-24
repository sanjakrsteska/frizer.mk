package mk.frizer.domain.dto.simple;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class EmployeeSimpleDTO {
    private Long id;
    private List<Long> appointmentsActiveIds;
    private List<Long> appointmentsHistoryIds;
    private Long salonId;

    private Long baseUserId;

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<String> roles;
    private Double rating;
    private Integer numberOfReviews;
    private LocalDate employmentDate;
}
