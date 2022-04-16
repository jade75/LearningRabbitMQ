package ie.dkit.d00216118.basicmessage.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class DirectConfig {

    @Autowired
    private Environment env;

    //创建交换机-directExchange
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(env.getProperty("mq.direct.exchange.name"),true,false);
    }

    //创建队列1
    @Bean(name = "directQueueOne")
    public Queue directQueueOne(){
        return new Queue(env.getProperty("mq.direct.queue.one.name"),true);
    }

    //创建队列2
    @Bean(name = "directQueueTwo")
    public Queue directQueueTwo(){
        return new Queue(env.getProperty("mq.direct.queue.two.name"),true);
    }

    //创建绑定1
    @Bean
    public Binding directBindingOne(){
        return BindingBuilder.bind(directQueueOne()).to(directExchange()).with(env.getProperty("mq.direct.routing.key.one.name"));
    }

    //创建绑定2
    @Bean
    public Binding directBindingTwo(){
        return BindingBuilder.bind(directQueueTwo()).to(directExchange()).with(env.getProperty("mq.direct.routing.key.two.name"));
    }

}
