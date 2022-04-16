package ie.dkit.d00216118.basicmessage;

import ie.dkit.d00216118.basicmessage.consumer.KnowledgeConsumer;
import ie.dkit.d00216118.basicmessage.entity.DeadInfo;
import ie.dkit.d00216118.basicmessage.entity.KnowledgeInfo;
import ie.dkit.d00216118.basicmessage.entity.Person;
import ie.dkit.d00216118.basicmessage.producer.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasicMessageApplicationTests {

//    @Test
//    void contextLoads() {
//    }

    @Autowired
    ie.dkit.d00216118.basicmessage.producer.producer producer;

    @Test
    void basicStringTest() throws InterruptedException {
        producer.producerMsg("test direct message");
        Thread.sleep(1000);
//        producer.producer("fuck ");
    }


    @Test
    void basicObjectTest() throws InterruptedException {
        Person p=new Person(1,"大圣","debug");
        producer.produceObjMsg(p);
        Thread.sleep(10000);
    }

    @Autowired
    FanoutProducer fanoutProducer;

    @Test
    public void testFanout1() throws Exception{
        Person p=new Person(3,"唐僧","debug");
        fanoutProducer.sendMsgFanout(p);
    }


    @Autowired
    DirectProducer directProducer;


    @Test
    public void test4() throws Exception{
        Person info=new Person(4,"猪八戒","debug");
        directProducer.sendMsgDirectOne(info);

        info=new Person(5,"沙僧","debug");
        directProducer.sendMsgDirectTwo(info);
    }

    @Autowired
    TopicProducer topicProducer;

    @Test
    public void test5() throws Exception{
        String msg="这是TopicExchange消息模型的消息";

        //此时相当于*，即java替代了*的位置;
        //当然由于#表示任意个单词，故而也将路由到#表示的路由和对应的队列中
        String routingKeyOne="local.middleware.mq.topic.routing.java.key";

        //此时相当于#：即 php.python 替代了#的位置
        String routingKeyTwo="local.middleware.mq.topic.routing.php.python.key";

        //此时相当于#：即0个单词
        String routingKeyThree="local.middleware.mq.topic.routing.key";

//        topicProducer.sendMsgTopic(msg,routingKeyOne);
//        topicProducer.sendMsgTopic(msg,routingKeyTwo);
        topicProducer.sendMsgTopic(msg,routingKeyThree);
    }


    @Autowired
    private KnowledgeProducer knowledgeProducer;

    @Test
    public void test6() throws Exception{
        KnowledgeInfo info=new KnowledgeInfo();
        info.setId(10010);
        info.setCode("auto");
        info.setMode("基于AUTO的消息确认消费模式");

        knowledgeProducer.sendAutoMsg(info);

    }


    @Autowired
    private KnowledgeManualProducer knowledgeManualPublisher;

    @Test
    public void test7() throws Exception{
        KnowledgeInfo info=new KnowledgeInfo();
        info.setId(10011);
        info.setCode("manual");
        info.setMode("基于MANUAL的消息确认消费模式");

        knowledgeManualPublisher.sendAutoMsg(info);
    }


    @Autowired
    DeadProducer deadPublisher;

    @Test
    public void test8() throws Exception{
        //定义实体对象1
        DeadInfo info=new DeadInfo(1,"~~~~我是第一则消息~~~");
        //发送实体对象1消息入死信队列
        deadPublisher.sendMsg(info);

        //定义实体对象2
        info=new DeadInfo(2,"~~~~我是第二则消息~~~");
        //发送实体对象2消息入死信队列
        deadPublisher.sendMsg(info);

        //等待30s再结束-目的是为了能看到消费者监听真正队列中的消息
        Thread.sleep(30000);
    }

}
