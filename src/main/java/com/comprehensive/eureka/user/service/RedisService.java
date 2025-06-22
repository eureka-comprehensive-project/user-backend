package com.comprehensive.eureka.user.service;

public interface RedisService {
    void save(String key, String value, long ttlSeconds);
    String get(String key);
    boolean hasKey(String key);
    void delete(String key);
}
