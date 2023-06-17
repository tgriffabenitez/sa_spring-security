package com.sistemasactivos.msusuario.controller;

import com.sistemasactivos.msusuario.model.UsuarioRegister;
import com.sistemasactivos.msusuario.service.UsuarioRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para las operaciones relacionadas con el registro de nuevos usuarios.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/signup")
public class UsuarioRegisterController {

    @Autowired
    private UsuarioRegisterService usuarioRegisterService;

    /**
     * Registra un nuevo usuario.
     *
     * @param userRegistration Objeto UsuarioRegister que contiene los datos de registro del usuario.
     * @return ResponseEntity con estado HTTP 201 (Created) si el usuario se registra correctamente.
     *         Si hay una violación de integridad de datos al registrar el usuario, se devuelve un ResponseEntity con estado HTTP 409 (Conflict).
     *         En caso de error, se devuelve un ResponseEntity con el mensaje de error y el estado HTTP 409 (Conflict).
     */
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UsuarioRegister userRegistration) {

        try {
            if (usuarioRegisterService.registerUser(userRegistration))
                return new ResponseEntity<>(HttpStatus.CREATED);


        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

