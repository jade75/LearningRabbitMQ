package ie.dkit.d00216118.demomessage.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RabbitMQConfig {

    @Autowired
    private Environment env;

    //创建队列
    @Bean(name = "loginQueue")
    public Queue loginQueue(){
        return new Queue(env.getProperty("mq.login.queue.name"),true);
    }

    //创建交换机
    @Bean
    public TopicExchange loginExchange(){
        return new TopicExchange(env.getProperty("mq.login.exchange.name"),true,false);
    }

    //创建绑定
    @Bean
    public Binding loginBinding(){
        return BindingBuilder.bind(loginQueue()).to(loginExchange()).with(env.getProperty("mq.login.routing.key.name"));
    }

}
