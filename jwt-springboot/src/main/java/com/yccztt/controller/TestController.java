package com.yccztt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author hyz
 * @Date 2021/7/26
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }

    @GetMapping("/excHello")
    public Map<String,Object> excHello() {
        Map<String,Object> map = new HashMap<>();
        map.put("message", "不经过校验!");
        map.put("code", 200);
        return map;
    }

}
