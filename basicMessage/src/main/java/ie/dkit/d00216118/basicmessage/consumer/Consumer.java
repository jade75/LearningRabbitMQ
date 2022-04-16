package ie.dkit.d00216118.basicmessage.consumer;

import ie.dkit.d00216118.basicmessage.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("${mq.basic.info.queue.name}"))
    public void consumerMsg(@Payload String msg) {
        try {
//            String message=new String(msg,"utf-8");
            log.info("基本消息模型-消费者-监听消费到消息：{} ", msg);

        } catch (Exception e) {
            log.error("基本消息模型-消费者-发生异常：", e.fillInStackTrace());
        }
    }


    @RabbitListener(queues = "${mq.object.info.queue.name}")
    public void consumeObjectMsg(@Payload Person person){
        try {
            log.info("基本消息模型-监听消费处理对象信息-消费者-监听消费到消息：{} ",person);


        }catch (Exception e){
            log.error("基本消息模型-监听消费处理对象信息-消费者-发生异常：",e.fillInStackTrace());
        }
    }

//    @RabbitHandler
//    @RabbitListener(queuesToDeclare = @Queue("local.middleware.mq.basic.info.queue"))
//    public void processQ1(String message) {
//        System.out.println("++++++++direct Receiver Q1: " + message);
//    }
}
