package com.sistemasactivos.msbff.utils;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CacheUtils {
    private final Cache<String, Object> cache;

    @Autowired
    public CacheUtils(Cache<String, Object> cache) {
        this.cache = cache;
    }

    public boolean isTokenInCache(String token) {
        return cache.getIfPresent(token) != null;
    }

    public Object getValueFromCache(String token) {
        return cache.getIfPresent(token);
    }
}
