package management.rabbitmq.demo.rabbittest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.amqp.DirectRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;


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
    // 普通队列名
    private static final String commonQueue = "commonQueue";
    // 死信队列名
    private static final String deadQueue = "deadQueue";
    // 普通交换机名
    private static final String commonExchange = "commonExchange";
    // 死信交换机名
    private static final String deadExchange = "deadExchange";
    // 普通路由键名
    private static final String commonRoutingKey = "commonRoutingKey";
    // 死信路由键名
    private static final String deadRoutingKey = "deadRoutingKey";

    /**
     * 多消费者配置
     *
     * @return
     */
    @Bean("simpleListener")
    public SimpleRabbitListenerContainerFactory simpleListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory simpleFactory = new SimpleRabbitListenerContainerFactory();
        simpleFactory.setConnectionFactory(connectionFactory);
        simpleFactory.setMessageConverter(messageConverter());
        simpleFactory.setAdviceChain();
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


    @Bean
    public MessageConverter messageConverter() {
        return new ContentTypeDelegatingMessageConverter(new Jackson2JsonMessageConverter());
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

    /**
     * @return org.springframework.amqp.rabbit.core.RabbitTemplate
     * @Author xiqiuwei
     * @Date 21:55  2019/7/29
     * @Param []
     * @Description rabbitMQ初始化的时候会将自定义得bean注入到spring
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        // 消息发送到交换机前，失败后callback
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("这是ack消息到了交换机:{}", ack);
            } else {
                log.error("这是错误的ack消息:{}", cause);
                throw new RuntimeException("发送消息失败的原因是:" + cause);
                // TODO 也可以讲异常原因放数据库货缓存便于查找
            }
        });
        // 模板设置重试机制
        rabbitTemplate.setRetryTemplate(retryTemplate());
        // 消息发送到队列前，失败后callback
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息内容:{}", new String(message.getBody()));
            log.info("回复文本:{},回复代码：{}", replyText, replyCode);
            log.info("交换器名称:{},路由键：{}", exchange, routingKey);
        });
        return rabbitTemplate;
    }


    /**
     * @return org.springframework.amqp.core.Queue
     * @Author xiqiuwei
     * @Date 15:04  2019/7/29
     * @Param []
     * @Description 创建死信队列
     */
    @Bean
    public Queue deadUserQueue() {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", deadExchange);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", deadRoutingKey);
        return new Queue(deadQueue, true, false, false, params);
    }

    /**
     * 普通交换机
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(commonExchange, true, false);
    }

    /**
     * @return org.springframework.amqp.core.DirectExchange
     * @Author xiqiuwei
     * @Date 14:53  2019/7/29
     * @Param []
     * @Description 死信交换机
     */
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(deadExchange, true, false);
    }

    /**
     * @return org.springframework.amqp.core.Queue
     * @Author xiqiuwei
     * @Date 18:16  2019/7/25
     * @Param []
     * @Description 创建普通队列
     */
    @Bean
    public Queue userQueue() {
        return new Queue(commonQueue, true);
    }

    /**
     * @return org.springframework.amqp.core.Binding
     * @Author xiqiuwei
     * @Date 18:37  2019/7/25
     * @Param []
     * @Description 创建基本交换机+基本路由 -> 死信队列 的绑定
     */
    @Bean
    public Binding bindingQueue() {
        return BindingBuilder.bind(deadUserQueue()).to(directExchange()).with(commonRoutingKey);
    }

    /**
     * @return org.springframework.amqp.core.Binding
     * @Author xiqiuwei
     * @Date 15:11  2019/7/29
     * @Param []
     * @Description 死信路由键+死信交换机->普通队列最终消费消息的地方
     */
    @Bean
    public Binding deadBindingQueue() {
        return BindingBuilder.bind(userQueue()).to(deadExchange()).with(deadRoutingKey);
    }

    /**
     * @return org.springframework.retry.support.RetryTemplate
     * @Author xiqiuwei
     * @Date 21:32  2019/7/29
     * @Param []
     * @Description rabbitMQ重试机制
     */
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy exponentialBackOff = new ExponentialBackOffPolicy();
        exponentialBackOff.setInitialInterval(1000);
        exponentialBackOff.setMaxInterval(60000);
        exponentialBackOff.setMultiplier(1.5);
        retryTemplate.setBackOffPolicy(exponentialBackOff);
        return retryTemplate;
    }
}
