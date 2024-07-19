package org.example.chat;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;


//WebSocket을 통해 즉시 메시지를 전달하면서, 동시에 RabbitMQ를 통해 메시지를 저장하고 추가적인 처리를 할 수 있는 구조
@RestController
public class ChatController {

    @Autowired
    //Spring AMQP에서 제공하는 클래스로, RabbitMQ와의 통신을 쉽게 할 수 있도록 도와주는 핵심적인 컴포넌트
    private RabbitTemplate rabbitTemplate;


    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        // RabbitMQ를 통해 메시지 전송
        rabbitTemplate.convertAndSend("chatExchange", "chat.message", chatMessage);
        return chatMessage;
    }


}