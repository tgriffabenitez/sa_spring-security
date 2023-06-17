package com.sistemasactivos.msbff.controller;

import com.sistemasactivos.msbff.service.UsuarioService;
import com.sistemasactivos.msbff.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CacheUtils cacheUtils;

    @GetMapping("")
    public ResponseEntity<?> findAll(ServerHttpRequest request) {
        try {
            if (cacheUtils.isAdmin(request)) {
                return new ResponseEntity<>(usuarioService.findAll(request), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No tiene permisos para acceder a este endpoint", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}