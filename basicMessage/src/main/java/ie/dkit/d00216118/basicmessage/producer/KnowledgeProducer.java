package ie.dkit.d00216118.basicmessage.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.dkit.d00216118.basicmessage.entity.KnowledgeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KnowledgeProducer {

    @Autowired
    private Environment env;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendAutoMsg(KnowledgeInfo info){
        try {
            if (info!=null){
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(env.getProperty("mq.auto.knowledge.exchange.name"));
                rabbitTemplate.setRoutingKey(env.getProperty("mq.auto.knowledge.routing.key.name"));

                Message message= MessageBuilder.withBody(objectMapper.writeValueAsBytes(info))
                        .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                        .build();
                rabbitTemplate.convertAndSend(message);
                log.info("基于AUTO机制-生产者发送消息-内容为：{} ",info);
            }
        }catch (Exception e){
            log.error("基于AUTO机制-生产者发送消息-发生异常：{} ",info,e.fillInStackTrace());
        }
    }


}
