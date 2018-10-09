package com.bozy.cloud;

import com.alibaba.fastjson.JSON;
import com.bozy.cloud.domain.Country;
import com.bozy.cloud.domain.User;
import com.bozy.cloud.enums.UserSexEnum;
import com.bozy.cloud.service.CountryService;
import com.bozy.cloud.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// extends AbstractTransactionalJUnit4SpringContextTests
@RunWith(SpringRunner.class)
@SpringBootTest
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@EnableTransactionManagement
//@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true) /**expose-proxy 为false->目标对象内部的自我调用将无法实施切面中的增强**/
public class FeignServerApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private Environment environment;

    @Test
    public void contextLoads() {

        System.out.println(environment.getProperty("spring.datasource.type"));
    }

    @Test
    public void testAddUser(){
        try {
            System.out.println(environment.getProperty("spring.datasource.type"));
            User user = new User("why34", "343434", UserSexEnum.MAN.getValue());
            userService.addUser(user);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void testGetUserList(){
        try{
            System.out.println(JSON.toJSONString(userService.getUserList()));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void addCountry(){
        Country country = new Country();
        country.setFull_name("中国");
        country.setSimple_name("ZN");
        country.setCreate_time(new Date());
        country.setModify_time(new Date());
        countryService.insert(country);
    }

    @Test
    public void saveUserAndCountry() throws Exception{
        User user = new User();
        user.setUserName("Wade");
        user.setNickName("闪电侠");
        user.setPassWord("66666");
        user.setUserSex(UserSexEnum.MAN.getValue());

        Country country = new Country();
        country.setSimple_name("ARG");
        country.setFull_name("阿根廷");
        country.setParent_id(-1);
        country.setCreate_time(new Date());
        country.setModify_time(new Date());

        userService.saveUserAndCountry(user, country);
    }

    /**
     * Description: 分页查询用户数据
     * @Author tym
     * @Create Date: 2018/9/17/0017 下午 3:58
     */
    @Test
    public void findUsersByPage() throws Exception{
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("currentPage", 1);
        condition.put("pageSize", 10);
        condition.put("userName", "bozy");
        PageInfo<User> pageInfo = userService.findUsersByPage(condition);
        System.out.println(JSON.toJSONString(pageInfo));
    }

}
