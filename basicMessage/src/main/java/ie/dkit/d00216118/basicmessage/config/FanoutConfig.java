package ie.dkit.d00216118.basicmessage.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class FanoutConfig {

    @Autowired
    private Environment env;

    @Bean(name = "fanoutQueueOne")
    public Queue fanoutQueueOne() {
        return new Queue(env.getProperty("mq.fanout.queue.one.name"), true);
    }

    //创建队列2
    @Bean(name = "fanoutQueueTwo")
    public Queue fanoutQueueTwo() {
        return new Queue(env.getProperty("mq.fanout.queue.two.name"), true);
    }

    //创建交换机-fanoutExchange
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(env.getProperty("mq.fanout.exchange.name"), true, false);
    }

    //创建绑定1
    @Bean
    public Binding fanoutBindingOne(){
        return BindingBuilder.bind(fanoutQueueOne()).to(fanoutExchange());
    }

    //创建绑定2
    @Bean
    public Binding fanoutBindingTwo(){
        return BindingBuilder.bind(fanoutQueueTwo()).to(fanoutExchange());
    }

}
