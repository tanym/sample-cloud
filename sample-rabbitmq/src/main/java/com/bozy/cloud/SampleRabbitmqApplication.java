package com.bozy.cloud;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;

//@EnableRabbit
@SpringBootApplication(exclude = {GatewayAutoConfiguration.class})
public class SampleRabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleRabbitmqApplication.class, args);
    }
}
