package com.sistemasactivos.msbff.service;

import com.sistemasactivos.msbff.model.LoginRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

public interface ILoginService {
//    Mono<?> login(LoginRequest loginRequest);

    Mono<?> login(LoginRequest loginRequest, ServerHttpResponse response);
}
