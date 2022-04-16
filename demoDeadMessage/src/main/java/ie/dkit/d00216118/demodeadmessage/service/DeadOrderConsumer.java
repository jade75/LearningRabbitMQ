package ie.dkit.d00216118.demodeadmessage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.dkit.d00216118.demodeadmessage.entity.UserOrder;
import ie.dkit.d00216118.demodeadmessage.mapper.UserOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeadOrderConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    //定义用户下单操作Mapper
    @Autowired
    private UserOrderMapper userOrderMapper;

    //用户下单支付超时-处理服务实例
    @Autowired
    private DeadUserOrderService deadUserOrderService;

    @RabbitListener(queues = "${mq.consumer.order.real.queue.name}")
    public void consumeMsg(@Payload Integer orderId){
        try {
            log.info("用户下单支付超时消息模型-监听真正队列-监听到消息内容为：orderId={}",orderId);

            //TODO:接下来执行核心的业务逻辑
            //查询该用户下单记录Id对应的支付状态是为"已保存"
            UserOrder userOrder=userOrderMapper.selectByIdAndStatus(orderId,1);

            log.info("=====order{}", userOrder.toString());
            if (userOrder!=null){

                //不等于null，则代表该用户下单记录已经超时没支付该笔订单了，故而需要失效该笔下单记录
                deadUserOrderService.updateUserOrderRecord(userOrder);
            }
        }catch (Exception e){
            log.error("用户下单支付超时消息模型-监听真正队列-发生异常：orderId={} ",orderId,e.fillInStackTrace());
        }
    }

}
