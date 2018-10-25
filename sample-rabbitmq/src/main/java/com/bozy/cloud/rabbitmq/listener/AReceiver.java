package com.bozy.cloud.rabbitmq.listener;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;

import java.util.Map;

/**
 * Description: A消息接受者
 * Created by tym on 2018/10/13 16:57.
 */
@Component
@RabbitListener(queues = "aQueue")
public class AReceiver {

    @RabbitHandler
    public void process(String context){
        System.out.println("===aQueue receive the message by String : " + context);
    }

    /***
     * 不同的参数类型采用不同的handler处理
     */
    @RabbitHandler
    public void process(Map map){
        System.out.println("===aQueue receive the message by map" + map);
    }

    /**
     * Description: 无论content-type是何类型,Message类型接收消息都不会报错.
     * @Author tym
     * @Create Date: 2018/10/23/0023 下午 3:09
     * @param message
     */
    @RabbitHandler
    public void process(Message message){
        System.out.println("====aQueue receive the message by Message : " + JSON.toJSONString(message.getBody()));
    }

}
