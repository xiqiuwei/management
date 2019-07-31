package management.rabbitmq.demo.rabbittest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import management.rabbitmq.demo.entity.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
@SuppressWarnings("all")
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
        System.out.println(objectMapper.readValue(body, User.class));
        System.out.println(message.getMessageProperties().getHeaders().get("error"));
    }
}
