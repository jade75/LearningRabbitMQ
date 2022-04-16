package ie.dkit.d00216118.basicmessage.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.dkit.d00216118.basicmessage.entity.KnowledgeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KnowledgeManualProducer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Environment env;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 配置 confirm 机制
     */
    private final RabbitTemplate.ConfirmCallback confirmCallback= new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            if (ack) {
//                System.out.println("confirm 消息发送确认成功...消息ID为：" + correlationData.getId());
                log.info("消息发送成功:correlationData({}))",correlationData.getId());
            } else {
                System.out.println("confirm 消息发送确认失败...消息ID为：" + correlationData + " 失败原因: " + cause);
            }
        }
    };

    private final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {

        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.out.println("return 消息退回 exchange: " + exchange + ", routingKey: "
                    + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
        }
    };


    /**
     * 基于MANUAL机制-生产者发送消息
     * @param info
     */
    public void sendAutoMsg(KnowledgeInfo info){
        try {
            if (info!=null){
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//                rabbitTemplate.setExchange(env.getProperty("mq.manual.knowledge.exchange.name"));
//                rabbitTemplate.setRoutingKey(env.getProperty("mq.manual.knowledge.routing.key.name"));

                Message message= MessageBuilder.withBody(objectMapper.writeValueAsBytes(info))
                        .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                        .build();

                CorrelationData correlationData = new CorrelationData(info.getId()+"");

//                rabbitTemplate.convertAndSend(message);

                log.info("基于MANUAL机制-生产者发送消息-内容为：{} ",info);
                rabbitTemplate.convertAndSend(
                        env.getProperty("mq.manual.knowledge.exchange.name"),
                        env.getProperty("mq.manual.knowledge.routing.key.namew"),
                        message,
//                        (message2)->{
//                            message2.getMessageProperties();
//                            System.out.println("return 消息退回 exchange:" + message2.getMessageProperties());
//                            return message2;
//                        },
                        correlationData
                        );




                rabbitTemplate.setConfirmCallback(confirmCallback);
                rabbitTemplate.setReturnCallback(returnCallback);


            }
        }catch (Exception e){
            log.error("基于MANUAL机制-生产者发送消息-发生异常：{} ",info,e.fillInStackTrace());
        }
    }
}
