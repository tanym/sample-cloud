package com.bozy.cloud;

import feign.Request;
import feign.Retryer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

//@EnableCircuitBreaker or @EnableHystrix(内部嵌套的还是注解@EnableCircuitBreaker)
//@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(exclude = ThymeleafAutoConfiguration.class) //引入了jar包没有使用的情况下,需要排除掉自动加载配置,否则springboot会启动自动加载.FreeMarkerAutoConfiguration.class
//@SpringCloudApplication
public class FeignClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignClientApplication.class, args);
    }
//    @Bean
    Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory =  new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setReadTimeout(10000);
        httpRequestFactory.setConnectTimeout(5000);
        return new RestTemplate(httpRequestFactory);
    }

    @Bean
    Request.Options requestOptions(Environment env){
        int ribbonReadTimeout = env.getProperty("ribbon.ReadTimeout", int.class, 60000);
        int ribbonConnectionTimeout = env.getProperty("ribbon.ConnectTimeout", int.class, 30000);

        return new Request.Options(ribbonConnectionTimeout, ribbonReadTimeout);
    }
}
