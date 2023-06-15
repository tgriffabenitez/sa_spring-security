package com.sistemasactivos.msbff.controller;

import com.sistemasactivos.msbff.model.Empleado;
import com.sistemasactivos.msbff.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("")
    public ResponseEntity<?> findAll(ServerHttpRequest request) {
        try {
            if (empleadoService.isAdmin(request) || empleadoService.isUsuario(request)) {
                return new ResponseEntity<>(empleadoService.findAll(), HttpStatus.OK);
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
            if (empleadoService.isAdmin(request) || empleadoService.isUsuario(request)) {
                return new ResponseEntity<>(empleadoService.findById(id), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No tiene permisos para acceder a este endpoint", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Empleado empleado, ServerHttpRequest request) {
        try {
            if (empleadoService.isAdmin(request)) {
                return new ResponseEntity<>(empleadoService.save(empleado), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("No tiene permisos para acceder a este endpoint", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Empleado empleado, ServerHttpRequest request) {
        try {
            if (empleadoService.isAdmin(request)) {
                return new ResponseEntity<>(empleadoService.update(id, empleado), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No tiene permisos para acceder a este endpoint", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, ServerHttpRequest request) {
        try {
            if (empleadoService.isAdmin(request)) {
                return new ResponseEntity<>(empleadoService.delete(id), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("No tiene permisos para acceder a este endpoint", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
