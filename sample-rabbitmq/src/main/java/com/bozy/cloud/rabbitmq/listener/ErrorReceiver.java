package com.bozy.cloud.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Description: log.error队列消费监听
 * Created by tym on 2018/10/15 17:45.
 */
@Component
@RabbitListener(queues = "log.error")
public class ErrorReceiver {

    @RabbitHandler
    public void process(String context){
        System.out.println("Queue log.error receive the message:" + context);
    }
}
