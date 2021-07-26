package com.yccztt.controller;

import com.yccztt.domain.JWTUser;
import com.yccztt.domain.LoginEntity;
import com.yccztt.service.UserService;
import com.yccztt.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author hyz
 * @Date 2021/7/26
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户登录
     * @param loginEntity
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ResultUtil<String> login(@RequestBody LoginEntity loginEntity, HttpServletResponse response) {
        ResultUtil<String> resultUtil = new ResultUtil<>();

        JWTUser jwtUser = userService.queryUserByName(loginEntity.getUsername());

        //判断返回的用户密码与传入密码是否匹配
        boolean isOk = jwtUser != null && jwtUser.getPassword().equals(loginEntity.getPassword());
        if (!isOk) {
            resultUtil.setErrMsg("用户名或密码错误");
            resultUtil.setStatus(false);
            return resultUtil;
        }

        //否则生成Token,并设置头部的authorization信息
        String token = userService.createWebToken(jwtUser.getId(), loginEntity.getIsRemember());
        response.setHeader("authorization", token);
        resultUtil.setData(jwtUser.getId());

        return resultUtil;
    }

    /**
     * 注销登录
     * @param userId
     * @return
     */
    @GetMapping("/exit")
    public String logout(String userId) {
        userService.deleteWebToken(userId);
        return "注销成功";
    }

}
