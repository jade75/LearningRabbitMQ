package ie.dkit.d00216118.basicmessage.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TopicProducer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;


    public void sendMsgTopic(String msg, String routingKey) {
        //判断对象是否为null
        if (!Strings.isNullOrEmpty(msg) && !Strings.isNullOrEmpty(routingKey)) {
            try {
                //设置消息的传输格式为json
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //指定交换机
                rabbitTemplate.setExchange(env.getProperty("mq.topic.exchange.name"));
                //指定路由的实际取值，根据不同取值，RabbitMQ将自行进行匹配通配符，从而路由到不同的队列中
                rabbitTemplate.setRoutingKey(routingKey);

                //创建消息
                Message message = MessageBuilder.withBody(msg.getBytes("utf-8")).build();
                //发送消息
                rabbitTemplate.convertAndSend(message);

                //打印日志
                log.info("消息模型TopicExchange-生产者-发送消息：{}  路由：{} ", msg, routingKey);
            } catch (Exception e) {
                log.error("消息模型TopicExchange-生产者-发送消息发生异常：{} ", msg, e.fillInStackTrace());
            }
        }
    }
}