package ie.dkit.d00216118.basicmessage.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.dkit.d00216118.basicmessage.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DirectProducer {

    @Autowired
    private Environment env;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 发送消息-基于DirectExchange消息模型-one
     * @param info
     */
    public void sendMsgDirectOne(Person info){
        if (info!=null){
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

                rabbitTemplate.setExchange(env.getProperty("mq.direct.exchange.name"));
                rabbitTemplate.setRoutingKey(env.getProperty("mq.direct.routing.key.one.name"));

                Message msg= MessageBuilder.withBody(objectMapper.writeValueAsBytes(info)).build();
                rabbitTemplate.convertAndSend(msg);

                log.info("消息模型DirectExchange-one-生产者-发送消息：{} ",info);
            }catch (Exception e){
                log.error("消息模型DirectExchange-one-生产者-发送消息发生异常：{} ",info,e.fillInStackTrace());
            }
        }
    }

    /**
     * 发送消息-基于DirectExchange消息模型-two
     * @param info
     */
    public void sendMsgDirectTwo(Person info){
        if (info!=null){
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

                rabbitTemplate.setExchange(env.getProperty("mq.direct.exchange.name"));
                rabbitTemplate.setRoutingKey(env.getProperty("mq.direct.routing.key.two.name"));

                Message msg= MessageBuilder.withBody(objectMapper.writeValueAsBytes(info)).build();
                rabbitTemplate.convertAndSend(msg);

                log.info("消息模型DirectExchange-two-生产者-发送消息：{} ",info);
            }catch (Exception e){
                log.error("消息模型DirectExchange-two-生产者-发送消息发生异常：{} ",info,e.fillInStackTrace());
            }
        }
    }


}
