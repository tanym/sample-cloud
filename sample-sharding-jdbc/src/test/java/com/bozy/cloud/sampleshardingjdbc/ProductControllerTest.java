package com.bozy.cloud.sampleshardingjdbc;

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
 * Description: 商品测试类
 * Created by tym on 2019/03/18 17:45.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ProductControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Description: 添加商品
     * @Author tym
     * @Create Date: 2019/3/18/0018 下午 5:47
     * @throws Exception
     */
    @Test
    public void testAddProduct() throws Exception {
        mockMvc.perform(get("/product/add").accept(MediaType.APPLICATION_JSON_UTF8))
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
