package ie.dkit.d00216118.demodeadmessage.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class OrderMqConfig {

    @Autowired
    private Environment env;

    @Bean
    public Queue orderDeadQueue(){
        Map<String, Object> args=new HashMap<>();
        args.put("x-dead-letter-exchange", env.getProperty("mq.order.dead.exchange.name"));
        args.put("x-dead-letter-routing-key", env.getProperty("mq.order.dead.routing.key.name"));

        log.info("===== create Dead queue");
        //设定TTL，单位为ms，在这里为了测试方便，设置为10s，当然实际业务场景可能为1h或者更长
        args.put("x-message-ttl", 10000);
        return new Queue(env.getProperty("mq.order.dead.queue.name"), true, false, false, args);
    }

    @Bean
    public TopicExchange orderProducerExchange() {
        return new TopicExchange(env.getProperty("mq.producer.order.exchange.name"), true, false);
    }

    //创建“基本消息模型”的基本绑定-基本交换机+基本路由 - 面向生产者
    @Bean
    public Binding orderProducerBinding() {
        return BindingBuilder.bind(orderDeadQueue()).to(orderProducerExchange()).with(env.getProperty("mq.producer.order.routing.key.name"));
    }

    //创建真正队列 - 面向消费者
    @Bean
    public Queue realOrderConsumerQueue() {
        return new Queue(env.getProperty("mq.consumer.order.real.queue.name"), true);
    }

    //创建死信交换机
    @Bean
    public TopicExchange basicOrderDeadExchange() {
        return new TopicExchange(env.getProperty("mq.order.dead.exchange.name"), true, false);
    }

    //创建死信路由及其绑定
    @Bean
    public Binding basicOrderDeadBinding() {
        return BindingBuilder.bind(realOrderConsumerQueue()).to(basicOrderDeadExchange()).with(env.getProperty("mq.order.dead.routing.key.name"));
    }


}

