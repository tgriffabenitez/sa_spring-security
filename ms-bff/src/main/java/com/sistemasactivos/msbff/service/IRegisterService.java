package com.sistemasactivos.msbff.service;

import com.sistemasactivos.msbff.model.RegisterRequest;
import reactor.core.publisher.Mono;

public interface IRegisterService {
    Mono<?> signIn(RegisterRequest registerRequest);
}
