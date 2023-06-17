package com.sistemasactivos.msbff.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Configuración para la caché.
 */
@Configuration
public class CacheConfig {

    /**
     * Crea y configura la instancia de la caché.
     * Tiene un tamano de 1000 elementos y expira después de 60 minutos.
     *
     * @return La instancia de la caché configurada.
     */
    @Bean
    public Cache<String, Object> cache() {
        return Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build();
    }
}