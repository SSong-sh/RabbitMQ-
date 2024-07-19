package org.example.chat;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    private final RabbitTemplate rabbitTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageController(RabbitTemplate rabbitTemplate, SimpMessagingTemplate messagingTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/sendFromServer")
    public void sendMessageFromServer(@RequestBody String message) {
        String serverMessage = "서버: " + message;
        rabbitTemplate.convertAndSend("messageExchange", "message.routing.key", serverMessage);
        messagingTemplate.convertAndSend("/topic/messages", serverMessage);
    }
}