package com.lucien.myspringboot.service;

import com.lucien.myspringboot.model.DelayEvent;
import com.lucien.myspringboot.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author huoershuai
 * @Date 2020/5/12
 */
@Service
@Slf4j
public class DemoService {

    private static final long DEFAULT_DELAY_MILLS_SECOND = 60 * 1000;

    @Autowired
    private RedisDelayQueueService<User> redisDelayQueueService;

    /**
     * 执行延迟任务内容
     * @param user
     * @return
     */
    public String updateDB(User user) {
        log.info("DemoService updateDB user:{}", user);
        return user.getUsername();
    }

    /**
     * 增加延迟提醒
     * @param username
     * @param age
     * @param delayMills
     */
    public void addDelayJob(String username, int age, long delayMills) throws Exception {
        if (!StringUtils.isNotBlank(username)) {
            log.warn("addDelayJob warn, username is null!!!");
            throw new IllegalStateException("username is null, please check params");
        }
        User user = User.builder()
                .username(username)
                .age(age)
                .createTime(new Date())
                .updateTime(new Date())
                .delayMills(delayMills)
                .build();
        int slot = redisDelayQueueService.getCurrentSlot(user.getUpdateTime().getTime() + delayMills);
        DelayEvent<User> delayEvent = new DelayEvent<>();
        delayEvent.setKey(user.getUsername());
        delayEvent.setSlot(slot);
        delayEvent.setData(user);
        delayEvent.setCycleNum(redisDelayQueueService.getCycleNumber(delayMills));
        try {
            redisDelayQueueService.addDelayEvent(delayEvent);
            log.info("add slot:{}", slot);
        } catch (Exception e) {
            log.error("addDelayJob error, key:{}, slot:{}, delayEvent:{}, e:{}", user.getUsername(), slot, delayEvent, e);
            throw new IllegalStateException("addDelayJob error, delayEvent:{" + delayEvent + "}, e:{" + e.getMessage() + "}");
        }

    }

    public static void main(String[] args) {
        List<Integer> rawList = new ArrayList<Integer>(){{
            add(1);add(2);
            add(3);add(4);
            add(9);add(8);
            add(7);add(6);
        }};

        for (int i = 0; i < rawList.size(); i++) {
            for (int j = i + 1; j < rawList.size(); j++) {
                if (rawList.get(i) < rawList.get(j)) {
                    int tmp = rawList.get(i);
                    rawList.set(i, rawList.get(j));
                    rawList.set(j, tmp);
                }
            }
        }
        System.out.println(rawList);
    }
}
