package ie.dkit.d00216118.demomessage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.dkit.d00216118.demomessage.dto.UserLoginDto;
import ie.dkit.d00216118.demomessage.mapper.SysLogMapper;
import ie.dkit.d00216118.demomessage.model.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@EnableAsync
public class SysLogService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SysLogMapper sysLogMapper;

    @Async
    public void recordLog(UserLoginDto dto) {

        try {
            SysLog entity = new SysLog();
            entity.setUserId(dto.getUserId());
            entity.setModule("用户登录模块");
            entity.setData(objectMapper.writeValueAsString(dto));
            entity.setMemo("用户登录成功记录相关登录信息");
            entity.setCreateTime(new Date());

            sysLogMapper.insertSelective(entity);
        } catch (Exception e) {
            log.error("系统日志服务-记录用户登录成功的信息入数据库-发生异常：{} ", dto, e.fillInStackTrace());
        }
    }

}
