package com.sistemasactivos.msbff.controller;

import com.sistemasactivos.msbff.model.LoginRequest;
import com.sistemasactivos.msbff.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @PostMapping("")
    private Mono<?> login(@RequestBody LoginRequest loginRequest, ServerHttpResponse response) {
        return loginService.login(loginRequest, response);
    }
}