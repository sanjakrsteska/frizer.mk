package mk.frizer.domain.dto.simple;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class BusinessOwnerSimpleDTO {
    private Long id;
    @Builder.Default
    private List<Long> salonListIds = new ArrayList<>();

    private Long baseUserId;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<String> roles;
}
