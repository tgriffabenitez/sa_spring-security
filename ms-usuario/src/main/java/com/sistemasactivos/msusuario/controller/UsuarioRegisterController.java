package com.sistemasactivos.msusuario.controller;

import com.sistemasactivos.msusuario.model.UsuarioRegister;
import com.sistemasactivos.msusuario.service.UsuarioRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/signup")
public class UsuarioRegisterController {

    @Autowired
    private UsuarioRegisterService usuarioRegisterService;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UsuarioRegister userRegistration) {

        try {
            if (usuarioRegisterService.registerUser(userRegistration))
                return new ResponseEntity<>(HttpStatus.CREATED);


        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}

