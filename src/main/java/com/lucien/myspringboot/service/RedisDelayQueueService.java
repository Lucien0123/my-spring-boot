package com.lucien.myspringboot.service;

import com.lucien.myspringboot.model.DelayEvent;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author huoershuai
 * @Date 2020/5/11
 */
@Service
public class RedisDelayQueueService {

    private static final String DELAY_QUEUE_3600_KEY = "DELAY_QUEUQ_3600_KEY";
    private static final int DELAY_QUEUE_3600_LENGTH = 3600;

    /**
     * 模拟redis中的存储，每个队列一个key
     */
    private static Map<String, List<Set<DelayEvent>>> simulateRedisData;

    static {
        simulateRedisData = new HashMap<>();
        /* 初始化一个3600长度的队列，每个slot代表一秒 */
        List<Set<DelayEvent>> delayQueue = new ArrayList<>(DELAY_QUEUE_3600_LENGTH);
        delayQueue.add(0, new HashSet<>());
        simulateRedisData.put(DELAY_QUEUE_3600_KEY, delayQueue);
    }

    /**
     * 获取指定slot的任务
     * @param key
     * @param slot
     * @return
     */
    public Set<DelayEvent> getBySlot(String key, int slot) {
        if (StringUtils.isNotBlank(key) && DELAY_QUEUE_3600_LENGTH >= slot) {
            List<Set<DelayEvent>> values = simulateRedisData.get(key);
            if (!CollectionUtils.isEmpty(values)) {
                return values.get(slot) == null ? new HashSet<>() : values.get(slot);
            }
        }
        return null;
    }

    /**
     *
     * @param key
     * @param slot
     * @param delayEvent
     * @throws Exception
     */
    public void addDelayEvent(String key, int slot, DelayEvent delayEvent) throws Exception {
        if (!StringUtils.isNotBlank(key) || DELAY_QUEUE_3600_LENGTH < slot) {
            throw new IllegalStateException("key or slot invalid, key: " + key + " slot: " + slot);
        }
        List<Set<DelayEvent>> values = simulateRedisData.get(key);
        if (values == null) {
            throw new IllegalStateException("don`t find delay queue with key: " + key);
        }
        Set<DelayEvent> eventSet = values.get(slot);
        if (eventSet == null) {
            eventSet = new HashSet<>();
        }

        eventSet.add(delayEvent);

        /* 验证任务是否存在，改成map，使用key去重 */
    }
}
