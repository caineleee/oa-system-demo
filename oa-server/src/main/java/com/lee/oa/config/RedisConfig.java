package com.lee.oa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * @ClassName RedisConfig
 * @Description Redis 配置类
 * @Author lihongliang
 * @Date 2025/10/3 17:48
 * @Version 1.0
 */
@Configuration
public class RedisConfig {

    // Redis 配置, redis 服务器地址
    @Value("${spring.redis.host}")
    private String host;

    // Redis 配置, redis 端口号
    @Value("${spring.redis.port}")
    private int port;

    // Redis 配置, redis 连接超时时间
    @Value("${spring.redis.timeout}")
    private int timeout;

    // Redis 配置, redis 数据库索引
    @Value("${spring.redis.database}")
    private int database;

    // Redis 连接池配置, 最大连接数
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    // Redis 连接池配置, 最大空闲数
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    // Redis 连接池配置, 最大等待时间
    @Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWait;

    // Redis 连接池配置, 最小空闲数
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    /**
     * 创建 Jedis 连接池
     * @return Jedis 连接池对象
     */
    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWait(Duration.ofMillis(maxWait));
        config.setMinIdle(minIdle);

        JedisPool jedisPool = null;
        try {
            jedisPool = new JedisPool(config, host, port, timeout, null, database);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jedisPool;
    }
}
