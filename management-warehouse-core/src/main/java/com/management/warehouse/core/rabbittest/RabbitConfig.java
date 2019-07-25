package com.management.warehouse.core.rabbittest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;


@SuppressWarnings("all")
/**
 * @Author xiqiuwei
 * @Date Created in 10:00 2019/7/25
 * @Description rabbitMQ配置类
 * @Modified By:
 */
@Slf4j
@Configuration
public class RabbitConfig {
    @Autowired
    private Environment env;
    @Autowired
    private CachingConnectionFactory connectionFactory;
    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;


    /**
     * 多消费者配置
     *
     * @return
     */
    @Bean("multiListener")
    public SimpleRabbitListenerContainerFactory listenerContainerFactory() {
        SimpleRabbitListenerContainerFactory simpleFactory = new SimpleRabbitListenerContainerFactory();
        simpleFactory.setConnectionFactory(connectionFactory);
        simpleFactory.setAcknowledgeMode(env.getProperty("spring.rabbitmq.listener.acknowledge-mode", AcknowledgeMode.class));
        simpleFactory.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.concurrency", Integer.class));
        simpleFactory.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.max-concurrency", Integer.class));
        factoryConfigurer.configure(simpleFactory, connectionFactory);
        return simpleFactory;
    }

    /**
     * 序列化消息
     *
     * @return
     */
    @Bean
    public MessageConverter jsonMessage() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * @return org.springframework.amqp.rabbit.core.RabbitAdmin
     * @Author xiqiuwei
     * @Date 17:13  2019/7/25
     * @Param []
     * @Description rabbitMQ代理类
     */
    @Bean
    public RabbitAdmin rabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        // 发送确认
        rabbitTemplate.setConfirmCallback((correlationData, result, cause) -> {
            if (!result) {
                throw new RuntimeException("发送失败的原因是:" + cause);
            }
        });
        rabbitTemplate.setReturnCallback((message,replyCode,replyText,exchange,routingKey)->{
            log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
        });
        // rabbitMQ消息序列化
        rabbitTemplate.setMessageConverter(jsonMessage());
        return rabbitTemplate;
    }

    /**
     * @return org.springframework.amqp.core.Queue
     * @Author xiqiuwei
     * @Date 18:16  2019/7/25
     * @Param []
     * @Description 创建队列
     */
    @Bean
    public Queue userQueue() {
        return new Queue("myQueue", true);
    }

    /**
     * 创建交换机
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange", true, false);
    }
    /**
     *@Author xiqiuwei
     *@Date 18:37  2019/7/25
     *@Param []
     *@return org.springframework.amqp.core.Binding
     *@Description 将队列通过routingKey绑定到交换机
     */
    @Bean
    public Binding bindingQueueToExchange() {
        return BindingBuilder.bind(userQueue()).to(directExchange()).with("myRoutingKey");
    }

}
