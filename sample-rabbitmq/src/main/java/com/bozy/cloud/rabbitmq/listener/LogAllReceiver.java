package com.bozy.cloud.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Description: log.all队列消费监听
 * Created by tym on 2018/10/15 17:48.
 */
@Component
@RabbitListener(queues = "log.all")
public class LogAllReceiver {
    @RabbitHandler
    public void process(String context){
        System.out.println("======log.all receiver the message:" + context);
    }
}
