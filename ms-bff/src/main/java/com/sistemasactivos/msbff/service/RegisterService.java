package com.sistemasactivos.msbff.service;

import com.sistemasactivos.msbff.model.RegisterRequest;
import com.sistemasactivos.msbff.utils.StatusCodeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Servicio para operaciones relacionadas con el registro de nuevos usuarios.
 */
@Service
public class RegisterService implements IRegisterService {

    @Autowired
    @Qualifier("signInWebClient")
    private WebClient webClient;

    /**
     * Realiza el proceso de registro de usuario.
     *
     * @param registerRequest El objeto RegisterRequest que contiene los datos del usuario a registrar.
     * @return Un Mono que emite el resultado del proceso de registro.
     */
    @Override
    public Mono<?> signIn(RegisterRequest registerRequest) {
        return webClient.post()
                .uri("/signup")
                .bodyValue(registerRequest)
                .exchangeToMono(clientResponse -> StatusCodeHandler.clientResponse(clientResponse, RegisterRequest.class));
    }
}
