package com.sistemasactivos.msbff.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CacheService {

    private final Cache<String, Object> cache;

    /**
     * Constructor de la clase.
     *
     * @param cache La instancia de la caché a utilizar.
     */
    @Autowired
    public CacheService(Cache<String, Object> cache) {
        this.cache = cache;
    }

    /**
     * Obtiene el valor asociado a una clave en la caché.
     *
     * @param key La clave del dato a obtener.
     * @return El valor asociado a la clave, o `null` si no existe.
     */
    public Object getDataFromCache(String key) {
        return cache.getIfPresent(key);
    }

    /**
     * Almacena un valor en la caché asociado a una clave.
     *
     * @param key   La clave del dato a almacenar.
     * @param value El valor a almacenar en la caché.
     */
    public void putDataInCache(String key, Object value) {
        cache.put(key, value);
    }
}
