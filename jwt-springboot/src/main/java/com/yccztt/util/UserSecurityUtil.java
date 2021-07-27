package com.yccztt.util;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yccztt.domain.JWTEntity;
import com.yccztt.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.ZoneId;

/**
 * @Author hyz
 * @Date 2021/7/26
 */
@Component
public class UserSecurityUtil {

    //设置小于多少时间自动更新token
    @Value("${jwt.validate-time}")
    private long validateTime;

    @Autowired
    private JWTService jwtService;

    /**
     * 校验token
     * @param req
     * @param resp
     * @return
     */
    public boolean verifyWebToken(HttpServletRequest req, HttpServletResponse resp) {
        //获取请求头中的 authorization 信息
        String token = req.getHeader("authorization");
        if (token == null) {
            return false;
        }

        //解析token
        DecodedJWT jwtToken = JWTUtil.decode(token);
        if (jwtToken == null) {
            return false;
        }

        // 获取token中的用户id
        String uid = jwtToken.getSubject();
        //根据id获取JWT实体类
        JWTEntity queryJwt = jwtService.queryJwt(uid);

        try {
            //校验token
            JWTUtil.verifyToken(token);
        } catch (SignatureVerificationException e) {
            //签名校验异常
            return false;
        } catch (TokenExpiredException e) {
            //token过期,判断是否符合获得刷新Token的条件
            String newToken = JWTUtil.getRefreshToken(jwtToken, queryJwt);
            if (newToken == null) {
                jwtService.deleteJwt(uid);
                return false;
            }
            //否则说明符合token刷新条件,设置返回头部的authorization,并返回true
            resp.setHeader("authorization", newToken);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        // 校验成功,判断是否为不记住密码,且Token剩余有效时间小于某个特定值
        if (!queryJwt.getIsRemember()) {
            Instant exp = queryJwt.getLastLoginTime().atZone(ZoneId.systemDefault()).toInstant();
            Instant now = Instant.now();
            //若小于某个特定时间,则刷新Token
            if (now.getEpochSecond() - exp.getEpochSecond() <= validateTime) {
                token = JWTUtil.getRefreshToken(jwtToken);
            }
        }
        //设置返回头中的token
        resp.setHeader("authorization", token);
        return true;
    }

}
