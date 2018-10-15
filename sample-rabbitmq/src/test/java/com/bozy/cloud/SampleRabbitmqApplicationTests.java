package com.bozy.cloud;

import com.bozy.cloud.rabbitmq.Sender;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleRabbitmqApplicationTests {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private Sender sender;

    @Test
    public void contextLoads() {
    }

    @Test
    public void sendFanoutMsg(){
        String msgText = "Rabbit say hello to u!";
        sender.send(msgText);
    }

    @Test
    public void sendTopicMsg(){
        String msgText = sdf.format(new Date()) + " Rabbit say to u :";
        String routingKey = "log.error";
        sender.sendTopicExchange(routingKey, msgText+routingKey);
    }

}
