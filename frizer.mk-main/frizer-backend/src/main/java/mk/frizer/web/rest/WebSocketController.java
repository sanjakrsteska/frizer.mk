package mk.frizer.web.rest;

import mk.frizer.domain.dto.simple.MessageAddDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void handleChatMessage(MessageAddDTO userWithMessage) {
        Long receiverId = userWithMessage.getReceiverId();
        String destination = "/user/" + receiverId + "/queue/messages";
        messagingTemplate.convertAndSend(destination, userWithMessage);
    }
}
