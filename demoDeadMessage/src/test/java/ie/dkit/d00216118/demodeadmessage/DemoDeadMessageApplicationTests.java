package ie.dkit.d00216118.demodeadmessage;

import ie.dkit.d00216118.demodeadmessage.service.DeadOrderPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoDeadMessageApplicationTests {

    @Autowired
    DeadOrderPublisher deadOrderPublisher;

    @Test
    void test() {
        deadOrderPublisher.sendMsg(5);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
