package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/*
    1. Created this Generic class which set and get any Object type of data using RedisTemplate
    2. If ttl is set to -1, it means the key will never expire
    3. For Mapping a class object to string we use ObjectMapper class
    4. We can also save List Maps Sets in Redis, Look for Documentation online for details and example
 */
@Service
@Slf4j
public class RedisService {

    @Autowired
    RedisTemplate redisTemplate;


    public <T> T get(String key, Class<T> entityClass){
        try{
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(o.toString(), entityClass);
        } catch (Exception e) {
            log.error("Exception while getting key from class : " +entityClass.toString());
            return null;
        }
    }

    public void set(String key, Object o, Long ttl){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception while setting key : "+key);
        }
    }


}
