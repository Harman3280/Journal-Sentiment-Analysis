package org.example.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/*
    1. This class is created just to test, whether redis is working or not
    2. As Redis-Cli and RedisTemplate use different Serializer & Deserializers, so they can't fetch each other keys and their values.
    3. Because by default while creating a bean for RedisTemplate, due to SpringAutoConfiguration default Serializer & Deserializers
       are being used, Which are not same with our redis-cli Serializer & Deserializers.
    4. We can create a configuration class for RedisConfiguration for configuring Serializer & Deserializers.
    5.
 */
@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    public void testRedisTemplate(){
        //red isTemplate.opsForValue().set("email", "myEmail@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
        Object salary = redisTemplate.opsForValue().get("salary");

        System.out.println(" email Key value : " +email);
        System.out.println(" salary Key value : " +salary);
    }

}
