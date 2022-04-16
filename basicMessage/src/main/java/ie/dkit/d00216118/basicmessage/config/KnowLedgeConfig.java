package ie.dkit.d00216118.basicmessage.config;

//import ie.dkit.d00216118.basicmessage.consumer.KnowledgeManualConsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
public class KnowLedgeConfig {

    @Autowired
    private Environment env;

    //创建队列
    @Bean(name = "autoQueue")
    public Queue autoQueue(){
        return new Queue(env.getProperty("mq.auto.knowledge.queue.name"),true);
    }


    //创建交换机
    @Bean
    public DirectExchange autoExchange(){
        return new DirectExchange(env.getProperty("mq.auto.knowledge.exchange.name"),true,false);
    }

    //创建绑定
    @Bean
    public Binding autoBinding(){
        return BindingBuilder.bind(autoQueue()).to(autoExchange()).with(env.getProperty("mq.auto.knowledge.routing.key.name"));
    }



    ///////////manual///////////////
    //创建绑定

    //创建队列
    @Bean(name = "manualQueue")
    public Queue manualQueue(){
        return new Queue(env.getProperty("mq.manual.knowledge.queue.name"),true);
    }

    //创建交换机
    @Bean
    public TopicExchange manualExchange(){
        return new TopicExchange(env.getProperty("mq.manual.knowledge.exchange.name"),true,false);
    }

    @Bean
    public Binding manualBinding(){
        return BindingBuilder.bind(manualQueue()).to(manualExchange()).with(env.getProperty("mq.manual.knowledge.routing.key.name"));
    }


}
