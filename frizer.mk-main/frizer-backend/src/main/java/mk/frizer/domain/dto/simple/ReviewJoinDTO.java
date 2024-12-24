package mk.frizer.domain.dto.simple;


import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewJoinDTO {
    private Long id;
    private String authorFirstName;
    private String authorLastName;
    private String employeeFullName;
    private Long authorId;
    private Long employeeId;
    private Double rating;
    private String comment;
    @DateTimeFormat(pattern = "yyyy-dd-MM HH:mm")
    private LocalDateTime date;
}

