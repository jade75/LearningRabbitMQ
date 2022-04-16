package ie.dkit.d00216118.demomessage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("ie.dkit.d00216118.demomessage.mapper")
public class DemoMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMessageApplication.class, args);
    }

}
