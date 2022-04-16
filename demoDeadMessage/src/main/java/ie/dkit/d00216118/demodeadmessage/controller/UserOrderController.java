package ie.dkit.d00216118.demodeadmessage.controller;

import ie.dkit.d00216118.demodeadmessage.dto.UserOrderDto;
import ie.dkit.d00216118.demodeadmessage.response.BaseResponse;
import ie.dkit.d00216118.demodeadmessage.response.StatusCode;
import ie.dkit.d00216118.demodeadmessage.service.DeadUserOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserOrderController {

    //定义请求前缀
    private static final String prefix = "user/order";

    //用户下单处理服务实例
    @Autowired
    private DeadUserOrderService deadUserOrderService;


    /**
     * 用户下单
     *
     * @return
     */
    @RequestMapping(value = prefix + "/push", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody @Validated UserOrderDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            deadUserOrderService.pushUserOrder(dto);
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

}
/*

 ==>Preparing:insert into user_order(order_no,user_id,status,create_time)values(?,?,?,?)
 2022-04-15 11:43:42.381DEBUG 32134---[nio-8080-exec-1]i.d.d.d.m.U.insertSelective:==>Parameters:20220415001(String),10011(Integer),1(Integer),2022-04-15 11:43:42.06(Timestamp)
 2022-04-15 11:43:42.390DEBUG 32134---[nio-8080-exec-1]i.d.d.d.m.U.insertSelective:<==Updates:1
 2022-04-15 11:43:42.404INFO 32134---[nio-8080-exec-1]i.d.d.d.service.DeadUserOrderService:用户成功下单，下单信息为：UserOrder{id=12,orderNo='20220415001',userId=10011,status=1,isActive=null,createTime=Fri Apr 15 11:43:42CST 2022,updateTime=null}
 2022-04-15 11:43:42.411INFO 32134---[nio-8080-exec-1]i.d.d.d.service.DeadOrderPublisher:用户下单支付超时-发送用户下单记录id的消息入死信队列-内容为：orderId=12
 2022-04-15 11:43:52.427INFO 32134---[ntContainer#0-1]i.d.d.d.service.DeadOrderConsumer:用户下单支付超时消息模型-监听真正队列-监听到消息内容为：orderId=12
 2022-04-15 11:43:52.431DEBUG 32134---[ntContainer#0-1]i.d.d.d.m.U.selectByIdAndStatus:==>Preparing:SELECT id,order_no,user_id,status,is_active,create_time,update_time FROM user_order WHERE is_active AND `status` =?AND id=?
 2022-04-15 11:43:52.432DEBUG 32134---[ntContainer#0-1]i.d.d.d.m.U.selectByIdAndStatus:==>Parameters:1(Integer),12(Integer)
 2022-04-15 11:43:52.445DEBUG 32134---[ntContainer#0-1]i.d.d.d.m.U.selectByIdAndStatus:<==Total:1
 2022-04-15 11:43:52.449INFO 32134---[ntContainer#0-1]i.d.d.d.service.DeadOrderConsumer:=====orderUserOrder{id=12,orderNo='20220415001',userId=10011,status=1,isActive=1,createTime=Fri Apr 15 11:43:42CST 2022,updateTime=null}
 2022-04-15 11:43:52.450DEBUG 32134---[ntContainer#0-1]i.d.d.d.m.U.updateByPrimaryKeySelective:==>Preparing:update user_order SET order_no=?,user_id=?,status=?,is_active=?,create_time=?,update_time=?where id=?
 2022-04-15 11:43:52.451DEBUG 32134---[ntContainer#0-1]i.d.d.d.m.U.updateByPrimaryKeySelective:==>Parameters:20220415001(String),10011(Integer),1(Integer),0(Integer),2022-04-15 11:43:42.0(Timestamp),2022-04-15 11:43:52.449(Timestamp),12(Integer)
 2022-04-15 11:43:52.459DEBUG 32134---[ntContainer#0-1]i.d.d.d.m.U.updateByPrimaryKeySelective:<==Updates:1
 2022-04-15 11:43:52.460DEBUG 32134---[ntContainer#0-1]i.d.d.d.m.MqOrderMapper.insertSelective:==>Preparing:insert into mq_order(order_id,business_time,memo)values(?,?,?)
 2022-04-15 11:43:52.461DEBUG 32134---[ntContainer#0-1]i.d.d.d.m.MqOrderMapper.insertSelective:==>Parameters:12(Integer),2022-04-15 11:43:52.459(Timestamp),更新失效当前用户下单记录Id,orderId=12(String)
 2022-04-15 11:43:52.492DEBUG 32134---[ntContainer#0-1]i.d.d.d.m.MqOrderMapper.insertSelective:<==Updates:1

*/
