package com.bozy.cloud.controller;

import com.alibaba.fastjson.JSON;
import com.bozy.cloud.api.UserServiceAPI;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Created by tym on 2018/08/21 16:25.
 */
@RefreshScope
@RestController
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);

    @Value("${from}")
    private String from;

    @Value("${aa.testkey}")
    private String testKey;

    @Autowired
    UserServiceAPI userServiceAPI;

    @RefreshScope
    @RequestMapping(value = "/sayHello/{name}", method = RequestMethod.GET)
    public String sayHello(@PathVariable(value = "name") String name){
        /**采用cloud-config配置中心的配置数据**/
        name = this.from;
        name = this.testKey;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("name", name);
        log.info("======[INFO]The vistor name is:" +JSON.toJSONString(resultMap));
        log.error("======[ERROR]The vistor name is:" +JSON.toJSONString(resultMap));
        return userServiceAPI.sayHello(name);
    }

//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "80000"),
//            @HystrixProperty(name = "execution.timeout.enabled",value = "true")
//    },commandKey="UserServiceAPI#getUserList(String)")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public String findAll(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> conditonMap = new HashMap<String, Object>();
        conditonMap.put("userName", "lisa");
        return userServiceAPI.getUserList(JSON.toJSONString(conditonMap));
    }

}
