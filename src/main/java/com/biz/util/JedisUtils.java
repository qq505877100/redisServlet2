package com.biz.util;/**
 * Created by dell on 2017/7/14.
 */

import com.biz.com.biz.constant.Global;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * @author
 * @description
 * @create 2017-07-14 17:47
 **/
public class JedisUtils {
    private static JedisPool jedisPool;
    static {
        Properties properties = new Properties();
        InputStream stream = JedisUtils.class.getClassLoader().getResourceAsStream("constant.properties");
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(Global.MAX_ACTIVE);
        jedisPoolConfig.setMaxIdle(Global.MAX_IDEL);
        String host = properties.getProperty("host");
        int port = Integer.parseInt(properties.getProperty("port"));
        //提取常量到配置文件中
        jedisPool = new JedisPool(jedisPoolConfig,host,port);
    }

    /**
     * get方式存储学生信息，以json串的形式存储
     */

    public static String set(String key,String value) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.set(key, value);
        jedis.close();
        return result;
    }

    public static String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    public static Long zadd(String key,double score,String value) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.zadd(key,score,value);
        jedis.close();
        return result;
    }

    /**
     * 获取所有匹配的keys
     */

    public static Set<String> getKeys(String keys) {
        Jedis jedis = jedisPool.getResource();
        Set<String> result = jedis.keys(keys);
        jedis.close();
        return result;
    }

    public static int getAllCounts(String key) {
        Set<String> result = getKeys(key);
        int count = result.size();
        return count;
    }


    /**
     * 获取排名分数的keys
     * zrevrangebyscore key1 max min [withscores] [limit offset count];分数降序返回。
     */

    public static Set<String> getRangeByScore(String key,int offset,int count) {
        Jedis jedis = jedisPool.getResource();
        Set<String> result = jedis.zrevrangeByScore(key,100,0,offset,count);
        jedis.close();
        return result;
    }

    /**
     * 删除指定的key
     */

    public static Long delKey(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.del(key);
        jedis.close();
        return result;
    }

    /**
     * 删除sorted-set指定key中的值。
     */

    public static Long delSortedValue(String key,String value) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.zrem(key,value);
        jedis.close();
        return result;
    }


}
