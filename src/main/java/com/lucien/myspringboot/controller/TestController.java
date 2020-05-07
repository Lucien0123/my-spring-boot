package com.lucien.myspringboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author huoershuai
 * @Date 2020/5/7
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/test_simple", method = {RequestMethod.GET, RequestMethod.POST})
    public String test_simple(@RequestParam(name = "param", required = false) String param) {

        log.info("request param:{}", param);
        return param;
    }
}
