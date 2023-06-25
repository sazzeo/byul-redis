package com.byultudy.redisstudy.redis;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper objectMapper;

    public void set(String key, Long qty) {
        redisTemplate.opsForValue().set(key, qty.toString());
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setHash(String key, Long id, Object object) {
        try {
            redisTemplate.opsForHash().put(key, id.toString(), objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json 형식이 일치하지 않습니다.");
        }
    }

    public <T> Optional<T> getHash(String key, Long id, Class<T> clazz) {
        String o = (String) redisTemplate.opsForHash().get(key, id.toString());

        try {
            return Optional.ofNullable(objectMapper.readValue(o, clazz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json 형식이 일치하지 않습니다.");
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }
}
