package com.bozy.cloud.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Description: A消息接受者
 * Created by tym on 2018/10/13 16:57.
 */
@Component
@RabbitListener(queues = "aQueue")
public class AReceiver {

    @RabbitHandler
    public void process(String context){
        System.out.println("===aQueue==" + context);
    }
}
