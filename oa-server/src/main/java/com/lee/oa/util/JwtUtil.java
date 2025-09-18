package com.lee.oa.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT工具类，用于处理JWT令牌的生成和验证
 * 
 * @author Lee
 * @since 1.0.0
 */
@Component
public class JwtUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    /**
     * JWT令牌有效期（秒）
     */
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    /**
     * 密钥字符串
     */
    private String secret = "mySecretKeymySecretKeymySecretKeymySecretKeymySecretKeymySecretKey";

    /**
     * 签名密钥对象
     */
    private final SecretKey signingKey;

    /**
     * 构造函数，初始化签名密钥
     */
    public JwtUtil() {
        // 根据密钥字符串生成签名密钥
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 从令牌中检索用户名
     * 
     * @param token JWT令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        // 从令牌中获取主题声明（用户名）
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 从令牌中检索过期日期
     * 
     * @param token JWT令牌
     * @return 过期日期
     */
    public Date getExpirationDateFromToken(String token) {
        // 从令牌中获取过期时间声明
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 从令牌中获取声明信息
     * 
     * @param token JWT令牌
     * @param claimsResolver 声明解析器函数
     * @param <T> 声明类型
     * @return 声明值
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        // 从令牌中获取所有声明
        final Claims claims = getAllClaimsFromToken(token);
        // 应用解析器函数并返回结果
        return claimsResolver.apply(claims);
    }

    /**
     * 为令牌获取所有信息
     * 
     * @param token JWT令牌
     * @return 声明对象
     */
    private Claims getAllClaimsFromToken(String token) {
        // 创建JWT解析器构建器
        return Jwts.parserBuilder()
                // 设置签名密钥
                .setSigningKey(signingKey)
                // 构建解析器
                .build()
                // 解析JWT令牌并获取载荷
                .parseClaimsJws(token)
                // 获取载荷内容
                .getBody();
    }

    /**
     * 检查令牌是否过期
     * 
     * @param token JWT令牌
     * @return 是否过期
     */
    private Boolean isTokenExpired(String token) {
        // 获取令牌的过期时间
        final Date expiration = getExpirationDateFromToken(token);
        // 检查过期时间是否在当前时间之前
        return expiration.before(new Date());
    }

    /**
     * 生成令牌
     * 
     * @param username 用户名
     * @return JWT令牌
     */
    public String generateToken(String username) {
        // 创建声明映射
        Map<String, Object> claims = new HashMap<>();
        // 创建并返回令牌
        return doGenerateToken(claims, username);
    }

    /**
     * 创建令牌
     * 
     * @param claims 声明映射
     * @param subject 主题（用户名）
     * @return JWT令牌
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        // 使用JWT构建器创建令牌
        return Jwts.builder()
                // 设置声明
                .setClaims(claims)
                // 设置主题
                .setSubject(subject)
                // 设置签发时间
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                // 设置签名密钥
                .signWith(signingKey)
                // 生成JWT字符串
                .compact();
    }

    /**
     * 验证令牌
     * 
     * @param token JWT令牌
     * @param username 用户名
     * @return 验证结果
     */
    public Boolean validateToken(String token, String username) {
        // 从令牌中提取用户名
        final String extractedUsername = getUsernameFromToken(token);
        // 验证用户名是否匹配且令牌未过期
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}