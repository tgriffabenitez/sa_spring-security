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

    /**
     * Obtiene todos los empleados.
     *
     * @param request La solicitud HTTP entrante.
     * @return ResponseEntity con la lista de empleados o un mensaje de error y código de estado correspondiente.
     */
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

    /**
     * Obtiene un empleado por su ID.
     *
     * @param id      El ID del empleado a buscar.
     * @param request La solicitud HTTP entrante.
     * @return ResponseEntity con el empleado encontrado o un mensaje de error y código de estado correspondiente.
     */
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

    /**
     * Crea un nuevo empleado.
     *
     * @param empleado El objeto Empleado que contiene los datos del empleado a crear.
     * @param request  La solicitud HTTP entrante.
     * @return ResponseEntity con el empleado creado o un mensaje de error y código de estado correspondiente.
     */
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

    /**
     * Actualiza un empleado existente.
     *
     * @param id       El ID del empleado a actualizar.
     * @param empleado El objeto Empleado que contiene los datos actualizados del empleado.
     * @param request  La solicitud HTTP entrante.
     * @return ResponseEntity con el empleado actualizado o un mensaje de error y código de estado correspondiente.
     */
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

    /**
     * Elimina un empleado por su ID.
     *
     * @param id      El ID del empleado a eliminar.
     * @param request La solicitud HTTP entrante.
     * @return ResponseEntity con el código de estado correspondiente (204 - NO_CONTENT) o un mensaje de error y código de estado correspondiente.
     */
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
