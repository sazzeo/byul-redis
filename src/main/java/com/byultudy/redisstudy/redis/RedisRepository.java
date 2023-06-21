package com.byultudy.redisstudy.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public void setCount(String key, Long qty) {
        redisTemplate.opsForValue().set(key , qty.toString());
    }

    public Long getCount(String key) {
        return redisTemplate.opsForValue().increment(key);
    }
}
