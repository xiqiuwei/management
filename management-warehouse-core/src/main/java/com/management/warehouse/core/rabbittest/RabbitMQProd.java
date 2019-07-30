package com.management.warehouse.core.rabbittest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.warehouse.core.entity.User;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author xiqiuwei
 * @Date Created in 18:17 2019/7/24
 * @Description
 * @Modified By:
 */
@RestController
@RequestMapping("/base")
public class RabbitMQProd {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitMqMessageProducer rabbitMqMessageProducer;

    @PostMapping("/user")
    public void prod(@RequestBody User user) {
        String routingKey = "commonRoutingKey";
        String exchange = "commonExchange";
        rabbitTemplate.setRoutingKey(routingKey);
        rabbitTemplate.setExchange(exchange);
        Long expire = 10000L;
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        rabbitMqMessageProducer.sendDelay(exchange,routingKey,user,expire,correlationData);
    }
}
