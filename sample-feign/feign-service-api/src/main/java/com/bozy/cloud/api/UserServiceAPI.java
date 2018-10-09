package com.bozy.cloud.api;

import com.bozy.cloud.hystrix.UserServiceAPIHystrix;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description: 用户中心微服务APIHystrixCommand
 * Created by tym on 2018/08/21 16:08.
 * Note* @FeignClient中的path参数ep: /bozy或者bozy都可以,有无斜杠‘/’均可访问到.
 */
@FeignClient(value = "feign-server", path = "bozy", fallback = UserServiceAPIHystrix.class)
public interface UserServiceAPI {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    String sayHello(@RequestParam("name") String name);

    @RequestMapping(value = "/getUserList")
    String getUserList(@RequestBody String condition_json);

}
