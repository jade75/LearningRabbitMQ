package ie.dkit.d00216118.basicmessage.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.dkit.d00216118.basicmessage.entity.DeadInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeadProducer {

    @Autowired
    private Environment env;

    //定义RabbitMQ操作组件
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //定义Json序列化和反序列化组件实例
    @Autowired
    private ObjectMapper objectMapper;

    public void sendMsg(DeadInfo info){

        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("mq.producer.basic.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("mq.producer.basic.routing.key.name"));

            rabbitTemplate.convertAndSend(info, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {

                    MessageProperties messageProperties = message.getMessageProperties();
                    //设置消息的持久化策略
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

                    //设置消息头-即直接指定发送的消息所属的对象类型
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, DeadInfo.class);

                    messageProperties.setExpiration(String.valueOf(10000));

                    return message;
                }
            });

            log.info("死信队列实战-发送对象类型的消息入死信队列-内容为：{} ",info);

        }catch(Exception e){
            log.error("死信队列实战-发送对象类型的消息入死信队列-发生异常：{} ",info,e.fillInStackTrace());
        }
    }

}
