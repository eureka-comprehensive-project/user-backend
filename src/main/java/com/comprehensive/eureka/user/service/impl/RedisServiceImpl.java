package com.comprehensive.eureka.user.service.impl;


import com.comprehensive.eureka.user.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(String key, String value, long ttlSeconds) {
        redisTemplate.opsForValue().set(key, value, ttlSeconds, TimeUnit.SECONDS);
        log.info("[redis save] key = {}, value = {}, ttlSeconds = {}s", key, value, ttlSeconds);
    }

    @Override
    public String get(String key) {
        String value = redisTemplate.opsForValue().get(key);
        log.info("[redis get] key = {}, value = {}", key, value);
        return value;
    }

    @Override
    public boolean hasKey(String key) {
        boolean exists = Boolean.TRUE.equals(redisTemplate.hasKey(key));
        log.info("[redis hasKey] key = {}, exists = {}", key, exists);
        return exists;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
        log.info("[redis delete] key = {}", key);
    }
}
