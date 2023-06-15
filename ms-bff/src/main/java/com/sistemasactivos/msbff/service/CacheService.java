package com.sistemasactivos.msbff.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CacheService {

    @Autowired
    public CacheService(Cache<String, Object> cache) {
        this.cache = cache;
    }
    private final Cache<String, Object> cache;

    public Object getDataFromCache(String key) {
        return cache.getIfPresent(key);
    }

    public void putDataInCache(String key, Object value) {
        cache.put(key, value);
    }
}
