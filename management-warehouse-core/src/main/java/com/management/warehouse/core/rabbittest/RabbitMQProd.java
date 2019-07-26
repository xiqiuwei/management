package com.management.warehouse.core.rabbittest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.warehouse.core.entity.User;
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

    @PostMapping("/user")
    public void prod(@RequestBody User user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
       /* rabbitTemplate.setExchange("directExchange");
        rabbitTemplate.setRoutingKey("myRoutingKey");*/
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(user))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
               // .setHeader("error","三土这个傻逼打篮球想那条鲲一样")
                .build();
        rabbitTemplate.convertAndSend("directExchange", "myRoutingKey", message, correlationData);
    }
}
