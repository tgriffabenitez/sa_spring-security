package com.sistemasactivos.msbff.service;

import com.sistemasactivos.msbff.model.RegisterRequest;
import reactor.core.publisher.Mono;

/**
 * Interfaz para el servicio de registro.
 */
public interface IRegisterService {

    /**
     * Realiza el proceso de registro de un usuario.
     *
     * @param registerRequest La solicitud de registro con los datos del usuario.
     * @return Un Mono que emite el resultado del registro.
     */
    Mono<?> signIn(RegisterRequest registerRequest);
}