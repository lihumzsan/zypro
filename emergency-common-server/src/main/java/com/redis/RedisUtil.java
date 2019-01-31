package com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * @author LI.HU
 * @date 2019/1/22   17:58
 * <p>Description:
 * redis缓存
 * </p>
 */
public class RedisUtil {

    public static void main(String[] args) {

        // 连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        //使用字符串list存值
        jedis.lpush("城市", "南京");
        jedis.lpush("城市", "上海");
        jedis.lpush("城市", "苏州");
        jedis.lpush("城市", "北京");
        jedis.lpush("城市", "南通");

        // 连接本地的 Redis 服务
        Jedis tmpjedis = new Jedis("localhost");
        //list集合取值,这里注意的是,100的位置是结束的角标,如果大了没事,小了的话就会缺
        List<String> arr = tmpjedis.lrange("城市", 0, 100);
        System.out.println(arr.size());
        for (String string : arr) {
            System.out.println(string);
        }

    }

}
