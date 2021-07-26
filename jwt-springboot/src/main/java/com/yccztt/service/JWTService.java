package com.yccztt.service;

import com.yccztt.domain.JWTEntity;
import com.yccztt.repository.JWTEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author hyz
 * @Date 2021/7/26
 */
@Service
public class JWTService {

    @Autowired
    JWTEntityRepository jwtEntityRepository;

    /**
     * 保存jwt数据
     * @param entity
     */
    public void saveJwt(JWTEntity entity) {
        jwtEntityRepository.save(entity);
    }

    /**
     * 根据id查询jwt数据
     * @param userId
     * @return
     */
    public JWTEntity queryJwt(String userId) {
        JWTEntity entity = jwtEntityRepository.findJWTEntityById(userId);
        return entity;
    }

    /**
     * 删除jwt数据
     * @param userId
     */
    public void deleteJwt(String userId) {
        jwtEntityRepository.deleteJWTEntityById(userId);
    }

}
