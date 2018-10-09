package com.bozy.cloud.hystrix;

import com.bozy.cloud.api.UserServiceAPI;
import org.springframework.stereotype.Component;

/**
 * Description: 用户微服务接口熔断处理
 * Created by tym on 2018/09/12 11:18.
 */
@Component
public class UserServiceAPIHystrix implements UserServiceAPI {

    @Override
    public String sayHello(String name) {
        return "sorry "+ name +",it's my pot!";
    }

    @Override
    public String getUserList(String condition_json) {
        return "fallback->getUserList:" + condition_json;
    }

}
