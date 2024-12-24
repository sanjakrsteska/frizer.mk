package mk.frizer.service;

import mk.frizer.domain.Message;
import mk.frizer.domain.dto.simple.MessageAddDTO;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Optional<Message> createMessage(MessageAddDTO messageDTO);

    List<Message> findBySenderAndReceiver(Long senderId, Long receiverId);

    List<Message> getMessagesByUserId(Long userId);

    List<Message> findBySenderOrReceiver(Long senderId, Long receiverId);

    void markMessagesAsRead(Long senderId, Long receiverId);


}


