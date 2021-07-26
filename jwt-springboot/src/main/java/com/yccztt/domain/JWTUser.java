package com.yccztt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户类
 * @Author hyz
 * @Date 2021/7/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jwt_user")
public class JWTUser {

    @Id
    private String id;
    private String username;
    private String password;

}
