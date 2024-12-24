package mk.frizer.config.handlers;

import mk.frizer.domain.BaseUser;
import mk.frizer.service.BaseUserService;
import mk.frizer.domain.dto.simple.MessageAddDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;


@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;
    private CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final BaseUserService userService;

    public MessageWebSocketHandler(SimpMessagingTemplate messagingTemplate, BaseUserService userService) {
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for (WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void broadcast(String message) throws Exception {
        for (WebSocketSession session : sessions) {
            session.sendMessage(new TextMessage(message));
        }
    }

    public void sendToUser(Long userId, MessageAddDTO userWithMessage) throws Exception {
        Long receiverId = userWithMessage.getReceiverId();

        String destination = "/user/" + receiverId + "/queue/messages";
        Optional<BaseUser> rec = userService.getBaseUserById(userWithMessage.getReceiverId());
        if (rec.isPresent()) {
            messagingTemplate.convertAndSend(destination, userWithMessage);
        }
    }
}
