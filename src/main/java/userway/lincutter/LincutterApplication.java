package userway.lincutter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import userway.lincutter.config.MongoConfig;

@SpringBootApplication
@Import(MongoConfig.class)
public class LincutterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LincutterApplication.class, args);
    }

}
