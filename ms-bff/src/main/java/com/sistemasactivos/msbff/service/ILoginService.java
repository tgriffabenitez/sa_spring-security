package com.sistemasactivos.msbff.service;

import com.sistemasactivos.msbff.model.LoginRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * Interfaz para el servicio de inicio de sesión.
 */
public interface ILoginService {

    /**
     * Realiza el proceso de inicio de sesión para un usuario.
     *
     * @param loginRequest La solicitud de inicio de sesión con las credenciales del usuario.
     * @param response     La respuesta del servidor.
     * @return Un Mono que emite el resultado del inicio de sesión.
     */
    Mono<?> login(LoginRequest loginRequest, ServerHttpResponse response);
}
