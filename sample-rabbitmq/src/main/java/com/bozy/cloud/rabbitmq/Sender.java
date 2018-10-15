package com.bozy.cloud.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Created by tym on 2018/10/13 16:46.
 */
@Component
public class Sender {

    private static final String TOPIC_EXCHANGE_NAME ="log";
    private static final String FANOUT_EXCHANGE_NAME ="fanEx";

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * Description: 广播式
     * Note* 无需routingKey,需要提前绑定Exchange和Queue
     * @Author tym
     * @Create Date: 2018/10/15/0015 下午 3:05
     */
    public void send(String msgText){
        //向mq服务端发送消息，exchange为log，routingkey为log.error
//        String msgText = "Rabbit say hello to u!";
        /**向mq服务端发送消息,交换类型type为Fanout时,交换机是‘fanEx’,不关心routingKey的值,有无均可;**/
        amqpTemplate.convertAndSend(FANOUT_EXCHANGE_NAME, "", msgText);
    }

    /**
     * Description: 订阅式
     * Note* 需要routingKey,需要提前绑定好Exchange和Queue
     * @Author tym
     * @Create Date: 2018/10/15/0015 下午 4:13
     */
    public void sendTopicExchange(String routingKey, String msgText){
        amqpTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, routingKey, msgText);
    }

}
