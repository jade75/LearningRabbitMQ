package ie.dkit.d00216118.basicmessage.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TopicConsumer {


    /**
     * 监听并消费队列中的消息-topicExchange-*通配符
     */
    @RabbitListener(queues = "${mq.topic.queue.one.name}")
    public void consumeTopicMsgOne(@Payload byte[] msg) {
        try {
            String message = new String(msg, "utf-8");
            log.info("queue.one消息模型topicExchange-*-消费者-监听消费到消息：{} ", message);

        } catch (Exception e) {
            log.error("消息模型topicExchange-*-消费者-监听消费发生异常：", e.fillInStackTrace());
        }
    }


    /**
     * 监听并消费队列中的消息-topicExchange-#通配符
     */
    @RabbitListener(queues = "${mq.topic.queue.two.name}")
    public void consumeTopicMsgTwo(@Payload byte[] msg) {
        try {
            String message = new String(msg, "utf-8");
            log.info("queue.two消息模型topicExchange-#-消费者-监听消费到消息：{} ", message);


        } catch (Exception e) {
            log.error("消息模型topicExchange-#-消费者-监听消费发生异常：", e.fillInStackTrace());
        }
    }

}

/*

2022-04-11 09:07:03.324  INFO 31400 --- [           main] i.d.d.b.producer.TopicProducer           : 消息模型TopicExchange-生产者-发送消息：这是TopicExchange消息模型的消息  路由：local.middleware.mq.topic.routing.java.key
2022-04-11 09:07:03.327  INFO 31400 --- [           main] i.d.d.b.producer.TopicProducer           : 消息模型TopicExchange-生产者-发送消息：这是TopicExchange消息模型的消息  路由：local.middleware.mq.topic.routing.php.python.key
2022-04-11 09:07:03.328  INFO 31400 --- [           main] i.d.d.b.producer.TopicProducer           : 消息模型TopicExchange-生产者-发送消息：这是TopicExchange消息模型的消息  路由：local.middleware.mq.topic.routing.key
2022-04-11 09:07:03.340  INFO 31400 --- [ntContainer#7-1] i.d.d.b.consumer.TopicConsumer           : queue.two消息模型topicExchange-#-消费者-监听消费到消息：这是TopicExchange消息模型的消息(local.middleware.mq.topic.routing.java.key #)
2022-04-11 09:07:03.340  INFO 31400 --- [ntContainer#6-1] i.d.d.b.consumer.TopicConsumer           : queue.one消息模型topicExchange-*-消费者-监听消费到消息：这是TopicExchange消息模型的消息(local.middleware.mq.topic.routing.java.key *)

2022-04-11 09:07:03.341  INFO 31400 --- [ntContainer#7-1] i.d.d.b.consumer.TopicConsumer           : queue.two消息模型topicExchange-#-消费者-监听消费到消息：这是TopicExchange消息模型的消息(local.middleware.mq.topic.routing.php.python.key #)
2022-04-11 09:07:03.342  INFO 31400 --- [ntContainer#7-1] i.d.d.b.consumer.TopicConsumer           : queue.two消息模型topicExchange-#-消费者-监听消费到消息：这是TopicExchange消息模型的消息(local.middleware.mq.topic.routing.key #)

*/
