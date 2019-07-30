package com.management.warehouse.core.rabbittest;

import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author xiqiuwei
 * @Date Created in 11:33 2019/7/29
 * @Description 生产者
 * @Modified By:
 */

@Component
public class RabbitMqMessageProducer extends MessageProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * @return com.management.warehouse.core.rabbittest.MessageResponse
     * @Author xiqiuwei
     * @Date 11:45  2019/7/29
     * @Param [routingKey, message]
     * @Description 普通消息发送
     */
    @Override
    public MessageResponse send(String exchange, String routingKey, Object message,CorrelationData correlationData) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey,message,correlationData);
            messageResponse.setRet(true);
            messageResponse.setMsg("message send successful");
        }catch (Exception e) {
            messageResponse.setRet(false);
            messageResponse.setMsg("message send error");
        }
        return messageResponse;
    }

    /**
     * @return com.management.warehouse.core.rabbittest.MessageResponse
     * @Author xiqiuwei
     * @Date 11:44  2019/7/29
     * @Param [routingKey, message, expiration]
     * @Description 死信队列 TTL模式当TTL过期的时候会将消息route到普通队列里面进行消费
     */
    @Override
    public MessageResponse sendDelay(String exchange, String routingKey, Object message,Long expiration, CorrelationData correlationData) {

        MessageResponse messageResponse = new MessageResponse();
        try {
            final String expire = String.valueOf(expiration);
            rabbitTemplate.convertAndSend(exchange,routingKey, message, messagePost -> {
                // 消息持久化
                messagePost.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                messagePost.getMessageProperties().setExpiration(expire);
                messagePost.getMessageProperties().setHeader("error","三土打篮球像那条鲲一样,头部消息看自己业务需不需要");
                return messagePost;
            },correlationData);
            messageResponse.setRet(true);
            messageResponse.setMsg("message send successful");
        } catch (Exception e) {
            messageResponse.setRet(false);
            messageResponse.setMsg("message send error");
        }
        return messageResponse;
    }

    /**
     * @return com.management.warehouse.core.rabbittest.MessageResponse
     * @Author xiqiuwei
     * @Date 11:44  2019/7/29
     * @Param [routingKey, message, timeStamp]
     * @Description 定时队列发送消息
     */
    @Override
    public MessageResponse sendTiming(String exchange, String routingKey, Object message, Long timeStamp, CorrelationData correlationData) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            Date date = new Date();
            long time = date.getTime();
            if (null == timeStamp || timeStamp.compareTo(time) <= 0) {
                rabbitTemplate.convertAndSend(exchange,routingKey,message);
                messageResponse.setRet(true);
                messageResponse.setMsg("message send successful");
                return messageResponse;
            }
            // 单位换算到秒
            long delay = (timeStamp - time)/1000L;
            rabbitTemplate.convertAndSend(exchange,routingKey, message, messagePost -> {
                // 消息持久化
                messagePost.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                // 延迟队列时间差,和TTL有所区别的是此消息是延迟多久推送出去
                messagePost.getMessageProperties().setDelay((int)delay);
                messagePost.getMessageProperties().setHeader("error","三土打篮球像那条鲲一样，头部消息看自己的需求");
                return messagePost;
            },correlationData);
            messageResponse.setRet(true);
            messageResponse.setMsg("message send successful");
        } catch (Exception e) {
            messageResponse.setRet(false);
            messageResponse.setMsg("message send error");
        }
        return messageResponse;
    }
}
