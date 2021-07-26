package com.yccztt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
