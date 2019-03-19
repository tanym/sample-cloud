package com.bozy.cloud.sampleshardingjdbc.common;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Description:
 * Created by tym on 2019/03/18 15:13.
 */
public class ThreadPoolUtil {
    private static ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();

    static{
        //线程池所使用的缓冲队列
        poolTaskExecutor.setQueueCapacity(500);
        //线程池维护线程的最少数量
        poolTaskExecutor.setCorePoolSize(10);
        //线程池维护线程的最大数量
        poolTaskExecutor.setMaxPoolSize(1000);
        //线程池维护线程所允许的空闲时间
        poolTaskExecutor.setKeepAliveSeconds(60);
        poolTaskExecutor.initialize();
    }

    public static void execute(Thread thread){
        poolTaskExecutor.execute(thread);
    }

    public static void execute(Runnable runnable){
        poolTaskExecutor.execute(runnable);
    }
}
