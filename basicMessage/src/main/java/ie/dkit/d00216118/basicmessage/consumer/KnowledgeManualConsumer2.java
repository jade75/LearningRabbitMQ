package ie.dkit.d00216118.basicmessage.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import ie.dkit.d00216118.basicmessage.entity.KnowledgeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class KnowledgeManualConsumer2 {

    @Autowired
    public ObjectMapper objectMapper;

//    mq.auto.knowledge.exchange.name
    @RabbitListener(queues = "${mq.manual.knowledge.queue.name}")
    public void consumeAutoMsg(Message message, Channel channel) throws IOException, InterruptedException {
        try {
            byte[] msg=message.getBody();
            int i=1/0;
            KnowledgeInfo info=objectMapper.readValue(msg, KnowledgeInfo.class);
            log.info("确认消费模式-人为手动确认消费-监听器监听消费消息-内容为：{} ",info);


            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        }catch (Exception e){
            if (message.getMessageProperties().getRedelivered()) {
//                System.out.println("消息已重复处理失败,拒绝再次接收");
                log.error("消息已重复处理失败,拒绝再次接收");
                // 拒绝消息，requeue=false 表示不再重新入队，如果配置了死信队列则进入死信队列
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
//                System.out.println("消息即将再次返回队列处理");
                log.error("消息即将再次返回队列处理");
                // requeue为是否重新回到队列，true重新入队
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }
}
