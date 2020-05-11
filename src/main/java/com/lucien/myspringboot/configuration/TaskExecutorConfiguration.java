package com.lucien.myspringboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.DefaultManagedAwareThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class TaskExecutorConfiguration {

    private final Integer CORE_POOL_SIZE = 16;
    private final Integer MAX_POOL_SIZE = 64;
    private final Integer QUEUE_CAPACITY = 200;
    private final Integer KEEP_ALIVE_SECOND = 60;
//    private final Boolean WAIT_FOR_TASKS_TO_COMPLETE_ON_SHUTDOWN = true;

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_SECOND, TimeUnit.SECONDS, new ArrayBlockingQueue<>(QUEUE_CAPACITY));
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolExecutor.setThreadFactory(new DefaultManagedAwareThreadFactory());
        return threadPoolExecutor;
    }
}
