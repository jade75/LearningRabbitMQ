package ie.dkit.d00216118.basicmessage.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.dkit.d00216118.basicmessage.entity.DeadInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeadConsumer {

    @Autowired
    private ObjectMapper objectMapper;


    @RabbitListener(queues = "${mq.consumer.real.queue.name}")
    public void consumeMsg(@Payload byte[] msg){
        try {

            DeadInfo info = objectMapper.readValue(msg, DeadInfo.class);
            log.info("死信队列实战-监听真正队列-消费队列中的消息,监听到消息内容为：{}",info);

            //TODO:用于执行后续的相关业务逻辑

        }catch (Exception e){
            log.error("死信队列实战-监听真正队列-消费队列中的消息 - 面向消费者-发生异常：{} ",e.fillInStackTrace());
        }
    }
}
