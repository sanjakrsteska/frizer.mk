package mk.frizer.service.impl;

import mk.frizer.domain.BaseUser;
import mk.frizer.domain.Message;
import mk.frizer.domain.dto.simple.MessageAddDTO;
import mk.frizer.domain.exceptions.UserNotFoundException;
import mk.frizer.repository.MessageRepository;
import mk.frizer.service.BaseUserService;
import mk.frizer.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    private final BaseUserService userService;
    private final MessageRepository messageRepository;
    public MessageServiceImpl(BaseUserService userService, MessageRepository messageRepository) {
        this.userService = userService;
        this.messageRepository = messageRepository;
    }

    @Override
    public Optional<Message> createMessage(MessageAddDTO messageDTO) {
        BaseUser sender = userService.getBaseUserById(messageDTO.getSenderId())
                .orElseThrow(UserNotFoundException::new);
        BaseUser receiver = userService.getBaseUserById(messageDTO.getReceiverId())
                .orElseThrow(UserNotFoundException::new);

        Message message = new Message(messageDTO.getContent(),sender,receiver, LocalDateTime.now());
        messageRepository.save(message);
        return Optional.of(message);
    }

    @Override
    public List<Message> findBySenderAndReceiver(Long senderId, Long receiverId) {
        return messageRepository.findAllByReceiverIdAndSenderId(senderId, receiverId);
    }


    @Override
    public List<Message> getMessagesByUserId(Long userId) {
        return messageRepository.findAllByReceiverIdOrSenderId(userId, userId);
    }

    @Override
    public List<Message> findBySenderOrReceiver(Long senderId, Long receiverId) {
        return messageRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(senderId, receiverId);
    }

    @Override
    @Transactional
    public void markMessagesAsRead(Long senderId, Long receiverId) {
        messageRepository.updateMessagesToRead(senderId, receiverId);

    }
}
