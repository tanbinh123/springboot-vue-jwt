package com.yccztt.repository;

import com.yccztt.domain.JWTUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author hyz
 * @Date 2021/7/26
 */
public interface JWTUserRepository extends JpaRepository<JWTUser, Long> {

    JWTUser findJWTUserById(String id);

    JWTUser findJWTUserByUsername(String name);

}
