package com.bozy.cloud;

import com.bozy.cloud.rabbitmq.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleRabbitmqApplicationTests {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Executor executor = Executors.newCachedThreadPool();//启用多线程

    @Autowired
    private ThreadPoolTaskExecutor poolTaskExecutor;/**线程池任务器**/

    @Autowired
    private Sender sender;

    private int count = 0;

    @Test
    public void contextLoads() {
    }

    @Test
    public void sendFanoutMsg(){
        String msgText = "Rabbit say hello to u!";
        sender.send(msgText);
    }

    @Test
    public void sendTopicMsg() throws Exception{
        for(int i=0; i< 100; i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("========"+ count++ +"======");
                    String msgText = sdf.format(new Date()) + " Test TWO Rabbit Consumer say to u :";
                    String routingKey = "log.error.test";
                    sender.sendTopicExchange(routingKey, msgText+routingKey);
                }
            });
        }

        Thread.sleep(100000);
    }

    @Test
    public void sendTopicMsgByCountDownLatch(){
        //定义线程数
        int subThreadNum = 100;
        //取得一个倒计时器，从200开始
        final CountDownLatch countDownLatch = new CountDownLatch(subThreadNum);
        try {
            long start_time = System.currentTimeMillis();
            //开启200个线程,并发执行
            for (int i = 0; i < subThreadNum; i++) {
                poolTaskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            String msgText = sdf.format(new Date()) + " Test TWO Rabbit Consumer say to u :";
                            String routingKey = "log.error.test";
                            sender.sendTopicExchange(routingKey, msgText+routingKey);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }finally {
                            //线程结束时，将计时器减一
                            countDownLatch.countDown();
                        }
                    }
                });
            }
            countDownLatch.await();
            System.out.println("===耗时:" + (System.currentTimeMillis() - start_time) / 1000 + "秒");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
