package mk.frizer.domain.dto.simple;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerSimpleDTO {
    private Long id;
    private List<Long> appointmentsActiveIds;
    private List<Long> appointmentsHistoryIds;

    private Long baseUserId;

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<String> roles;
    private List<Long> favouriteSalonsIds;

}
