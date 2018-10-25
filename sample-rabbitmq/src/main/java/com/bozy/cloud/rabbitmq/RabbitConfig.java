package com.bozy.cloud.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 配置 队列(queue)、交换器(exchange)、绑定(binding)
 * Created by tym on 2018/10/13 16:42.
 */
@Configuration
public class RabbitConfig {

    private static final String FANOUT_EXCHANGE_NAME = "fanEx";

    private static final String TOPIC_EXCHANGE_NAME = "log";

    private static final String QUEUE_LOG_ERROR = "log.error";

    private static final String QUEUE_LOG_ALL = "log.all";

    /**创建队列aQueue**/
    @Bean
    public Queue AQueue(){
        return new Queue("aQueue");
    }

    /**创建队列bQueue**/
    @Bean
    public Queue BQueue(){
        return new Queue("bQueue");
    }


    @Bean
    public Queue logErrorQueue(){
        return new Queue(QUEUE_LOG_ERROR);
    }

    @Bean
    public Queue logAllQueue(){
        return new Queue(QUEUE_LOG_ALL);
    }

    /**创建交换器fanoutExchange**/
    @Bean
    Exchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE_NAME);
    }

    /**创建主题订阅式交换器,
     * ->*（星号）: 可以(只能)匹配一个单词
     * ->#（井号）: 可以匹配多个单词(或者零个)
     * **/
    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    /**绑定FanoutExchange与AQueue**/
    @Bean
    Binding bindingAQueue(Queue AQueue, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(AQueue).to(fanoutExchange);
    }

    /**绑定FanoutExchange与BQueue**/
    @Bean
    Binding bindingBQueue(Queue BQueue, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(BQueue).to(fanoutExchange);
    }

    /**绑定TopicExchange指定routingKey**/
    @Bean
    Binding bindingLogError(Queue logErrorQueue, TopicExchange topicExchange){
        return BindingBuilder.bind(logErrorQueue).to(topicExchange).with("log.error.#");
    }

    /**绑定TopicExchange指定routingKey,‘.#’表示零个或若干个单词(log.test.ss),‘.*’表示匹配1个单词(log.aa).**/
    @Bean
    Binding bindingLogAll(Queue logAllQueue, TopicExchange topicExchange){
        return BindingBuilder.bind(logAllQueue).to(topicExchange).with("*.log");
    }

}
