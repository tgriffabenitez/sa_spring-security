package com.sistemasactivos.msbff.controller;

import com.sistemasactivos.msbff.model.LoginRequest;
import com.sistemasactivos.msbff.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


/**
 * Controlador para manejar las operaciones relacionadas con el login.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    /**
     * Realiza el proceso de inicio de sesión.
     *
     * @param loginRequest El objeto LoginRequest que contiene las credenciales de inicio de sesión.
     * @param response     La respuesta HTTP que se utilizará para establecer las cookies de autenticación.
     * @return Un Mono que emite el resultado del proceso de inicio de sesión.
     */
    @PostMapping("")
    private Mono<?> login(@RequestBody LoginRequest loginRequest, ServerHttpResponse response) {
        return loginService.login(loginRequest, response);
    }
}