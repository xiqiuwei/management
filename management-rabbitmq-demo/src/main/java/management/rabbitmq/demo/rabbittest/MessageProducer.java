package management.rabbitmq.demo.rabbittest;


import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * @Author xiqiuwei
 * @Date Created in 11:37 2019/7/29
 * @Description 消息生产者父类接口
 * @Modified By:
 */
public abstract class MessageProducer {
    /**
     * @return com.management.warehouse.core.rabbittest.MessageResponse
     * @Author xiqiuwei
     * @Date 11:41  2019/7/29
     * @Param [routingKey, message]
     * @Description 普通消息通过路由键绑定交换机消息发送
     */
    public abstract MessageResponse send(String exchange, String routingKey, Object message, CorrelationData correlationData);

    /**
     * @return com.management.warehouse.core.rabbittest.MessageResponse
     * @Author xiqiuwei
     * @Date 11:41  2019/7/29
     * @Param [routingKey, message, delayTime]
     * @Description 延迟队列
     */
    public abstract MessageResponse sendDelay(String exchange, String routingKey, Object message,Long expiration, CorrelationData correlationData);

    /**
     * @return com.management.warehouse.core.rabbittest.MessageResponse
     * @Author xiqiuwei
     * @Date 11:41  2019/7/29
     * @Param [routingKey, message, timeStamp]
     * @Description 定时消息发送
     */
    public abstract MessageResponse sendTiming(String exchange, String routingKey, Object message, Long timeStamp, CorrelationData correlationData);
}
