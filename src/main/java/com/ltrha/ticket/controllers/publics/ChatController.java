package com.ltrha.ticket.controllers.publics;


import com.ltrha.ticket.domain.request.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {


    private Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/messages/{userId}")
    public void sendMessageToUser(@DestinationVariable(value = "userId") String userId, @Payload ChatMessageRequest message) {
        simpMessagingTemplate.convertAndSend("/topic/messages/" + userId, message);
        logger.info("Message sent to user: " + userId);
    }


    @MessageMapping("/messages/service")
    public void sendMessageToCustomerService(@Payload ChatMessageRequest message) {
        simpMessagingTemplate.convertAndSend("/topic/messages/services", message);
        logger.info("Message sent to customer service: " + message.getAgentId());
    }
}
