//
//import com.alibaba.fastjson2.JSON;
//import com.alibaba.fastjson2.JSONWriter;
//import com.lee.oa.Application;
//import com.lee.oa.pojo.User;
//import com.lee.oa.util.SerializeUtil;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.Transaction;
//
//import java.util.Map;
//import java.util.Set;
//
//import static java.lang.Thread.sleep;
//
//
///**
// * @ClassName RedisJedisTest  Note: Jedis 已经弃用, 依赖已经移除,此类已弃用
// * @Description Redis 测试类
// * @Author lihongliang
// * @Date 2025/10/3 16:56
// * @Version 1.0
// */
//@SpringBootTest(classes = Application.class)
//@SpringJUnitConfig
//public class RedisJedisTest {
//
//    /**
//     * JedisPool 对象
//     */
//    @Autowired
//    private JedisPool jedisPool;
//
//    private Jedis jedis = null;
//
//    /**
//     * 初始化 jedis 实例对象
//     */
//    @BeforeEach
//    public void initConnection() {
//        jedis = jedisPool.getResource();
//        // 测试默认执行 index 1 数据库
//        jedis.select(1);
//    }
//
//    /**
//     * 释放资源
//     */
//    @AfterEach
//    public void closeConnection() {
//        if (jedis != null) {
//            jedis.close();
//        }
//    }
//
//    private static final String NAME_KEY = "name_test";
//    private static final String NAME_VALUE = "Lee";
//    private static final String AGE_KEY = "age_test";
//    private static final String AGE_VALUE = "18";
//    private static final String ADDRESS_KEY = "address_test";
//    private static final String ADDRESS_VALUE = "Beijing";
//
//    /**
//     * 测试 Redis String 操作
//     */
//    @Test
//    public void testString() {
//        if (jedis.get(NAME_KEY) != null) {
//            jedis.del(NAME_KEY);
//        }
//        if (jedis.get(AGE_KEY) != null) {
//            jedis.del(AGE_KEY);
//        }
//        if (jedis.get(ADDRESS_KEY) != null) {
//            jedis.del(ADDRESS_KEY);
//        }
//        // 设置单挑 String 数据
//        jedis.set(NAME_KEY, NAME_VALUE);
//        // 获取单条 String 数据 并输出
//        System.out.println(jedis.get(NAME_KEY));
//        // 设置多条 String 数据
//        jedis.mset(AGE_KEY, AGE_VALUE, ADDRESS_KEY, ADDRESS_VALUE);
//        // 获取多条 String 数据 并输出
//        jedis.mget(AGE_KEY, ADDRESS_KEY).forEach(System.out::println);
//        // 删除单条 String 数据
//        jedis.del(NAME_KEY);
//        // 删除多条 String 数据
//        jedis.del(AGE_KEY, ADDRESS_KEY);
//        // 获取多条 String 数据 并断言是否为空
//        jedis.mget(NAME_KEY, AGE_KEY, ADDRESS_KEY).forEach(Assertions::assertNull);
//    }
//
//    /**
//     * 测试 Redis Hash 操作
//     */
//    @Test
//    public void testHash() {
//        String hashKey = "hash_test";
//        String userKey = "user_test";
//        if (jedis.exists(hashKey)) {
//            jedis.del(hashKey);
//        }
//        if (jedis.exists(userKey)) {
//            jedis.del(userKey);
//        }
//        // 添加单条 hash 数据
//        jedis.hset(hashKey, NAME_KEY, NAME_VALUE);
//        // 获取单条 hash 数据 并输出
//        System.out.println(jedis.hget(hashKey, NAME_KEY));
//        // 添加多条 hash 数据
//        jedis.hmset(userKey, Map.of(NAME_KEY, NAME_VALUE, AGE_KEY, AGE_VALUE, ADDRESS_KEY, ADDRESS_VALUE));
//        // 获取多条 hash 数据 并输出
//        jedis.hgetAll(userKey).forEach((key, value) -> System.out.println(key + ":" + value));
//        // 删除单条 hash 数据
//        jedis.hdel(userKey, NAME_KEY);
//
//        jedis.hgetAll(userKey).forEach((key, value) -> System.out.println(key + "=" + value));
//    }
//
//    /**
//     * 测试 Redis List 操作
//     */
//    @Test
//    public void testList() {
//        String listKey = "list_test";
//        if (jedis.exists(listKey)) {
//            jedis.del(listKey);
//            System.out.println("Redis List 初始化成功");
//        }
//        jedis.lpush(listKey, "zhangsan", "lisi");
//        jedis.rpush(listKey,"wangwu", "zhaoliu");
//        jedis.lrange(listKey, 0, -1).forEach(System.out::println);
//        System.out.println("========================");
//
//        jedis.lrem(listKey, 1, "lisi");
//
//        jedis.lrange(listKey, 0, -1).forEach(System.out::println);
//    }
//
//    /**
//     * 测试 Redis Set 操作
//     */
//    @Test
//    public void testSet() {
//        String setKey = "set_test";
//        if (jedis.exists(setKey)) {
//            jedis.del(setKey);
//            System.out.println("Redis Set 测试初始化成功");
//        }
//        // 添加数据
//        jedis.sadd(setKey, "a", "b", "c", "d");
//        jedis.smembers(setKey).forEach(System.out::println);
//        System.out.println("========================");
//        // 删除数据
//        jedis.srem(setKey, "a");
//        jedis.smembers(setKey).forEach(System.out::println);
//    }
//
//    /**
//     * 测试 Redis Sorted Set 操作
//     */
//    @Test
//    public void testSortedSet() {
//        String sortedSetKey = "sorted_set_test";
//        if (jedis.exists(sortedSetKey)) {
//            jedis.del(sortedSetKey);
//            System.out.println("Redis Sorted Set 测试初始化成功");
//        }
//        // 添加数据
//        Map<String, Double> map = Map.of("a", 1.0, "w", 2.0, "c", 6.0, "d", 4.0);
//        jedis.zadd(sortedSetKey, map);
//        jedis.zrange(sortedSetKey, 0, -1).forEach(System.out::println);
//
//        System.out.println("========================");
//        jedis.zrem(sortedSetKey, "a");
//        jedis.zrange(sortedSetKey, 0, -1).forEach(System.out::println);
//
//    }
//
//    /**
//     * 测试 Redis Directory 操作
//     */
//    @Test
//    public void testDirectory() {
//        String directoryKey = "directory_test";
//        String directoryLevel2 = "user";
//        String directoryLevel3 = "property";
//        if (jedis.exists(directoryKey)) {
//            jedis.del(directoryKey);
//            System.out.println("Redis Directory 测试初始化成功");
//        }
//        // 添加数据
//        jedis.set(String.join(":", new String[]{directoryKey, directoryLevel2 + "01", directoryLevel3 + "01"}), "a");
//        jedis.set(String.join(":", new String[]{directoryKey, directoryLevel2 + "01", directoryLevel3 + "02"}), "b");
//        jedis.set(String.join(":", new String[]{directoryKey, directoryLevel2 + "02", directoryLevel3 + "01"}), "c");
//        System.out.println(jedis.get(String.join(":", new String[]{directoryKey, directoryLevel2 + "01", directoryLevel3 + "01"})));
//        System.out.println(jedis.get(String.join(":", new String[]{directoryKey, directoryLevel2 + "01", directoryLevel3 + "02"})));
//        System.out.println(jedis.get(String.join(":", new String[]{directoryKey, directoryLevel2 + "01", directoryLevel3 + "01"})));
//    }
//
//    /**
//     * 设置失效时间
//     * @throws InterruptedException 中断异常
//     */
//    @Test
//    public void testExpire() throws InterruptedException {
//        // 给已经存在的 key 设置失效时间
//        String key = "code";
//        String value = "test";
//        jedis.set(key, value);
//        // 设置失效时间
//        jedis.expire(key, 3);
//        System.out.println(jedis.get(key));
//        System.out.println(jedis.ttl(key));
//        // 等待过期
//        sleep(3000);
//        System.out.println(jedis.ttl(key));
//    }
//
//    /**
//     * 查询当前数据库中所有 key
//     */
//    @Test
//    public void testGetAllKey() {
//        Set<String> set = jedis.keys("*");
//        set.forEach(System.out::println);
//        System.out.println(set.size());
//    }
//
//    /**
//     * 测试 Redis 事务操作
//     */
//    @Test
//    public void testTransaction() {
//        // 创建事务
//        Transaction transaction = jedis.multi();
//        transaction.set("tel", "10010");
//        // 提交事务
////        transaction.exec();
//        // 回滚事务 (回滚必须在事务提交前执行, 如果 exec 执行过后 discard 回滚则不能再执行)
//        transaction.discard();
//    }
//
//    /**
//     * 测试 Redis 序列化操作
//     */
//    @Test
//    public void testByte() {
//        User user = new User();
//        user.setId(1L);
//        user.setUsername("redistest");
//        user.setPassword("root");
//        // 序列化 Key
//        byte[] byteKey = SerializeUtil.serialize("user:" + user.getId());
//        // 序列化 Value
//        byte[] byteValue = SerializeUtil.serialize(user);
//
//        jedis.set(byteKey, byteValue);
//        byte[] userByte = jedis.get(byteKey);
//        // 反序列化
//        User userFromRedis = (User) SerializeUtil.deserialize(userByte);
//        System.out.println(JSON.toJSONString(userFromRedis, JSONWriter.Feature.PrettyFormat));
//
//    }
//
//}
