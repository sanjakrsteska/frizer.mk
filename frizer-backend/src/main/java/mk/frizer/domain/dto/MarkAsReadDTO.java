package mk.frizer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkAsReadDTO {
    private Long senderId;
    private Long receiverId;
}
