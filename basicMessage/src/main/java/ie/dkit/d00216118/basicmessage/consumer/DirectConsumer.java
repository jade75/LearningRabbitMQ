package ie.dkit.d00216118.basicmessage.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import ie.dkit.d00216118.basicmessage.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.rmi.CORBA.ValueHandler;

@Slf4j
@Component
public class DirectConsumer {


    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 监听并消费队列中的消息-directExchange-one
     */
    @RabbitListener(queues = "${mq.direct.queue.one.name}")
    public void consumeDirectMsgOne(@Payload byte[] msg){
        try {
            Person info=objectMapper.readValue(msg, Person.class);
            log.info("消息模型directExchange-one-消费者-监听消费到消息：{} ",info);


        }catch (Exception e){
            log.error("消息模型directExchange-one-消费者-监听消费发生异常：",e.fillInStackTrace());
        }
    }

    /**
     * 监听并消费队列中的消息-directExchange-two
     */
    @RabbitListener(queues = "${mq.direct.queue.two.name}")
    public void consumeDirectMsgTwo(@Payload byte[] msg){
        try {
            Person info=objectMapper.readValue(msg, Person.class);
            log.info("消息模型directExchange-two-消费者-监听消费到消息：{} ",info);


        }catch (Exception e){
            log.error("消息模型directExchange-two-消费者-监听消费发生异常：",e.fillInStackTrace());
        }
    }

}

/*

2022-04-11 06:44:50.561  INFO 3533 --- [           main] i.d.d.b.producer.DirectProducer          : 消息模型DirectExchange-one-生产者-发送消息：Person(id=4, name=猪八戒, userName=debug)
2022-04-11 06:44:50.564  INFO 3533 --- [           main] i.d.d.b.producer.DirectProducer          : 消息模型DirectExchange-two-生产者-发送消息：Person(id=5, name=沙僧, userName=debug)
2022-04-11 06:44:50.597  INFO 3533 --- [ionShutdownHook] o.s.a.r.l.SimpleMessageListenerContainer : Waiting for workers to finish.
2022-04-11 06:44:50.624  INFO 3533 --- [ntContainer#2-1] i.d.d.b.consumer.DirectConsumer          : 消息模型directExchange-one-消费者-监听消费到消息：Person(id=4, name=猪八戒, userName=debug)
2022-04-11 06:44:50.624  INFO 3533 --- [ntContainer#3-1] i.d.d.b.consumer.DirectConsumer          : 消息模型directExchange-two-消费者-监听消费到消息：Person(id=5, name=沙僧, userName=debug)

*/
