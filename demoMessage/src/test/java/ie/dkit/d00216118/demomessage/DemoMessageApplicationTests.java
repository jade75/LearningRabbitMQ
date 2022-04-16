package ie.dkit.d00216118.demomessage;

import ie.dkit.d00216118.demomessage.mapper.UserMapper;
import ie.dkit.d00216118.demomessage.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoMessageApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void test() {
        User user=userMapper.selectByUserNamePassword("jack","123456");
    }

}
