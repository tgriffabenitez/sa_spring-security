package com.sistemasactivos.msbff.controller;

import com.sistemasactivos.msbff.service.UsuarioService;
import com.sistemasactivos.msbff.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;


/**
 * Controlador para manejar las operaciones relacionadas con los usuarios.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CacheUtils cacheUtils;

    /**
     * Obtiene todos los empleados.
     *
     * @param request La solicitud HTTP entrante.
     * @return ResponseEntity con la lista de empleados o un mensaje de error y código de estado correspondiente.
     */
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

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id, ServerHttpRequest request) {
        try {
            if (cacheUtils.isAdmin(request)) {
                return new ResponseEntity<>(usuarioService.findById(id, request), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No tiene permisos para acceder a este endpoint", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}