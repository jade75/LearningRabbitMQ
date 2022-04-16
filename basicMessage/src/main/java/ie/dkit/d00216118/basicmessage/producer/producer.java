package ie.dkit.d00216118.basicmessage.producer;


import ie.dkit.d00216118.basicmessage.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
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
public class producer {

    @Autowired
    private Environment env;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void produceObjMsg(Person person) {
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(env.getProperty("mq.object.info.exchange.name"));
        rabbitTemplate.setRoutingKey(env.getProperty("mq.object.info.routing.key.name"));

//        rabbitTemplate.convertAndSend(person);
        rabbitTemplate.convertAndSend(person, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties messageProperties=message.getMessageProperties();
                messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Person.class);

                return message;
            }
        });

    }


    public void producerMsg(String message) {
        if (!Strings.isNullOrEmpty(message)) {

            try {

                //set message convert type
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

                //set exchange by name
                rabbitTemplate.setExchange(env.getProperty("mq.basic.info.exchange.name"));

                //set routing by key
                rabbitTemplate.setRoutingKey(env.getProperty("mq.basic.info.routing.key.name"));

//                rabbitTemplate.convertAndSend(
//                        env.getProperty("mq.basic.info.exchange.name"),
//                        env.getProperty("mq.basic.info.routing.key.name"),
//                        message);

                rabbitTemplate.convertAndSend(message);
                log.info("-------- baisc message for Direct EX, {}", message);

            } catch (Exception e) {
                log.info("-------- baisc message for Direct EX error {}", e.fillInStackTrace());
            }
        }
    }
}
