package com.management.warehouse.core.rabbittest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.amqp.DirectRabbitListenerContainerFactoryConfigurer;
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
    private SimpleRabbitListenerContainerFactoryConfigurer simpleFactoryConfigurer;
    @Autowired
    private DirectRabbitListenerContainerFactoryConfigurer directFactoryConfigurer;

    /**
     * 多消费者配置
     *
     * @return
     */
    @Bean("simpleListener")
    public SimpleRabbitListenerContainerFactory simpleListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory simpleFactory = new SimpleRabbitListenerContainerFactory();
        simpleFactory.setConnectionFactory(connectionFactory);
        //simpleFactory.setMessageConverter(messageConverter());
        simpleFactoryConfigurer.configure(simpleFactory, connectionFactory);
        return simpleFactory;
    }

   /* @Bean("directListener")
    public DirectRabbitListenerContainerFactory directListenerContainerFactory () {
        DirectRabbitListenerContainerFactory directFactory = new DirectRabbitListenerContainerFactory();
        directFactory.setConnectionFactory(connectionFactory);
        // 最大并发性
        //directFactory.setTaskExecutor();
        return directFactory;
    }*/


   /* @Bean
    public MessageConverter messageConverter() {
        return new ContentTypeDelegatingMessageConverter(new Jackson2JsonMessageConverter());
    }
*/
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
        //rabbitTemplate.setMessageConverter(messageConverter());
        // 发送确认看消息到没到交换机
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("这是ack消息到了交换机:{}",ack);
                log.info("这是消息的唯一id:{}",correlationData.getId());
            }else {
                log.error("这是错误的ack消息:{}",cause);
                throw new RuntimeException("发送消息失败的原因是:" + cause);
            }
        });
        // 如果消息到了交换机然后出错的话就会走这个方法
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message,replyCode,replyText,exchange,routingKey)->{
            log.info("消息内容:{}",new String(message.getBody()));
            log.info("回复文本:{},回复代码：{}",replyText,replyCode);
            log.info("交换器名称:{},路由键：{}",exchange,routingKey);
        });
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
