package ie.dkit.d00216118.basicmessage.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class TopConfig {

    @Autowired
    private Environment env;

    //创建交换机-topicExchange
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(env.getProperty("mq.topic.exchange.name"),true,false);
    }

    //创建队列1
    @Bean(name = "topicQueueOne")
    public Queue topicQueueOne(){
        return new Queue(env.getProperty("mq.topic.queue.one.name"),true);
    }

    //创建队列2
    @Bean(name = "topicQueueTwo")
    public Queue topicQueueTwo(){
        return new Queue(env.getProperty("mq.topic.queue.two.name"),true);
    }

    //创建绑定1
    @Bean
    public Binding topicBindingOne(){
        return BindingBuilder.bind(topicQueueOne()).to(topicExchange()).with(env.getProperty("mq.topic.routing.key.one.name"));
    }

    //创建绑定2
    @Bean
    public Binding topicBindingTwo(){
        return BindingBuilder.bind(topicQueueTwo()).to(topicExchange()).with(env.getProperty("mq.topic.routing.key.two.name"));
    }



}
