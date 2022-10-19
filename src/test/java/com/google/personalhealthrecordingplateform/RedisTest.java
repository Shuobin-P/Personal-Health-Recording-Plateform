package com.google.personalhealthrecordingplateform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    public void test() {
        System.out.println(redisTemplate == null);
        System.out.println(redisTemplate.opsForValue() == null);
        redisTemplate.opsForValue().set("sex", "male");
        System.out.println(redisTemplate.opsForValue().get("sex"));
    }
}
