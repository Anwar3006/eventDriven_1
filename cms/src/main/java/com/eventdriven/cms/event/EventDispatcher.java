package com.eventdriven.cms.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eventdriven.cms.domain.BlogPostEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EventDispatcher {
    
    private final RabbitTemplate rabbitTemplate;

    private final String exchangeName;

    private final String routingKey;
    
    public EventDispatcher(RabbitTemplate rabbitTemplate, @Value("${rabbitmq.exchange.notificiation}") String exchangeName, @Value("${rabbitmq.routing.notification}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    };

    public void dispatch(final BlogPostEvent postEvent) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, postEvent);
        log.info("Sending event: " + postEvent.toString());
    }
}
