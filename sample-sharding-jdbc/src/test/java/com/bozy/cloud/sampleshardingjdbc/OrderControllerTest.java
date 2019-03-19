package com.bozy.cloud.sampleshardingjdbc;

import com.bozy.cloud.sampleshardingjdbc.common.ThreadPoolUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Description:
 * Created by tym on 2019/03/12 17:45.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:application.xml"})  //指定Bean的配置文件信息
@WebAppConfiguration
public class OrderControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAddOrder() throws Exception {
        /*mockMvc.perform(post("/add").contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();*/
        mockMvc.perform(get("/order/add").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
        /*for (int i = 0; i < 33; i++) {
            ThreadPoolUtil.execute(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mockMvc.perform(get("/order/add").accept(MediaType.APPLICATION_JSON_UTF8))
                                .andExpect(status().isOk())
                                .andDo(print())
                                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

}
