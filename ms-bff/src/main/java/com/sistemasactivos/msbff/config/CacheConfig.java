package com.sistemasactivos.msbff.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, Object> cache() {
        return Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .build();
    }
}