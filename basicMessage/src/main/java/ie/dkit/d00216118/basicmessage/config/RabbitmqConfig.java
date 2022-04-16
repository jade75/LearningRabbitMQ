package ie.dkit.d00216118.basicmessage.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;





@Slf4j
@Configuration
public class RabbitmqConfig {

    @Autowired
    private Environment env;

    @Bean(name = "basicQueue")
    public Queue basicQueue(){
        return new Queue(env.getProperty("mq.basic.info.queue.name"));
    }

    @Bean
    public DirectExchange basicExchange(){
        return new DirectExchange(env.getProperty("mq.basic.info.exchange.name"));
    }

    @Bean
    public Binding basicBinding(){
        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(env.getProperty("mq.basic.info.routing.key.name"));
    }



    /**object **/
    @Bean(name = "objectQueue")
    public Queue objectQueue(){
        return new Queue(env.getProperty("mq.object.info.queue.name"),true);
    }

    @Bean
    public DirectExchange objectExchange(){
        return new DirectExchange(env.getProperty("mq.object.info.exchange.name"),true,false);
    }

    @Bean
    public Binding objectBinding(){
        return BindingBuilder.bind(objectQueue()).to(objectExchange()).with(env.getProperty("mq.object.info.routing.key.name"));
    }
}
