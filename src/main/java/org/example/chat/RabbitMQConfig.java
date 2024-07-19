package org.example.chat;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    //chatqueue라는 이름의 rabbitMQ 큐 생성 => 단일 큐로 처리
    public Queue chatQueue() {
        return new Queue("chatQueue", false);
    }

    @Bean
    //chatExchange라는 Topic 타입의 교환기 생성
    //Topic 타입 : routing key 패턴 기반으로 메시지를 전달
    //chat.# 패턴과 일치하는 모든 메시지를 "chatQueue"로 라우팅
    public TopicExchange chatExchange() {
        return new TopicExchange("chatExchange");
    }

    @Bean
    //chat.으로 시시작하는 모든 라우팅 키를 가진 메세지가 "chatQueue"로 전달됩니다.
    public Binding binding(Queue chatQueue, TopicExchange chatExchange) {
        return BindingBuilder.bind(chatQueue).to(chatExchange).with("chat.#");
    }


    @Bean
    //JSON 형식으로 메시지를 변환하는 컨버터를 생성
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    //RabbitMQ와의 통신을 담당하는 RabbitTemplate을 구성
    //JSON 메시지 컨버터를 설정
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}