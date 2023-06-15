package com.sistemasactivos.msbff.service;

import com.sistemasactivos.msbff.model.RegisterRequest;
import com.sistemasactivos.msbff.utils.StatusCodeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RegisterService implements IRegisterService {

    @Autowired
    @Qualifier("signInWebClient")
    private WebClient webClient;

    @Override
    public Mono<?> signIn(RegisterRequest registerRequest) {
        return webClient.post()
                .uri("/signup")
                .bodyValue(registerRequest)
                .exchangeToMono(clientResponse -> StatusCodeHandler.clientResponse(clientResponse, RegisterRequest.class));
    }
}
