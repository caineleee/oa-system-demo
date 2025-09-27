package com.lee.oa.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
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


    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.token-secret}")
    private String tokenSecret;

    @Value("${jwt.token-expire-time}")
    private long tokenExpireTime;


    /**
     * 签名密钥对象，用于JWT的签名和验证
     */
    private SecretKey signKey;

    /**
     * 构造函数，初始化签名密钥
     */
    public JwtUtil() {
        // 注意：在构造函数中不能直接使用tokenSecret，因为Spring尚未完成属性注入
        // 签名密钥将在首次使用时初始化
    }
    
    /**
     * 初始化签名密钥
     * 该方法确保签名密钥只在首次使用时初始化一次
     */
    private void initSignKey() {
        if (this.signKey == null) {
            // 根据密钥字符串生成签名密钥
            this.signKey = Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8));
        }
    }


    /**
     * 基于用户信息生成 JWT token
     * 该方法创建包含用户信息和过期时间的JWT Token
     * @param userDetails 用户详细信息
     * @return JWT令牌
     */
    public String generateToken(UserDetails userDetails) {
        // 确保签名密钥已初始化
        initSignKey();
        // 创建声明映射
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        // 创建并返回令牌
        return doGenerateToken(claims);
    }




    /**
     * 验证令牌
     * 验证JWT Token的有效性，包括用户名匹配和过期时间检查
     * @param token JWT令牌
     * @param userDetails 用户信息
     * @return 验证结果，true表示有效，false表示无效
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        // 确保签名密钥已初始化
        initSignKey();
        // 从令牌中提取用户名
        String username = getUsernameFromToken(token);
        // 验证用户名是否匹配且令牌未过期
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }



    /**
     * 从令牌中检索用户名
     * 从JWT Token中提取存储的用户名信息
     * @param token JWT令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        // 确保签名密钥已初始化
        initSignKey();
        // 从令牌中获取主题声明（用户名）
        return getClaimFromToken(token, Claims::getSubject);
    }


    /**
     * 判断令牌是否可以刷新
     * 检查JWT Token是否未过期，可用于刷新Token的判断
     * @param token JWT令牌
     * @return 是否可以刷新令牌，true表示未过期可以刷新
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新令牌
     * 更新JWT Token的创建时间，生成新的Token
     * @param token JWT令牌
     * @return 新的令牌
     */
    public String refreshToken(String token) {
        // 确保签名密钥已初始化
        initSignKey();
        // 获取现有Token的所有声明
        Claims claims = getAllClaimsFromToken(token);
        // 更新创建时间
        claims.put(CLAIM_KEY_CREATED, new Date());
        // 生成新的Token
        return doGenerateToken(claims);
    }


    /**
     * 检查令牌是否过期
     * 比较JWT Token的过期时间与当前时间，判断是否已过期
     * @param token JWT令牌
     * @return 是否过期，true表示已过期
     */
    private Boolean isTokenExpired(String token) {
        // 确保签名密钥已初始化
        initSignKey();
        // 获取令牌的过期时间
        Date expiration = getExpirationDateFromToken(token);
        // 检查过期时间是否在当前时间之前
        return expiration.before(new Date());
    }

    /**
     * 从令牌中检索过期日期
     * 提取JWT Token中的过期时间信息
     * @param token JWT令牌
     * @return 过期日期
     */
    private Date getExpirationDateFromToken(String token) {
        // 从令牌中获取过期时间声明
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 从令牌中获取声明信息
     * 通用方法，用于从JWT Token中提取各种声明信息
     * @param token JWT令牌
     * @param claimsResolver 声明解析器函数
     * @param <T> 声明类型
     * @return 声明值
     */
    private  <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        // 确保签名密钥已初始化
        initSignKey();
        // 从令牌中获取所有声明
        Claims claims = getAllClaimsFromToken(token);
        // 应用解析器函数并返回结果
        return claimsResolver.apply(claims);
    }

    /**
     * 为令牌获取所有信息
     * 解析JWT Token并获取其中包含的所有声明
     * @param token JWT令牌
     * @return 声明对象
     */
    private Claims getAllClaimsFromToken(String token) {
        // 确保签名密钥已初始化
        if (token == null || token.isEmpty()) {
            return null;
        }
        initSignKey();
        // 创建JWT解析器构建器
        return Jwts.parserBuilder()
                // 设置签名密钥
                .setSigningKey(signKey)
                // 构建解析器
                .build()
                // 解析JWT令牌并获取载荷
                .parseClaimsJws(token)
                // 获取载荷内容
                .getBody();
    }

    /**
     * 基于荷载创建 JWT token
     * 使用声明信息创建JWT Token
     * @param claims 声明映射
     * @return JWT令牌
     */
    private String doGenerateToken(Map<String, Object> claims) {
        // 确保签名密钥已初始化
        initSignKey();
        // 使用JWT构建器创建令牌
        return Jwts.builder()
                // 设置声明
                .setClaims(claims)
                // 设置主题
//                .setSubject(subject)
                // 设置签发时间
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime * 1000))
                // 设置签名密钥
                .signWith(signKey)
                // 生成JWT字符串
                .compact();
    }
}