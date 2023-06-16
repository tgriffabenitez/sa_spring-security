package com.sistemasactivos.msbff.utils;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Clase utilitaria para el manejo de la caché.
 */
@Component
public class CacheUtils {

    @Autowired
    private TokenUtils tokenUtils;

    private final Cache<String, Object> cache;

    @Autowired
    public CacheUtils(Cache<String, Object> cache) {
        this.cache = cache;
    }

    /**
     * Verifica si un token está presente en la caché.
     *
     * @param token el token a verificar.
     * @return {@code true} si el token está en la caché, {@code false} en caso contrario.
     */
    public boolean isTokenInCache(String token) {
        return cache.getIfPresent(token) != null;
    }

    /**
     * Obtiene el valor asociado a un token en la caché.
     * Si el token ha expirado, se invalida y se devuelve {@code null}.
     *
     * @param token el token del cual obtener el valor en la caché.
     * @return el valor asociado al token en la caché, o {@code null} si el token ha expirado.
     */
    public List<String> getValueFromCache(String token) {
        if (tokenUtils.isTokenExpired(token)) {
            cache.invalidate(token);
            return null;
        }
        return (List<String>) cache.getIfPresent(token);
    }

    /**
     * Almacena un par clave-valor en la caché.
     *
     * @param key   la clave del elemento a almacenar en la caché.
     * @param value el valor asociado a la clave a almacenar en la caché.
     */
    public void putDataInCache(String key, Object value) {
        cache.put(key, value);
    }
}
