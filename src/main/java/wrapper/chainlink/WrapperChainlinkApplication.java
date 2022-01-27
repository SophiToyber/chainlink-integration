package wrapper.chainlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WrapperChainlinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(WrapperChainlinkApplication.class, args);
    }
    

}
