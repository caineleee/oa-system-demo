package com.lee.oa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//import java.time.Duration;

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

    // Redis 配置, redis 密码
    @Value("${spring.redis.password:}")
    private String password;

    // Redis 连接池配置, 最大连接数
    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;

    // Redis 连接池配置, 最大空闲数
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;

    // Redis 连接池配置, 最大等待时间
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private int maxWait;

    // Redis 连接池配置, 最小空闲数
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle;
    
    // Sentinel 配置
    @Value("${spring.redis.sentinel.master:}")
    private String sentinelMaster;
    
    @Value("${spring.redis.sentinel.nodes:}")
    private String sentinelNodes;

    /**
     * 创建 Lettuce 连接工厂（支持 Sentinel）
     * @return Lettuce 连接工厂
     */
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // 检查是否配置了 Sentinel
        if (sentinelMaster != null && !sentinelMaster.isEmpty() && 
            sentinelNodes != null && !sentinelNodes.isEmpty()) {
            // 使用 Sentinel 配置
            /*
             * 正常状态：
             *    Sentinel 监控 -> Redis 主节点 (可写)
             *                 -> Redis 从节点1 (只读)
             *                 -> Redis 从节点2 (只读)
             *    主节点宕机后：
             *    1. Sentinel 检测到主节点失联
             *    2. Sentinel 选举一个从节点提升为新主节点
             *    3. Sentinel 更新主节点信息
             *    4. 应用程序下次请求时，Sentinel 返回新主节点地址
             *    5. 应用程序连接到新主节点继续写操作
             */
            RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master(sentinelMaster);
            
            String[] nodes = sentinelNodes.split(",");
            for (String node : nodes) {
                String[] parts = node.split(":");
                if (parts.length == 2) {
                    sentinelConfig.sentinel(parts[0], Integer.parseInt(parts[1]));
                }
            }
            
            if (password != null && !password.isEmpty()) {
                sentinelConfig.setPassword(RedisPassword.of(password));
            }
            
            return new LettuceConnectionFactory(sentinelConfig);
        } else {
            // 使用标准主从配置
            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
            config.setDatabase(database);
            // 如果配置了密码，则设置密码
            if (password != null && !password.isEmpty()) {
                config.setPassword(RedisPassword.of(password));
            }
            return new LettuceConnectionFactory(config);
        }
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 为 String 类型的 Key 设置序列化器
        template.setKeySerializer(new StringRedisSerializer());
        // 为 String 类型的 Value 设置序列化器
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 为 Hash 类型的 Key & Value 值设置序列化器
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.setConnectionFactory(lettuceConnectionFactory);
        return template;

    }

    /**
     * 创建使用默认 JDK 序列化器的 RedisTemplate
     * @param lettuceConnectionFactory Lettuce 连接工厂
     * @return 使用默认序列化器的 RedisTemplate
     */
    @Bean
    public RedisTemplate<?, ?> defaultRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        // 使用默认的 JDK 序列化器
        template.setConnectionFactory(lettuceConnectionFactory);
        // 调用 afterPropertiesSet 初始化模板
        template.afterPropertiesSet();
        return template;
    }

//    /**
//     * 创建 RedisTemplate
//     * @param connectionFactory Redis连接工厂
//     * @return RedisTemplate对象
//     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//
//        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//
//        // 使用StringRedisSerializer来序列化和反序列化redis的key值
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//
//        // 设置key和value的序列化规则
//        template.setKeySerializer(stringRedisSerializer);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setHashKeySerializer(stringRedisSerializer);
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//
//        // 设置支持事务
//        template.setEnableTransactionSupport(true);
//        template.afterPropertiesSet();
//
//        return template;
//    }

//    /**
//     * 创建 Jedis 连接池 (jedis 已弃用, 注释掉)
//     * @return Jedis 连接池对象
//     */
//    @Bean
//    public JedisPool getJedisPool() {
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(maxActive);
//        config.setMaxIdle(maxIdle);
//        config.setMaxWait(Duration.ofMillis(maxWait));
//        config.setMinIdle(minIdle);
//
//        JedisPool jedisPool = null;
//        try {
//            jedisPool = new JedisPool(config, host, port, timeout, null, database);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return jedisPool;
//    }
}