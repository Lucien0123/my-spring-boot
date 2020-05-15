package com.lucien.myspringboot.service;

import com.lucien.myspringboot.model.DelayEvent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author huoershuai
 * @Date 2020/5/11
 */
@Service
@Slf4j
@Data
public class RedisDelayQueueService<T> {

    /**
     * 队列的redis key
     */
    public static final String DELAY_QUEUE_3600_KEY = "DELAY_QUEUQ_3600_KEY";
    /**
     * 队列长度
     */
    public static final int DELAY_QUEUE_3600_LENGTH = 3600;
    /**
     * 延迟队列执行频率，1次 / 1000ms
     */
    public static final int DELAY_QUEUE_STEP_TIME = 1000;

    /**
     * 模拟redis中的存储，每个队列一个key
     */
    private Map<String, Map<Integer, Map<String, DelayEvent<T>>>> simulateRedisData;

    public RedisDelayQueueService() {
        simulateRedisData = new HashMap<>();
        simulateRedisData.put(DELAY_QUEUE_3600_KEY, new HashMap<>());
    }

    /**
     * 获取指定slot的任务
     * @param key
     * @param slot
     * @return
     */
    public Map<String, DelayEvent<T>> getBySlot(String key, int slot) {
        if (StringUtils.isNotBlank(key) && DELAY_QUEUE_3600_LENGTH >= slot) {
            Map<Integer, Map<String, DelayEvent<T>>> values = simulateRedisData.get(key);
            if (!CollectionUtils.isEmpty(values)) {
                return values.get(slot) == null ? new HashMap<>() : values.get(slot);
            }
        }
        return null;
    }

    /**
     *
     * @param delayEvent
     * @throws Exception
     */
    public synchronized void addDelayEvent(DelayEvent<T> delayEvent) throws Exception {
        if (delayEvent == null || !StringUtils.isNotBlank(delayEvent.getKey())) {
            throw new IllegalStateException("DelayEvent is null");
        }
        if (!StringUtils.isNotBlank(delayEvent.getKey()) || DELAY_QUEUE_3600_LENGTH < delayEvent.getSlot()) {
            throw new IllegalStateException("key or slot invalid, key: " + delayEvent.getKey() + " slot: " + delayEvent.getSlot());
        }
        Map<Integer, Map<String, DelayEvent<T>>> values = simulateRedisData.remove(DELAY_QUEUE_3600_KEY);
        if (values == null) {
            throw new IllegalStateException("don`t find delay queue with key: " + delayEvent.getKey());
        }
        Map<String, DelayEvent<T>> eventMap = values.get(delayEvent.getSlot());
        if (eventMap == null) {
            eventMap = new HashMap<>();
        } else {
            // 防止业务Id重复
            eventMap.remove(delayEvent.getKey());
        }
        eventMap.put(delayEvent.getKey(), delayEvent);
        values.put(delayEvent.getSlot(), eventMap);
        simulateRedisData.put(DELAY_QUEUE_3600_KEY, values);
    }

    /**
     * 根据
     * @param timeMillis
     * @return
     */
    public int getCurrentSlot(long timeMillis) {
        return (int) (timeMillis / 1000 % 3600);
    }


    public int getCycleNumber(long delayTime) {
        return (int) (delayTime / (DELAY_QUEUE_3600_LENGTH * 1000));
    }
}
