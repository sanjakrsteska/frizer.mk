package mk.frizer.domain.dto.simple;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Builder
public class AppointmentJoinDTO {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime dateFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime dateTo;
    private String treatmentName;
    private String salonName;
    private String employeeName;
    private Long employeeBaseUserId;
    private Long customerBaseUserId;
    private String customerName;
    private boolean attended;
}
