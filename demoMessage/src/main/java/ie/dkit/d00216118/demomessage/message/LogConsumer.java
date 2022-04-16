package ie.dkit.d00216118.demomessage.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.dkit.d00216118.demomessage.dto.UserLoginDto;
import ie.dkit.d00216118.demomessage.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogConsumer {

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    public ObjectMapper objectMapper;

    @RabbitListener(queues = "${mq.login.queue.name}")
    public void consumeMsg(@Payload byte[] msg) {
        try {
            UserLoginDto loginDto = objectMapper.readValue(msg, UserLoginDto.class);
            log.info("系统日志记录-消费者-监听消费用户登录成功后的消息-内容：{}", loginDto);
            sysLogService.recordLog(loginDto);
        } catch (Exception e) {
            log.error("系统日志记录-消费者-监听消费用户登录成功后的消息-发生异常：{} ", e.fillInStackTrace());
        }
    }
}
