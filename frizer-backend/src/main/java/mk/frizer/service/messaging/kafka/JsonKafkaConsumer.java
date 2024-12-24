package mk.frizer.service.messaging.kafka;

import mk.frizer.config.handlers.MessageWebSocketHandler;
import mk.frizer.domain.dto.simple.MessageAddDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumer.class);

    @Autowired
    private MessageWebSocketHandler webSocketHandler;

    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(MessageAddDTO user){
        LOGGER.info(String.format("Json message received -> %s", user.toString()));

        try {
            webSocketHandler.sendToUser(user.getReceiverId(), user);
        } catch (Exception e) {
            LOGGER.error("Error broadcasting message to WebSocket", e);
        }
    }
}
