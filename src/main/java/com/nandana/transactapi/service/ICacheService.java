package com.nandana.transactapi.service;

public interface ICacheService {
    void set(String key, Object value, long ttlInSeconds);

    Object get(String key);

    void delete(String key);

    boolean exists(String key);

    void clearCacheByPattern(String pattern);
}
