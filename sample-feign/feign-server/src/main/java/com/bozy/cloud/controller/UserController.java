package com.bozy.cloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bozy.cloud.constants.Constants;
import com.bozy.cloud.domain.User;
import com.bozy.cloud.service.UserService;
import com.bozy.cloud.utils.SpringMVCUtil;
import com.bozy.cloud.vo.OutPutObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Description: 用户中心服务提供者
 * Created by tym on 2018/08/20 17:31.
 */
@RestController
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);

//    @Autowired
//    private DiscoveryClient discoveryClient;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam("name") String name){
       /*try{
           List<String> serviceIds = discoveryClient.getServices();
           log.info("======ServiceID:"+JSON.toJSONString(serviceIds));
           ServiceInstance instance = discoveryClient.getInstances(serviceIds.get(0)).get(0);
           log.info("Hello "+name+": "+ instance.getServiceId()+":"+instance.getHost()+":"+instance.getPort());
       }catch (Exception ex){
           ex.printStackTrace();
       }*/
        try {
            Thread.sleep(1001L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello "+name;
    }

    /**
     * Description: 增加用户信息
     * @Author tym
     * @Create Date: 2018/8/23/0023 下午 4:45
//     * @param userInfoJson
//     * @param response
     */
    @RequestMapping(value = "/addUser")
    public void addUser(@RequestBody String userInfoJson, HttpServletResponse response){
        OutPutObject opo = new OutPutObject(false, "系统繁忙，请稍后再试");
        try{
            if(StringUtils.isBlank(userInfoJson)){
                opo.setReturnCode("1001");
                opo.setReturnMsg("ERROR:参数错误");
                SpringMVCUtil.printWriter(response, JSON.toJSONString(opo));
                return;
            }
            User user = JSON.parseObject(userInfoJson, User.class);
            if(null == user){
               SpringMVCUtil.printWriter(response, JSON.toJSONString(opo));
               return;
            }
            userService.addUser(user);
            log.info("======invoke server addUser interface success======");
        }catch (Exception ex){
            log.error("======invoke server addUser interface occur exception:" + ex.getMessage());
        }
    }

    /**
     * Description: 获取用户集合
     * @Author tym
     * @Create Date: 2018/9/12/0012 下午 5:29
     * @param response
     */
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    public String getUserList(HttpServletResponse response, @RequestBody String condition_json){
        OutPutObject opo = new OutPutObject(false, "系统繁忙，请稍后再试");
        try{
            opo.setIsSuccess(true);
            opo.setReturnMsg(JSON.toJSONString(userService.getUserList()));
            opo.setReturnCode("200");
            Thread.sleep(3000L);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println("======invoke getUserList request params:" + condition_json +", repsponse msg:" + JSON.toJSONString(opo));
        /**如果抛出异常,则会进入熔断fallback处理***/
//        System.out.println(1/0);
        return JSON.toJSONString(opo);
    }

    /**
     * Description: 分页查询用户数据
     * @Author tym
     * @Create Date: 2018/9/17/0017 下午 3:46
     * @param request
     * @param response
     * @param condition_json
     * @return
     */
    @RequestMapping(value = "/findUsersByPage", method = RequestMethod.POST)
    public String findUsersByPage(HttpServletRequest request, HttpServletResponse response, @RequestBody String condition_json){
       OutPutObject opo = new OutPutObject(false, Constants.SYSTEM_ERROR);
       try{
          if(StringUtils.isBlank(condition_json)){
              opo.setReturnMsg("参数错误");
              return JSON.toJSONString(opo);
          }
          Map<String, Object> conditionMap = JSON.parseObject(condition_json, new TypeReference<Map<String, Object>>(){});
          PageInfo<User> pageInfo = userService.findUsersByPage(conditionMap);
          opo.setIsSuccess(true);
          opo.setReturnMsg(JSON.toJSONString(pageInfo));
       }catch (Exception ex){
           ex.printStackTrace();
       }
        log.info("======用户分页查询返回结果:" + JSON.toJSONString(opo));
        return JSON.toJSONString(opo);
    }

}
