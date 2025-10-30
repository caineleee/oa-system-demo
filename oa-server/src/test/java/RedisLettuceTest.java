import com.lee.oa.Application;
import com.lee.oa.pojo.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisLettuceTest
 * @Description Redis 测试类
 * @Author lihongliang
 * @Date 2025/10/10 18:11
 * @Version 1.0
 */

@SpringBootTest(classes = Application.class)
@SpringJUnitConfig
public class RedisLettuceTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Autowired
    private RedisTemplate<?, ?> defaultRedisTemplate;


    /**
     * 测试 RedisTemplate 序列化
     */
    @Test
    public void testRedisTemplateSerialize() {
        User user = new User()
                .setId(1L)
                .setUsername("Lee")
                .setPassword("123456")
                .setName("Lee")
                .setEmail("<EMAIL>")
                .setPhone("12345678901")
                .setTelephone("12345678901")
                .setAddress("China")
                .setEnabled(true)
                .setUserFace("https://avatars.githubusercontent.com/u/10252602?v=4")
                .setRemark("Lee is a good boy");

        // 使用 redisConfig 创建的 JSON 格式序列化对象 RedisTemplate 处理数据,
        // 存入 redis 数据为 JSON 字符串.
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set("user", user);
        User user1 = (User) ops.get("user");

        // 使用 defaultRedisTemplate 创建的默认序列化对象处理数据, 存入 redis 数据为对象.
        // 存入 redis 数据为 Byte[]数据.
        ValueOperations defaultOps = defaultRedisTemplate.opsForValue();
        defaultOps.set("default-user", user);
        User user2 = (User) defaultOps.get("default-user");

        System.out.println("user: " + user1);
        System.out.println("default-user: " + user2);
    }

    /**
     * 测试 String 操作
     */
    @Test
    public void stringTest() {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        // 操作单条 String 数据
        ops.set("user:02", "abc");
        String user02 = (String) ops.get("user:02");
        System.out.println("user:02: " + user02);
        // 操作多条 String 数据
        ops.multiSet(Map.of("age", "29", "address", "bj"));
        List<Object> values = ops.multiGet(List.of("name", "age", "address"));
        Assertions.assertNotNull(values);
        values.forEach(System.out::println);

        // 删除数据
//        redisTemplate.delete("name");

    }

    /**
     * 测试 Hash 操作
     */
    @Test
    public void hashTest() {
        HashOperations ops = redisTemplate.opsForHash();
        // 操作单挑数据
        ops.put("user:04", "name", "Lee");
        ops.put("user:04", "age", "29");
        ops.put("user:04", "address", "bj");
        System.out.println(ops.get("user:04", "name"));
        System.out.println(ops.entries("user:04"));

        // 操作多条数据
        ops.putAll("user:05", Map.of("name", "Lee", "age", "29", "address", "bj"));
        System.out.println(ops.multiGet("user:05", List.of("name", "age", "address")));
        ops.entries("user:05").forEach((k , v) -> System.out.println(k + ": " + v));

        // 删除数据
        ops.delete("user:05", "name", "age", "address");
        redisTemplate.delete("user:04");
    }

    /**
     * 测试 List 操作
     */
    @Test
    public void listTest() {
        ListOperations<String, Object> ops = redisTemplate.opsForList();
        // 左添加
        ops.leftPush("list:01", "Lee");
        ops.leftPush("list:01", "Wan");

        // 指定目标左添加
        ops.leftPush("list:01", "Wan", "Sun");

        // 右添加
        ops.rightPush("list:01", "Zhao");
        ops.rightPush("list:01", "Qian");
        // 指定目标右添加
        ops.rightPush("list:01", "Zhao", "Bai");

        // 获取数据
        List<Object> list = ops.range("list:01", 0,  -1);
        System.out.println( list);
        System.out.println("list length: " + ops.size("list:01"));

        ops.remove("list:01", 1, "Wan");
        System.out.println("list length: " + ops.size("list:01"));
        redisTemplate.delete("list:01");

    }

    @Test
    public void setTest() {
        SetOperations<String, Object> ops = redisTemplate.opsForSet();
        // 操作单条数据
        ops.add("set:01", "Lee");

        // 操作多条数据
        Set<String> set = new HashSet<>();
        set.add("Zhou");
        set.add("Ding");
        set.add("Chen");
        set.add("Liu");
        ops.add("set:01", set.toArray());
        System.out.println("set:01: " + ops.members("set:01"));

        ops.remove("set:01", "Zhou", "Liu");
        System.out.println("set:01: " + ops.members("set:01"));
        redisTemplate.delete("set:01");
    }

    /**
     * 测试 Sorted Set 操作
     */
    @Test
    public void sortedSetTest() {
        ZSetOperations<String, Object> ops = redisTemplate.opsForZSet();
        // 单条数据添加
        ops.add("zset:01", "Lee", 1);
        ops.add("zset:01", "Wan", 0.9);
        ops.add("zset:01", "Sun", 2.5);
        ops.add("zset:01", "Zhao", 1.8);
        ops.add("zset:01", "Qian", 2.0);

        // 多条数据添加
        TypedTuple<Object> bai = new DefaultTypedTuple<>("Bai", 0.9);
        TypedTuple<Object> pan = new DefaultTypedTuple<>("Pan", 1.1);
        TypedTuple<Object> zhou = new DefaultTypedTuple<>("Zhou", 1.5);
        TypedTuple<Object> wu = new DefaultTypedTuple<>("Wu", 3.0);
        ops.add("zset:01", Set.of(bai, pan, zhou, wu));

        System.out.println(ops.range("zset:01", 0, -1));

        // 删除数据
        ops.remove("zset:01", "Wan", "Sun");
        // 获取指定 sorted set 全部数据
        System.out.println("range: " + ops.range("zset:01", 0, -1));
        // 获取指定元素分数
        System.out.println("score: " + ops.score("zset:01", "Zhao"));
        // 获取指定元素排名
        System.out.println("rank: " + ops.rank("zset:01", "Zhao"));
        // 获取指定元素倒序排名
        System.out.println("reverseRank: " + ops.reverseRank("zset:01", "Zhao"));
        // 获取指定分数区间的元素个数
        System.out.println("count: " + ops.count("zset:01", 1.0, 2.0));
        // 获取指定分数区间的元素
        System.out.println("rangeByScore: " + ops.rangeByScore("zset:01", 1.0, 2.0));

        // 获取整个有序集合（按分数升序）
        Set<TypedTuple<Object>> allElements = ops.rangeWithScores("zset:01", 0, -1);
        System.out.println("allElements" + allElements);
        allElements.forEach(typedTuple ->
                System.out.println(typedTuple.getValue() + ": " + typedTuple.getScore()));

        // 获取指定分数范围内的元素（按分数升序）
        Set<TypedTuple<Object>> rangeElements = ops.rangeByScoreWithScores("zset:01", 1.0,2.0);
        System.out.println("rangeElements" + rangeElements);
        rangeElements.forEach(typedTuple ->
                System.out.println(typedTuple.getValue() + ": " + typedTuple.getScore()));

        redisTemplate.delete("zset:01");

    }

    /**
     * 测试获取所有 key 和 所有的 value
     */
    @Test
    public void getAllKeyTest() {
         Set<String> keys = redisTemplate.keys("*");
         keys.forEach(System.out::println);

        List<Object> values = redisTemplate.opsForValue().multiGet(redisTemplate.keys("*"));
        values.forEach(System.out::println);
    }

    /**
     * 测试失效时间
     */
    @Test
    public void expiresTest() throws InterruptedException {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        
        // 确保键不存在
        redisTemplate.delete("expires:01");

        // 插入数据的时候设置失效时间
        ops.set("expires:01", "abc", 5, TimeUnit.SECONDS);
        System.out.println("设置键后立即获取 TTL: " + redisTemplate.getExpire("expires:01") + " 秒");
        
        System.out.println("初始值: " + ops.get("expires:01"));
        Thread.sleep(3000);
        System.out.println("休眠 3 秒后 TTL: " + redisTemplate.getExpire("expires:01") + " 秒");
        // 再次检查值是否变化
        System.out.println("3秒后的值: " + ops.get("expires:01"));
        Thread.sleep(3000);
        System.out.println("再休眠 3 秒后 TTL: " + redisTemplate.getExpire("expires:01") + " 秒");
        // 检查值是否变化
        System.out.println("最终获取值: " + ops.get("expires:01"));
        // 额外测试：检查键是否存在
        System.out.println("键是否存在: " + redisTemplate.hasKey("expires:01"));

        // 给存在的值设置失效时间
        ops.set("expires:02", "def");
        redisTemplate.expire("expires:02", 5, TimeUnit.SECONDS);
        System.out.println("设置键后立即获取 TTL: " + redisTemplate.getExpire("expires:02") + " 秒");

        System.out.println("初始值: " + ops.get("expires:02"));
        Thread.sleep(3000);
        System.out.println("休眠 3 秒后 TTL: " + redisTemplate.getExpire("expires:02") + " 秒");
        // 再次检查值是否变化
        System.out.println("3秒后的值: " + ops.get("expires:02"));
        Thread.sleep(3000);
        System.out.println("再休眠 3 秒后 TTL: " + redisTemplate.getExpire("expires:02") + " 秒");
        // 检查值是否变化
        System.out.println("最终获取值: " + ops.get("expires:02"));
        // 额外测试：检查键是否存在
        System.out.println("键是否存在: " + redisTemplate.hasKey("expires:02"));



    }

}