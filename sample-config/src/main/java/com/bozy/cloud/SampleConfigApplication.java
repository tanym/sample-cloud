package com.bozy.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SampleConfigApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(SampleConfigApplication.class, args);
        // 测试用数据，仅用于本文测试使用
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        jdbcTemplate.execute("delete from properties");

        jdbcTemplate.execute(
                "INSERT INTO properties VALUES(1, 'com.didispace.message', 'test-stage-master', 'config-client', 'stage', 'master')"
        );
        jdbcTemplate.execute(
                "INSERT INTO properties VALUES(2, 'com.didispace.message', 'test-online-master', 'config-client', 'online', 'master')"
        );
        jdbcTemplate.execute(
                "INSERT INTO properties VALUES(3, 'com.didispace.message', 'test-online-develop', 'config-client', 'online', 'develop')"
        );
        jdbcTemplate.execute(
                "INSERT INTO properties VALUES(4, 'com.didispace.message', 'hello-online-master', 'hello-service', 'online', 'master')"
        );
        jdbcTemplate.execute(
                "INSERT INTO properties VALUES(5, 'com.didispace.message', 'hello-online-develop', 'hello-service', 'online', 'develop')"
        );
    }
}
