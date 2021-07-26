package com.yccztt.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
    private String id;
    @Column(name = "token")
    private String token;
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "lastLoginTime")
    private LocalDateTime lastLoginTime;
    @Column(name = "isRemember")
    private String isRemember;

}
