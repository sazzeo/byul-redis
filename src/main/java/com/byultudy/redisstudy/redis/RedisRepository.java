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
        Long qty = redisTemplate.opsForValue().decrement(key);
        if(qty <0) {
            throw new RuntimeException("모든 티켓이 소진되었습니다.");
        }
        return qty;

    }
}
