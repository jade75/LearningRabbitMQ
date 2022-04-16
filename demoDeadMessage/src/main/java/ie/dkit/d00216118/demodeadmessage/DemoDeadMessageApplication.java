package ie.dkit.d00216118.demodeadmessage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("ie.dkit.d00216118.demodeadmessage.mapper")
@SpringBootApplication
public class DemoDeadMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoDeadMessageApplication.class, args);
    }

}
