package com.sistemasactivos.msbff.controller;

import com.sistemasactivos.msbff.model.RegisterRequest;
import com.sistemasactivos.msbff.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/signup")
public class RegisterController {

    @Autowired
    private IRegisterService registerService;

    /**
     * Realiza el proceso de registro de un nuevo usuario.
     *
     * @param registerRequest El objeto RegisterRequest que contiene los datos de registro del usuario.
     * @return Un Mono que emite el resultado del proceso de registro.
     */
    @PostMapping("")
    private Mono<?> signup(@RequestBody RegisterRequest registerRequest) {
        return registerService.signIn(registerRequest);
    }
}
