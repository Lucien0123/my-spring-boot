package com.lucien.myspringboot.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Author huoershuai
 * @Date 2020/5/12
 */
@Data
@Builder
public class User {

    private String username;
    private int age;
    private Date createTime;
    private Date updateTime;
    private long delayMills;
}
