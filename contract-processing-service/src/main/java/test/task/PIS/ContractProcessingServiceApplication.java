package test.task.PIS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import test.task.PIS.config.ApplicationProperties;
import test.task.PIS.config.RabbitProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class, RabbitProperties.class})
public class ContractProcessingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContractProcessingServiceApplication.class, args);
    }

}
