package ie.dkit.d00216118.basicmessage.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.dkit.d00216118.basicmessage.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FanoutConsumer {

    @Autowired
    public ObjectMapper objectMapper;

    /**
     * 监听并消费队列中的消息-fanoutExchange-one
     */

    @RabbitListener(queues = "${mq.fanout.queue.one.name}")
    public void consumeFanoutMsgOne(@Payload byte[] msg) {
        try {
            Person info = objectMapper.readValue(msg, Person.class);
            log.info("消息模型fanoutExchange-one-消费者-监听消费到消息：{} ", info);
        } catch (Exception e) {
            log.error("消息模型-消费者-发生异常：", e.fillInStackTrace());
        }
    }


    /**
     * 监听并消费队列中的消息-fanoutExchange-two
     */
    @RabbitListener(queues = "${mq.fanout.queue.two.name}")
    public void consumeFanoutMsgTwo(@Payload byte[] msg){
        try {
            Person info=objectMapper.readValue(msg, Person.class);
            log.info("消息模型fanoutExchange-two-消费者-监听消费到消息：{} ",info);


        }catch (Exception e){
            log.error("消息模型-消费者-发生异常：",e.fillInStackTrace());
        }
    }
}

/*
an easy way, receive object directly not need convert to JSON

    @RabbitListener(queues = "${mq.fanout.queue.one.name}")
    public void consumeFanoutMsgOne(@Payload Person info) {
        try {
            log.info("消息模型fanoutExchange-one-消费者-监听消费到消息：{} ", info);
        } catch (Exception e) {
            log.error("消息模型-消费者-发生异常：", e.fillInStackTrace());
        }
    }

   */

/*

2022-04-10 14:18:24.453  INFO 89728 --- [           main] i.d.d.b.BasicMessageApplicationTests     : Started BasicMessageApplicationTests in 3.905 seconds (JVM running for 5.361)
2022-04-10 14:18:24.827  INFO 89728 --- [           main] i.d.d.b.producer.FanoutProducer          : 消息模型fanoutExchange-生产者-发送消息：Person(id=3, name=唐僧, userName=debug)
2022-04-10 14:18:24.878  INFO 89728 --- [ionShutdownHook] o.s.a.r.l.SimpleMessageListenerContainer : Waiting for workers to finish.
2022-04-10 14:18:24.918  INFO 89728 --- [ntContainer#2-1] i.d.d.b.consumer.FanoutConsumer          : 消息模型fanoutExchange-one-消费者-监听消费到消息：Person(id=3, name=唐僧, userName=debug)
2022-04-10 14:18:24.918  INFO 89728 --- [ntContainer#3-1] i.d.d.b.consumer.FanoutConsumer          : 消息模型fanoutExchange-two-消费者-监听消费到消息：Person(id=3, name=唐僧, userName=debug)

*/
