package com.lucien.myspringboot.job;

import com.lucien.myspringboot.model.DelayEvent;
import com.lucien.myspringboot.model.User;
import com.lucien.myspringboot.service.DemoService;
import com.lucien.myspringboot.service.RedisDelayQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author huoershuai
 * @Date 2020/5/12
 */
@Slf4j
@Component
public class DelayQueueJob implements InitializingBean {

    @Autowired
    RedisDelayQueueService<User> redisDelayQueueService;
    @Autowired
    ThreadPoolExecutor executor;
    @Autowired
    DemoService demoService;


    @Override
    public void afterPropertiesSet() throws Exception {
        execute();
    }

    @Scheduled(fixedDelay = RedisDelayQueueService.DELAY_QUEUE_STEP_TIME)
    public void execute() {
        doJob();
    }


    private void doJob() {
        int currentSlot = redisDelayQueueService.getCurrentSlot(System.currentTimeMillis());
        Map<String, DelayEvent<User>> eventMap = redisDelayQueueService.getBySlot(RedisDelayQueueService.DELAY_QUEUE_3600_KEY, currentSlot);
        log.info("doJob currentSlot:{}, slot size:{}", currentSlot, eventMap);
        if (!CollectionUtils.isEmpty(eventMap)) {
            eventMap.forEach((key, event) -> {
                log.info("do job key:{}, data:{}", key, event.getData());
                executor.execute(() -> {
                    log.info("sync execute job,  content:{}", event.getData());
                    demoService.updateDB(event.getData());
                });
            });
        }
    }
}
