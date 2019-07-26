package com.management.warehouse.core.rabbittest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.warehouse.core.entity.User;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author xiqiuwei
 * @Date Created in 17:26 2019/7/25
 * @Description
 * @Modified By:
 */
@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = "myQueue", containerFactory = "simpleListener")
    public void consumer(@Payload byte[] message, @Header(AmqpHeaders.DELIVERY_TAG) long delivery, Channel channel) throws IOException {
        // 手动ack消息确认
        channel.basicAck(delivery, false);
        //channel.basicNack(delivery, false, true);
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(message, User.class);
        System.out.println("这是消息队列传送过来的user对象:" + user);
    }
}
