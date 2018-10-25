package com.bozy.cloud.rabbitmq.listener;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Description: 消息统一处理入口(消费客户端)
 * Created by tym on 2018/10/23 15:37.
 */
/*@Component
public class MessageHandler {

    private static final Logger logger = Logger.getLogger(MessageHandler.class);

    @RabbitListener(queues = "log.error")
    public void handleMessage(Message message){
        System.out.println("===MessageHandler receive log.error:" + new String(message.getBody()));
    }

}*/
