package com.google.personalhealthrecordingplateform.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author W&F
 */
@Component
public class RedisUtils {

    private RedisTemplate redisTemplate;

    @Autowired
    public RedisUtils(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 给一个指定的 key 值附加过期时间
     *
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 判断redis中是否存在key所对应的键值对
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 根据key，拿到val
     *
     * @param key key
     * @return val
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /*---------------------------------------String-----------------------------------------*/

    /**
     * 把对象添加到Redis中，那么该对象应该要被序列化，
     *
     * @param key
     * @param val
     */
    public void add(String key, Object val) {
        //FIXME 序列化以后丢失字段，Redis丢失字段
        redisTemplate.opsForValue().set(key, val);
    }

    /**
     * 删除key对应的记录
     *
     * @param key Redis Key
     */
    public void delete(String key) {
        redisTemplate.delete(key);

    }


}

