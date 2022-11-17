package com.google.personalhealthrecordingplateform;

import com.google.personalhealthrecordingplateform.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    RedisUtils redisUtils;

    @Test
    public void test() {
        redisUtils.delete("java_sport:sys_user:admin");
    }

}
