package com.sistemasactivos.msusuario.controller;

import com.sistemasactivos.msusuario.model.Base;
import com.sistemasactivos.msusuario.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Implementación base de un controlador RESTful.
 *
 * @param <E> El tipo de entidad manejada por el controlador.
 * @param <S> El tipo de servicio asociado al controlador.
 */
public abstract class BaseControllerImpl <E extends Base, S extends BaseServiceImpl<E, Long>> implements BaseController<E, Long> {

    @Autowired
    protected S service;

    /**
     * Obtiene todas las entidades.
     *
     * @return ResponseEntity con la lista de entidades si se encuentran, o un ResponseEntity vacío si no hay contenido.
     *         En caso de error, se devuelve un ResponseEntity con el mensaje de error y el estado HTTP 500 (Internal Server Error).
     */
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<E> entity = service.findAll();
            if (entity.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(entity, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene una entidad por su ID.
     *
     * @param id ID de la entidad a buscar.
     * @return ResponseEntity con la entidad si se encuentra, o un ResponseEntity con estado HTTP 404 (Not Found) si no se encuentra.
     *         En caso de error, se devuelve un ResponseEntity con el mensaje de error y el estado HTTP 500 (Internal Server Error).
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            E entity = service.findById(id);
            if (entity == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(entity, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}