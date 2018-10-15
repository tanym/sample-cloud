package com.bozy.cloud.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Created by tym on 2018/10/13 17:00.
 */
@Component
@RabbitListener(queues = "bQueue")
public class BReceiver {

    @RabbitHandler
    public void process(String context){
        System.out.println("===bQueue===" + context);
    }
}
