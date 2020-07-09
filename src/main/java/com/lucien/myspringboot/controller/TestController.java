package com.lucien.myspringboot.controller;

import com.lucien.myspringboot.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author huoershuai
 * @Date 2020/5/7
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/test_simple", method = {RequestMethod.GET, RequestMethod.POST})
    public String test_simple(@RequestParam(name = "param", required = false) String param) {

        log.info("request param:{}", param);
        return param;
    }

    @RequestMapping(value = "/test_delay", method = {RequestMethod.GET, RequestMethod.POST})
    public String test_delay(@RequestParam(name = "username") String username,
                             @RequestParam(name = "age") int age,
                             @RequestParam(name = "delayTime") long delayTime) {

        log.info("test_delay request username:{}", username);
        try {
            demoService.addDelayJob(username, age, delayTime);
            return "success";
        } catch (Exception e) {
            log.error("test_delay addDelayJob error, username:{}, e:{}", username, e);
        }
        return "fail";
    }
}
