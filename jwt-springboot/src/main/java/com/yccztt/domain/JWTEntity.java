package com.yccztt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * JWT类，把生成的token放到数据库中，一般使用redis存储
 * @Author hyz
 * @Date 2021/7/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jwt_token")
public class JWTEntity {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "token")
    private String token;
    @Column(name = "lastLoginTime")
    private LocalDateTime lastLoginTime;
    @Column(name = "isRemember")
    private Boolean isRemember;

}
