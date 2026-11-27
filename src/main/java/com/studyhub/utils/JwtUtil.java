package com.studyhub.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final long expiration;

    public JwtUtil(
            @Value("${jwt.secret-base64}") String secret,
            @Value("${jwt.expiration}") long expiration) {

        this.key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret));

        this.expiration = expiration;
    }

    // 生成 JWT
    public String generateToken(Integer userId, String username) {

        Date now = new Date();

        return Jwts.builder()   //建造者模式
                .subject(userId.toString()) //设置主题，存放用户的唯一标识
                .claim("username", username) //自定义声明，解析时可以直接读取这个声明
                .issuedAt(now)   //签发时间
                .expiration(new Date(now.getTime() + expiration))   //过期时间
                .signWith(key, Jwts.SIG.HS256)   //签名算法
                .compact();  //压缩为JWT字符串
    }

    // 验证并解析 JWT
    public Claims parseToken(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        if (claims.getExpiration() == null) {
            throw new IllegalArgumentException("JWT缺少过期时间");
        }

        return claims;
    }

    // 获取用户 ID
    public Integer getUserId(String token) {

        Claims claims = parseToken(token);

        return Integer.valueOf(claims.getSubject());
    }
}