package com.lucien.myspringboot.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author huoershuai
 * @Date 2020/6/3
 */
@Component
@Slf4j
public class DemoCache implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    /**
     * 每分钟执行一次
     */
    @Scheduled(fixedDelay = 60000)
    public void refresh() {
        job();
    }

    private void job() {
        log.info("do something....");
    }
}
