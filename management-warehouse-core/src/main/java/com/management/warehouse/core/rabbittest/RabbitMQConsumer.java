package com.management.warehouse.core.rabbittest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.warehouse.core.entity.User;
import com.rabbitmq.client.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @Author xiqiuwei
 * @Date Created in 17:26 2019/7/25
 * @Description
 * @Modified By:
 */
@Component
public class RabbitMQConsumer {
    @Autowired
    private ObjectMapper objectMapper;
    @RabbitListener(queues = "commonQueue",containerFactory = "simpleListener")
    public void consumer(Message message, Channel channel) throws IOException {
//
        // 手动ack消息确认
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        byte[] body = message.getBody();
        System.out.println(objectMapper.readValue(body,User.class));
        System.out.println(message.getMessageProperties().getHeaders().get("error"));
    }
}
