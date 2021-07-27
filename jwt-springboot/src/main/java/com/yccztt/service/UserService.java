package com.yccztt.service;

import com.yccztt.domain.JWTEntity;
import com.yccztt.domain.JWTUser;
import com.yccztt.repository.JWTEntityRepository;
import com.yccztt.repository.JWTUserRepository;
import com.yccztt.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @Author hyz
 * @Date 2021/7/26
 */
@Service
public class UserService {

    @Autowired
    JWTUserRepository userRepository;

    @Autowired
    JWTEntityRepository entityRepository;

    /**
     * 根据ID查询用户
     * @param userId
     * @return
     */
    public JWTUser queryUserById(String userId) {
        JWTUser user = userRepository.findJWTUserById(userId);
        return user;
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public JWTUser queryUserByName(String username) {
        JWTUser user = userRepository.findJWTUserByUsername(username);
        return user;
    }

    /**
     * 生成Token
     * @param userId
     * @param isRemember
     * @return
     */
    @Transactional
    public String createWebToken(String userId, Boolean isRemember) {
        Instant now = Instant.now();
        String token = JWTUtil.createToken(userId, now);
        LocalDateTime lastLoginTime = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
        //存入到数据库中
        entityRepository.save(new JWTEntity(userId,token,lastLoginTime, isRemember != null && isRemember));
        return token;
    }

    /**
     * 删除Token
     * @param userId
     */
    @Transactional
    public void deleteWebToken(String userId) {
        entityRepository.deleteJWTEntityById(userId);
    }

}
