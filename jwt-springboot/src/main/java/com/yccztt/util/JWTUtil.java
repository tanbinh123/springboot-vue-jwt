package com.yccztt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.yccztt.domain.JWTEntity;
import com.yccztt.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * JWT生成类
 * @Author hyz
 * @Date 2021/7/26
 */
@Component
public class JWTUtil {

    private static String secret;
    private static long expiration;
    private static long rememberTime;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JWTUtil.secret = secret;
    }

    @Value("${jwt.expiration}")
    public void setExpiration(long expiration) {
        JWTUtil.expiration = expiration;
    }

    @Value("${jwt.remember-time}")
    public void setRememberTime(long rememberTime) {
        JWTUtil.rememberTime = rememberTime;
    }

    @Autowired
    private static JWTService jwtService;

    /**
     * 生成token
     * @param userId
     * @param issueAt
     * @return
     */
    public static String createToken(String userId, Instant issueAt) {
        Instant exp = issueAt.plusSeconds(expiration);
        return createToken(userId, issueAt, exp);
    }

    /**
     * 校验token
     * @param token
     */
    public static void verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWTUtil.secret)).build();
        verifier.verify(token);
    }

    /**
     * 刷新token-token失效
     * @param jwtToken
     * @param jwtEntity
     * @return
     */
    public static String getRefreshToken(DecodedJWT jwtToken, JWTEntity jwtEntity) {
        Instant exp = jwtEntity.getLastLoginTime().atZone(ZoneId.systemDefault()).toInstant();
        Instant now = Instant.now();
        //如果超过记住密码的期限或者是设置未记住密码,直接返回 null
        if (!jwtEntity.getIsRemember() || (now.getEpochSecond() - exp.getEpochSecond()) > rememberTime) {
            return null;
        }
        //否则生成刷新的Token,并重新设置数据库中JWT的信息
        Instant newExp = exp.plusSeconds(expiration);
        String token = createToken(jwtToken.getSubject(), now, newExp);
        LocalDateTime lastLoginTime = getLastLoginTime(newExp);
        //把新的token存入到数据库中
        jwtService.saveJwt(new JWTEntity(jwtToken.getSubject(),token, lastLoginTime, true));

        return token;
    }

    /**
     * 刷新token-有效时间小于特定值
     * @param jwtToken
     * @return
     */
    public static String getRefreshToken(DecodedJWT jwtToken) {
        Instant now = Instant.now();
        Instant newExp = now.plusSeconds(expiration);
        String token = createToken(jwtToken.getSubject(), now, newExp);
        //把新的token存入到数据库中
        jwtService.saveJwt(new JWTEntity(jwtToken.getSubject(),token, getLastLoginTime(now), false));
        return token;
    }

    /**
     * 生成token
     * @param sub
     * @param iat
     * @param exp
     * @return
     */
    private static String createToken(String sub, Instant iat, Instant exp) {
        return JWT.create()
                .withClaim("sub", sub)
                .withClaim("iat", Date.from(iat))
                .withClaim("exp", Date.from(exp))
                .sign(Algorithm.HMAC256(JWTUtil.secret));
    }

    /**
     * 返回token解码信息
     * @param token
     * @return
     */
    public static DecodedJWT decode(String token){
        try {
            return JWT.decode(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前时间
     * @param newExp
     * @return
     */
    private static LocalDateTime getLastLoginTime(Instant newExp) {
        return LocalDateTime.ofInstant(newExp, ZoneId.systemDefault());
    }

}
