# RabbitMQ

## install

```shell
% brew install rabbitmq
```



 % touch ~/.bash_profile

```shell
% vim .bash_profile 

export PATH=$PATH:/user/local/Cellar/rabbitmq/3.9.14/sbin

```

% source .bash_profile 

```shell
% rabbitmq-server
```



Add user and permission

```shell
% rabbitmqctl add_user admin 123456

#permission
% rabbitmqctl set_permissions -p "/" admin ".*" ".*" ".*"

#set tag
% rabbitmqctl set_user_tags admin administrator

#show 
% rabbitmqctl list_user_permissions admin   


#access
http://localhost:15672/  




启动监控管理器：rabbitmq-plugins enable rabbitmq_management

关闭监控管理器：rabbitmq-plugins disable rabbitmq_management

启动rabbitmq：rabbitmq-service start

关闭rabbitmq：rabbitmq-service stop

查看所有的队列：rabbitmqctl list_queues

清除所有的队列：rabbitmqctl reset

清除具体的某个队列：rabbitmqctl purge_queue queue_name

关闭应用：rabbitmqctl stop_app

启动应用：rabbitmqctl start_app



```



## concept



![python-one](https://github.com/jade75/LearningRabbitMQ/blob/master/pic/python-one.png)

***Producing*** means nothing more than sending. A program that sends messages is a *producer* :

*A **queue*** is the name for a post box which lives inside RabbitMQ. Although messages flow through RabbitMQ and your applications, they can only be stored inside a *queue*. A *queue* is only bound by the host's memory & disk limits, it's essentially a large message buffer. Many *producers* can send messages that go to one queue, and many *consumers* can try to receive data from one *queue*. This is how we represent a queue

***Consuming*** has a similar meaning to receiving. A *consumer* is a program that mostly waits to receive messages



The AMPQ protocol defines five exchange types: ***Direct*, *Fanout*, *Topic,* and *Headers***.

![截屏2022-04-16 下午5.33.08](https://github.com/jade75/LearningRabbitMQ/blob/master/pic/%E6%88%AA%E5%B1%8F2022-04-16%20%E4%B8%8B%E5%8D%885.33.08.png)

**The *default exchange*** is bound automatically to every queue created.

**The direct exchange** is bound to a queue by a routing key.(exchange type as one- to-one binding.)

**The *topic exchange*** is similar to the direct exchange; the only difference is that in its binding, you can add a wildcard into its routing key.

**The *fanout exchange*** copy the message to all the bound queues (exchange as a message broadcast).

**The *headers exchange*** is similar to the topic exchange; the only difference is that the binding is based on
 the `message headers` (this is a very powerful exchange, and you can do *all* and *any* expressions for its headers)







