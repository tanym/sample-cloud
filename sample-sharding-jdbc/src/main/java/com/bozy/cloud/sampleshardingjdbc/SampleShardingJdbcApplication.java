package com.bozy.cloud.sampleshardingjdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@ImportResource(locations = {"classpath:application.xml"})
public class SampleShardingJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleShardingJdbcApplication.class, args);
    }

}
