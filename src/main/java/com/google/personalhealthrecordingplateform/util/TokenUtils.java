package com.google.personalhealthrecordingplateform.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author W&F
 */
@Slf4j
@Component
public class TokenUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;


    public String generateToken(UserDetails userDetails) {
        log.info("生成token");
        Map<String, Object> map = new HashMap<>();
        map.put("username", userDetails.getUsername());
        map.put("created", new Date());
        return generateToken(map);
    }

    public String generateToken(Map<String, Object> map) {
        return Jwts.builder()
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    /**
     * 该方法是否有判断该token是否为我们服务器发送的token
     * 若不是我们服务器生成的token，那么调用ta会返回null
     *
     * @param token
     * @return
     */
    public Claims getTokenBody(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsernameByToken(String token) {
        log.info("用户名：" + this.getTokenBody(token).get("username"));
        return (String) this.getTokenBody(token).get("username");
    }

    public boolean isExpired(String token) {
        return this.getTokenBody(token).getExpiration().before(new Date());
    }

    /**
     * up的设置过期时间函数应该有问题，我的应该才是对的。
     *
     * @return 重新生成的token
     */
    public String refreshToken(String token) {
        log.info("token过期，生成新的token");
        Claims claims = this.getTokenBody(token);
        claims.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000));
        return generateToken(claims);
    }

    /**
     * 判断该token是否是本服务器生成的
     *
     * @param token JWT
     * @return
     */
    public boolean isLegal(String token) {
        return this.getTokenBody(token) == null ? false : true;
    }
}
