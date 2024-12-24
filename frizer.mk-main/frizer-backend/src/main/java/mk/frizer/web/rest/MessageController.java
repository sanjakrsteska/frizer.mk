package mk.frizer.web.rest;


import mk.frizer.domain.Message;
import mk.frizer.domain.dto.MarkAsReadDTO;
import mk.frizer.domain.dto.simple.MessageAddDTO;
import mk.frizer.domain.dto.simple.MessageSimpleDTO;
import mk.frizer.service.MessageService;
import mk.frizer.service.messaging.kafka.JsonKafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class MessageController {

    private JsonKafkaProducer kafkaProducer;
    private final MessageService messageService;

    public MessageController(JsonKafkaProducer kafkaProducer, MessageService messageService) {
        this.kafkaProducer = kafkaProducer;
        this.messageService = messageService;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody MessageAddDTO message) {
        kafkaProducer.sendMessage(message);
        messageService.createMessage(message);
        return ResponseEntity.ok("Json message sent to kafka topic");
    }

    @GetMapping("/all-conversation/{userId}")
    public ResponseEntity<List<MessageSimpleDTO>> getConversationByReceiverOrSender(@PathVariable Long userId) {
        List<Message> messages = messageService.getMessagesByUserId(userId);
        List<MessageSimpleDTO> messageSimpleDTOS = messages.stream().map(Message::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(messageSimpleDTOS);
    }

    @GetMapping("/conversation/{senderId}/{receiverId}")
    public ResponseEntity<List<MessageSimpleDTO>> getConversation(
            @PathVariable Long senderId, @PathVariable Long receiverId) {
        List<Message> messages = messageService.findBySenderAndReceiver(senderId, receiverId);
        List<MessageSimpleDTO> messageSimpleDTOS = messages.stream().map(Message::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(messageSimpleDTOS);
    }

    @GetMapping("/between/{userId1}/{userId2}")
    public ResponseEntity<List<MessageSimpleDTO>> getMessagesBetweenUsers(
            @PathVariable Long userId1, @PathVariable Long userId2) {
        List<Message> messages = messageService.findBySenderOrReceiver(userId1, userId2);
        List<MessageSimpleDTO> messageSimpleDTOS = messages.stream()
                .map(Message::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(messageSimpleDTOS);
    }

    @PutMapping("/mark-as-read")
    public ResponseEntity<Void> markMessagesAsRead(@RequestBody MarkAsReadDTO request) {
        Long senderId = request.getSenderId();
        Long receiverId = request.getReceiverId();

        messageService.markMessagesAsRead(senderId, receiverId);

        return ResponseEntity.ok().build();
    }

}
