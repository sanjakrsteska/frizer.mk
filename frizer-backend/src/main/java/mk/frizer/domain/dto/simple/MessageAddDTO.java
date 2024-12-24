package mk.frizer.domain.dto.simple;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MessageAddDTO {
    private String content;
    private LocalDateTime timestamp;
    private Long receiverId;
    private Long senderId;

}
