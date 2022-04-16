package ie.dkit.d00216118.demomessage.service;

import ie.dkit.d00216118.demomessage.dto.UserLoginDto;
import ie.dkit.d00216118.demomessage.mapper.UserMapper;
import ie.dkit.d00216118.demomessage.message.LogProducer;
import ie.dkit.d00216118.demomessage.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LogProducer logProducer;

    /**
     * 用户登录服务
     * @param dto
     * @return
     * @throws Exception
     */
    public Boolean login(UserLoginDto dto) throws Exception{
        User user=userMapper.selectByUserNamePassword(dto.getUserName(),dto.getPassword());
        if (user!=null){
            dto.setUserId(user.getId());
            logProducer.sendLogMsg(dto);
            return true;
        }else{
            return false;
        }
    }

}
