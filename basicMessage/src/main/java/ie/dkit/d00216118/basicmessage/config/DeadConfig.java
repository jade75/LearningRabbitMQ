package ie.dkit.d00216118.basicmessage.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class DeadConfig {

    @Autowired
    private Environment env;

    @Bean
    public Queue basicDeadQueue(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", env.getProperty("mq.dead.exchange.name"));
        args.put("x-dead-letter-routing-key", env.getProperty("mq.dead.routing.key.name"));
        //设定TTL，单位为ms，在这里指的是10s
        args.put("x-message-ttl", 10000);

        return new Queue(env.getProperty("mq.dead.queue.name"), true, false, false, args);
    }

    @Bean
    public TopicExchange basicProducerExchange() {
        return new TopicExchange(env.getProperty("mq.producer.basic.exchange.name"), true, false);
    }

    @Bean
    public Binding basicProducerBinding() {
        return BindingBuilder.bind(basicDeadQueue()).to(basicProducerExchange()).with(env.getProperty("mq.producer.basic.routing.key.name"));
    }



    //创建真正队列 - 面向消费者
    @Bean
    public Queue realConsumerQueue() {
        return new Queue(env.getProperty("mq.consumer.real.queue.name"), true);
    }

    //创建死信交换机
    @Bean
    public TopicExchange basicDeadExchange() {
        return new TopicExchange(env.getProperty("mq.dead.exchange.name"), true, false);
    }

    //创建死信路由及其绑定
    @Bean
    public Binding basicDeadBinding() {
        return BindingBuilder.bind(realConsumerQueue()).to(basicDeadExchange()).with(env.getProperty("mq.dead.routing.key.name"));
    }

}
