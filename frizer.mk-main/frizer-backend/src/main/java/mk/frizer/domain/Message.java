package mk.frizer.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.frizer.domain.dto.simple.MessageAddDTO;
import mk.frizer.domain.dto.simple.MessageSimpleDTO;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private BaseUser sender;
    private String content;
    private LocalDateTime timestamp;
    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id") // Specify the column for the sender
    private BaseUser receiver;

    private Boolean isRead;


    public Message(String content, BaseUser sender, BaseUser receiver, LocalDateTime timestamp) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.isRead = false;
    }
    public MessageSimpleDTO toDto(){
        return MessageSimpleDTO.builder()
                .id(id)
                .receiverId(receiver.getId())
                .senderId(sender.getId())
                .content(content)
                .timestamp(timestamp)
                .isRead(isRead)
                .build();


    }
}
