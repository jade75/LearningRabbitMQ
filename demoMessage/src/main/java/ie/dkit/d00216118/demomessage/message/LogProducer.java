package ie.dkit.d00216118.demomessage.message;

import ie.dkit.d00216118.demomessage.dto.UserLoginDto;
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

@Component
@Slf4j
public class LogProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;


    public void sendLogMsg(UserLoginDto userLoginDto) {
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("mq.login.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("mq.login.routing.key.name"));

            rabbitTemplate.convertAndSend(userLoginDto, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties messageProperties = message.getMessageProperties();
                    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_KEY_CLASSID_FIELD_NAME, UserLoginDto.class);
                    return message;
                }
            });

            log.info("系统日志记录-生产者-发送登录成功后的用户相关信息入队列-内容：{} ", userLoginDto);
        } catch (Exception e) {
            log.error("系统日志记录-生产者-发送登录成功后的用户相关信息入队列-发生异常：{} ", userLoginDto, e.fillInStackTrace());
        }
    }
}
