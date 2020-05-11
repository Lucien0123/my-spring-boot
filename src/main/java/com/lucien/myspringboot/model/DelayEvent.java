package com.lucien.myspringboot.model;

import lombok.Data;

/**
 * @Author huoershuai
 * @Date 2020/5/11
 */
@Data
public class DelayEvent<T> {

    /**
     * 环形中的下标
     */
    private int slot;

    /**
     * 等待圈数
     */
    private int cycleNum;

    /**
     * 业务数据
     */
    private T data;

    /**
     * 唯一标识
     */
    private String key;
}
