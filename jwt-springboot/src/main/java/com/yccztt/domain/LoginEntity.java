package com.yccztt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录类
 * @Author hyz
 * @Date 2021/7/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginEntity {

    private String username;
    private String password;
    private Boolean isRemember;

}
